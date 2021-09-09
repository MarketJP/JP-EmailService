package com.gzgy.EmailService.modules.email.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gzgy.EmailService.common.base.BaseMsg;
import com.gzgy.EmailService.common.config.MyEmailConfig;
import com.gzgy.EmailService.common.enums.MQConstant;
import com.gzgy.EmailService.common.utils.SendEmailUtils;
import com.gzgy.EmailService.modules.email.dto.EmailManageDto;
import com.gzgy.EmailService.modules.email.dto.EmailRequsetDto;
import com.gzgy.EmailService.modules.email.entity.EmailConfigEntity;
import com.gzgy.EmailService.modules.email.entity.EmailRecordEntity;
import com.gzgy.EmailService.modules.email.mapper.EmailConfigMapper;
import com.gzgy.EmailService.modules.email.mapper.EmailRecordMapper;
import com.gzgy.EmailService.modules.email.service.IEmailRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gzgy.EmailService.modules.listener.MQConfirm;
import com.gzgy.EmailService.modules.listener.MQListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Authenticator;
import javax.mail.Session;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 邮件发送记录 服务实现类
 * </p>
 *
 * @author huangziping
 * @since 2020-03-24
 */
@Service
public class EmailRecordServiceImpl extends ServiceImpl<EmailRecordMapper, EmailRecordEntity> implements IEmailRecordService {

	private static final Logger logger = LoggerFactory.getLogger(EmailRecordServiceImpl.class);

	@Autowired
	private EmailRecordMapper emailRecordMapper;

	@Autowired
	private EmailConfigMapper emailConfigMapper;

	@Autowired
	private RabbitTemplate rabbitTemplate;

//	@Autowired
//	private SendEmailUtilsBank sendEmailUtils;

	@Autowired
	private SendEmailUtils sendEmailUtils;
	@Autowired
	MQConfirm mqConfirm;
	@Autowired
	MQListener mqListener;

	@Override
	public BaseMsg sendMQEmail(EmailRequsetDto emailRequsetDto){
		EmailRecordEntity one = new EmailRecordEntity();
		one.setSystemName(emailRequsetDto.getSystemName());
		one.setCreateTime(LocalDateTime.now());
		one.setMail(emailRequsetDto.getMail());
		one.setTitle(emailRequsetDto.getTitle());
		one.setContent(emailRequsetDto.getContent());
		one.setResult(0);
		one.setFileJson(emailRequsetDto.getFileJson());
		try{
			QueryWrapper<EmailConfigEntity> wrapper = new QueryWrapper<>();
			wrapper.eq("system_name", emailRequsetDto.getSystemName());
			EmailConfigEntity emailConfigEntity = emailConfigMapper.selectOne(wrapper);
			if(emailConfigEntity==null){
				logger.error("找不到邮箱系统名:systemName="+emailRequsetDto.getSystemName());
				return  BaseMsg.failed("找不到邮箱系统名");
			}
		}catch (Exception e){
			logger.error("检验邮件系统名出错："+ JSONObject.toJSONString(emailRequsetDto));
			return  BaseMsg.failed("检验邮件系统名出错");
		}

		try {
			if(emailRequsetDto.getIsByOne().equals("1")){
				String[] emails = emailRequsetDto.getMail().split(",");
				for(int i=0;i<emails.length;i++){
					emailRequsetDto.setMail(emails[i]);
					if(emailRequsetDto.getReNum()>3){
						logger.info("该邮件超过重发次数上线：3"+emailRequsetDto.getMail());
						emailRecordMapper.insert(one);
						return  BaseMsg.failed("发送邮件消息队列失败");
					}
					String jsonData = JSONObject.toJSONString(emailRequsetDto);
					//发送消息队列
					CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
					rabbitTemplate.setConfirmCallback(mqConfirm);
					rabbitTemplate.convertAndSend(MQConstant.MSGTOPIC_EXCHANGE,MQConstant.GZGY_EMAIL_TOPIC,jsonData,correlationData);
					//rabbitTemplate.convertAndSend(MQConstant.MSGTOPIC_EXCHANGE,MQConstant.GZGY_EMAIL_TOPIC,jsonData);
				}
			}else{
				if(emailRequsetDto.getReNum()>3){
					logger.info("该邮件超过重发次数上线：3"+emailRequsetDto.getMail());
					emailRecordMapper.insert(one);
					return  BaseMsg.failed("发送邮件消息队列失败");
				}
				String jsonData = JSONObject.toJSONString(emailRequsetDto);
				//发送消息队列
				//rabbitTemplate.setConfirmCallback();
				CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
				rabbitTemplate.setConfirmCallback(mqConfirm);
				rabbitTemplate.convertAndSend(MQConstant.MSGTOPIC_EXCHANGE,MQConstant.GZGY_EMAIL_TOPIC,jsonData,correlationData);
			}
		}catch ( Exception e){
			logger.error("发送mq邮件数据失败："+JSONObject.toJSONString(emailRequsetDto));
			return  BaseMsg.failed("发送邮件消息队列失败");
		}
		return  BaseMsg.success("发送邮件消息队列成功");
	}

	@Override
	public void sendEmail(String message){
		EmailRequsetDto emailRequsetDto = JSON.parseObject(message, EmailRequsetDto.class);
		List<String> emailList = new ArrayList();

		QueryWrapper<EmailConfigEntity> wrapper = new QueryWrapper<>();
		wrapper.eq("system_name",emailRequsetDto.getSystemName());
		EmailConfigEntity emailConfigEntity = emailConfigMapper.selectOne(wrapper);

		String[] emails = emailRequsetDto.getMail().split(",");
		for(int i=0;i<emails.length;i++){
			emailList.add(emails[i]);
		}
		String subject = emailRequsetDto.getTitle();
		String content = emailRequsetDto.getContent();
		String fileJson = emailRequsetDto.getFileJson();
		String mimeType = "text/html;charset=UTF-8";
		Boolean sendSuccess = false;


		//邮件登录session
		Properties props = new Properties();

		String smtp =  emailConfigEntity.getHost(); // 设置发送邮件所用到的smtp
		String serverName = emailConfigEntity.getUsername();
		String serverPaswd = emailConfigEntity.getPassword();
		String fromEmail = emailConfigEntity.getFromName();
		String port = emailConfigEntity.getPort();

		javax.mail.Session mailSession; // 邮件会话对象
        props = java.lang.System.getProperties(); // 获得系统属性对象
        props.put("mail.smtp.host", smtp); // 设置SMTP主机
        props.put("mail.smtp.auth", "true"); // 是否到服务器用户名和密码验证
        props.put("mail.smtp.port",port);
        props.put("mail.smtp.starttls.enable","true");
        if(port.equals("465")){
            props.put("mail.smtp.starttls.required","true");
            props.put("mail.smtp.ssl.enable","true");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.port", port);
            props.put("mail.smtp.socketFactory.fallback", "false");
        }
		// 到服务器验证发送的用户名和密码是否正确
		MyEmailConfig myEmailAuther = new MyEmailConfig(
				serverName, serverPaswd);
		// 设置邮件会话
		mailSession = javax.mail.Session.getInstance(props,
				(Authenticator) myEmailAuther);

		//设置debug打印信息
		//mailSession.setDebug(true);

//		try {
//			sendSuccess = sendEmailUtils.sendmail(emailConfigEntity,subject,emailList,content,mimeType,fromEmail,mailSession);
//		}catch (Exception e){
//			logger.error("发送邮件工具失败："+sendSuccess);
//		}

        try {
            sendSuccess = sendEmailUtils.sendmail(emailConfigEntity,subject,emailList,content,mimeType,fromEmail,mailSession,fileJson);
            if (!sendSuccess){
                sendMQEmail(emailRequsetDto);
                return;
            }
            EmailRecordEntity one = new EmailRecordEntity();
            one.setId(emailRequsetDto.getId());
            one.setResult(1);
            one.setModifyTime(LocalDateTime.now());
            one.setSystemName(emailRequsetDto.getSystemName());
            one.setCreateTime(LocalDateTime.now());
            one.setMail(emailRequsetDto.getMail());
            one.setTitle(emailRequsetDto.getTitle());
            one.setContent(emailRequsetDto.getContent());
            one.setFileJson(emailRequsetDto.getFileJson());
            emailRecordMapper.insert(one);
        }catch (Exception e){
            logger.error("发送邮件工具失败："+sendSuccess);
            logger.error(e.getMessage());
            emailRequsetDto.setReNum(emailRequsetDto.getReNum()+1);
            sendMQEmail(emailRequsetDto);
        }
	}

	@Override
	public void sendEmail(EmailRequsetDto emailRequsetDto,EmailConfigEntity emailConfigEntity,Session mailSession,javax.mail.Transport transport)throws Exception{
		List<String> emailList = new ArrayList();

		String[] emails = emailRequsetDto.getMail().split(",");
		for(int i=0;i<emails.length;i++){
			emailList.add(emails[i]);
		}
		String subject = emailRequsetDto.getTitle();
		String content = emailRequsetDto.getContent();
		String fromEmail = emailConfigEntity.getFromName();

		String mimeType = "text/html;charset=UTF-8";
		Boolean sendSuccess = false;



		try {
			sendSuccess = sendEmailUtils.sendmail(emailConfigEntity,subject,emailList,content,mimeType,fromEmail,mailSession,transport);
			if (!sendSuccess)			sendMQEmail(emailRequsetDto);
			EmailRecordEntity one = new EmailRecordEntity();
			one.setId(emailRequsetDto.getId());
			one.setResult(1);
			one.setModifyTime(LocalDateTime.now());
			one.setSystemName(emailRequsetDto.getSystemName());
			one.setCreateTime(LocalDateTime.now());
			one.setMail(emailRequsetDto.getMail());
			one.setTitle(emailRequsetDto.getTitle());
			one.setContent(emailRequsetDto.getContent());
			emailRecordMapper.insert(one);
		}catch (Exception e){
			logger.error("发送邮件工具失败："+sendSuccess);
			logger.error(e.getMessage());
			synchronized (MQListener.class) {
				logger.info("发送失败,重新登录--更新MailSession");
				transport.close();
				mqListener.getMailSession(emailConfigEntity);
			}
			emailRequsetDto.setReNum(emailRequsetDto.getReNum()+1);
			sendMQEmail(emailRequsetDto);
		}
	}

	@Override
	public IPage<EmailRecordEntity> getList( EmailRequsetDto emailRequsetDto,Integer page,Integer pageSize){
		QueryWrapper<EmailRecordEntity> wrapper = new QueryWrapper<>();
		IPage<EmailRecordEntity> iPage = new Page<EmailRecordEntity>(page, pageSize);

		IPage<EmailRecordEntity> pageList = emailRecordMapper.selectPage(iPage,wrapper);
		return pageList;
	}

	@Override
	public BaseMsg sendMQEmailById(String id){
		String[] idArr = id.split(",");
		List <EmailRecordEntity> list = emailRecordMapper.selectBatchIds(Arrays.asList(idArr));
		for(EmailRecordEntity one:list){
			EmailRequsetDto emailRequsetDto = new EmailRequsetDto();
			emailRequsetDto.setSystemName(one.getSystemName());
			emailRequsetDto.setMail(one.getMail());
			emailRequsetDto.setTitle(one.getTitle());
			emailRequsetDto.setContent(one.getContent());
			emailRequsetDto.setIsByOne("0");
			try {
					emailRecordMapper.insert(one);
					emailRequsetDto.setId(one.getId());
					String jsonData = JSONObject.toJSONString(emailRequsetDto);
					//发送消息队列
					//rabbitTemplate.setConfirmCallback();
					CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
					rabbitTemplate.setConfirmCallback(mqConfirm);
					rabbitTemplate.convertAndSend(MQConstant.MSGTOPIC_EXCHANGE,MQConstant.GZGY_EMAIL_TOPIC,jsonData,correlationData);
			}catch ( Exception e){
				logger.error("发送mq邮件数据失败："+JSONObject.toJSONString(emailRequsetDto));
			}
		};
		return  BaseMsg.success("发送邮件消息队列成功");
	}

	@Override
    public BaseMsg getFailureEmailList(EmailManageDto emailManageDto) {
        QueryWrapper<EmailRecordEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("result", 0);
        Page<EmailRecordEntity> page = new Page<>(
                emailManageDto.getPage() == null ? 1 : emailManageDto.getPage(),
                emailManageDto.getPageCount() == null ? 12 : emailManageDto.getPageCount());
        IPage<EmailRecordEntity> emailRecordIPage = emailRecordMapper.selectPage(page, wrapper);
        return BaseMsg.success(emailRecordIPage, "success");
    }

	@Override
	public BaseMsg resendFailureEmail(EmailManageDto emailManageDto) {
		List<Integer> idList = emailManageDto.getIdList();
		QueryWrapper<EmailRecordEntity> wrapper;
		EmailRequsetDto emailRequsetDto;
		for (Integer id:idList) {
			wrapper = new QueryWrapper<>();
			wrapper.eq("id",id);
			EmailRecordEntity emailRecordEntity = emailRecordMapper.selectOne(wrapper);
			if (emailRecordEntity==null || emailRecordEntity.getResult().equals(1))
				continue;
			emailRequsetDto = new EmailRequsetDto();
			emailRequsetDto.setContent(emailRecordEntity.getContent());
			emailRequsetDto.setTitle(emailRecordEntity.getTitle());
			emailRequsetDto.setMail(emailRecordEntity.getMail());
			emailRequsetDto.setSystemName(emailRecordEntity.getSystemName());
			emailRequsetDto.setResult("0");
			emailRequsetDto.setIsByOne("1");
			//入队
			sendMQEmail(emailRequsetDto);
			//删除原有信息
			emailRecordMapper.deleteById(emailRecordEntity.getId());
		}
		return BaseMsg.success("success");
	}

	@Override
	public BaseMsg deleteFailureEmail(EmailManageDto emailManageDto) {
		List<Integer> idList = emailManageDto.getIdList();
		QueryWrapper<EmailRecordEntity> wrapper = new QueryWrapper<>();
		wrapper.in("id",idList);
		wrapper.eq("result",0);
		emailRecordMapper.delete(wrapper);
		return BaseMsg.success("success");
	}
}
