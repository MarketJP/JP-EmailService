package com.gzgy.EmailService.modules.sms.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.gy.core.gymessage.inside.sms.dto.AliSmsRequest;
import com.gzgy.EmailService.common.base.BaseMsg;
import com.gzgy.EmailService.common.enums.MQConstant;
import com.gzgy.EmailService.modules.sms.entity.SmsSignEntity;
import com.gzgy.EmailService.modules.sms.entity.SmsTemplateEntity;
import com.gzgy.EmailService.modules.sms.mapper.SmsSignMapper;
import com.gzgy.EmailService.modules.sms.mapper.SmsTemplateMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import com.gzgy.EmailService.common.utils.SendSmsUtils;
import com.gzgy.EmailService.modules.sms.dto.AliSmsRequestDto;
import com.gzgy.EmailService.modules.sms.entity.SmsRecordEntity;
import com.gzgy.EmailService.modules.sms.mapper.SmsRecordMapper;
import com.gzgy.EmailService.modules.sms.service.ISmsRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.time.LocalDateTime;



/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangziping
 * @since 2020-03-24
 */
@Service
public class SmsRecordServiceImpl extends ServiceImpl<SmsRecordMapper, SmsRecordEntity> implements ISmsRecordService {

	private static final Logger logger = LoggerFactory.getLogger(SmsRecordServiceImpl.class);

	@Resource
	private SmsRecordMapper smsRecordMapper;

	@Resource
	private SmsTemplateMapper smsTemplateMapper;

	@Resource
	private SmsSignMapper smsSignMapper;

	@Autowired
	private SendSmsUtils sendSmsUtils;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Override
	public BaseMsg sendMQSMS(AliSmsRequestDto aliSmsRequestDto){
		// 短信实体类
		SmsRecordEntity one = new SmsRecordEntity();
		one.setTemplateParam(aliSmsRequestDto.getTemplateParam());
		one.setTemplateCode(aliSmsRequestDto.getTemplateCode());
		one.setCreateTime(LocalDateTime.now());
		one.setPhone(aliSmsRequestDto.getPhones());
		if(aliSmsRequestDto.getExtras()!=null){
			one.setExtras(aliSmsRequestDto.getExtras().toString());
		}
		one.setSystemName(aliSmsRequestDto.getSystemName());
		try{
			QueryWrapper<SmsTemplateEntity> wrapper = new QueryWrapper<>();
			wrapper.eq("template_code", aliSmsRequestDto.getTemplateCode());
			SmsTemplateEntity smsTemplateEntity = smsTemplateMapper.selectOne(wrapper);
			if(smsTemplateEntity==null){
				logger.error("找不到短信模版编码:TemplateCode="+ aliSmsRequestDto.getTemplateCode());
				return  BaseMsg.failed("找不到短信模版编码");
			}
			QueryWrapper<SmsSignEntity> wrapper2 = new QueryWrapper<>();
			wrapper2.eq("sign_name", aliSmsRequestDto.getSignName());
			SmsSignEntity smsSignEntity = smsSignMapper.selectOne(wrapper2);
			if(smsSignEntity==null){
				logger.error("找不到短信签名:SignName="+ aliSmsRequestDto.getSignName());
				return  BaseMsg.failed("找不到短信签名");
			}
		}catch (Exception e){
			logger.error("验证签名和模版编码出差："+JSONObject.toJSONString(aliSmsRequestDto));
			return  BaseMsg.failed("验证签名和模版编码出差");
		}

		try {
			smsRecordMapper.insert(one);
			aliSmsRequestDto.setId(one.getId());
			String jsonData = JSONObject.toJSONString(aliSmsRequestDto);
			//发送消息队列
			rabbitTemplate.convertAndSend(MQConstant.MSGTOPIC_EXCHANGE,MQConstant.GZGY_SMS_TOPIC,jsonData);
		}catch ( Exception e){
			logger.error("发送mq短信数据失败："+JSONObject.toJSONString(aliSmsRequestDto));
			return  BaseMsg.failed("发送短信消息队列失败");
		}
		return  BaseMsg.success("发送消息队列成功");
	}

//	@Override
//	public BaseMsg sendMQSMS(AliSmsRequest aliSmsRequest){
//		AliSmsRequestDto aliSmsRequestDto = new AliSmsRequestDto();
//		BeanUtils.copyProperties(aliSmsRequestDto,aliSmsRequest);
//
//		return sendMQSMS(aliSmsRequestDto);
//	}


	@Override
	public void sendSMS(String message){
		AliSmsRequestDto aliSmsRequestDto = JSON.parseObject(message, AliSmsRequestDto.class);
		SmsRecordEntity one = new SmsRecordEntity();

		//发送短信
		CommonResponse commonResponse =  sendSmsUtils.sendSMS(aliSmsRequestDto);
		String data = commonResponse.getData();
		JSONObject result = JSON.parseObject(data);
		one.setId(aliSmsRequestDto.getId());

		if(result.getString("Code").equals("OK")){
			one.setSmsid(result.getString("BizId"));
			one.setMessage(result.getString("Message"));
			one.setResult(1);
			one.setModifyTime(LocalDateTime.now());
		}else{//失败
			one.setFailReason(result.getString("Code"));
			one.setMessage(result.getString("Message"));
			one.setResult(0);
			one.setModifyTime(LocalDateTime.now());
		}
		// 登录实体类
		try {
			smsRecordMapper.updateById(one);
		}catch ( Exception e){
			logger.error("更新短信发送结果失败："+data);
		}

	}

}
