package com.gzgy.EmailService.modules.listener;


import com.alibaba.fastjson.JSON;
import com.gzgy.EmailService.common.config.MyEmailConfig;
import com.gzgy.EmailService.common.enums.MQConstant;
import com.gzgy.EmailService.modules.email.dto.EmailRequsetDto;
import com.gzgy.EmailService.modules.email.entity.EmailConfigEntity;
import com.gzgy.EmailService.modules.email.mapper.EmailConfigMapper;
import com.gzgy.EmailService.modules.email.service.impl.EmailRecordServiceImpl;
import com.gzgy.EmailService.modules.sms.service.impl.SmsRecordServiceImpl;
import com.gzgy.EmailService.modules.wechat.service.IWechatService;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.mail.Authenticator;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Component
public class MQListener {

    private static Logger logger = LoggerFactory.getLogger(MQListener.class);
    @Autowired
    private SmsRecordServiceImpl smsRecordServiceImpl;
    @Autowired
    private EmailRecordServiceImpl emailRecordServiceImpl;

    @Autowired
    private IWechatService iWechatService;

    @Autowired
    private EmailConfigMapper emailConfigMapper;

    //多线程
    private static ExecutorService service = Executors.newFixedThreadPool(2);

    private static volatile Map<String,javax.mail.Session> mailSessionMap = new HashMap<>();
    private static volatile Map<String,javax.mail.Transport> mailTransportMap = new HashMap<>();



    //短信任务接收
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = MQConstant.MSGTOPIC_EXCHANGE, durable = "true"
                    , type = "topic"
            ),
            value = @Queue(value = MQConstant.GZGY_SMS_TOPIC, durable = "true"),
            key = MQConstant.GZGY_SMS_TOPIC
    ))
    public void handleMsgUpdateMsgQueue(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, @Header(value = AmqpHeaders.MESSAGE_ID, required = false) String messageId, Channel channel) throws Exception {
        logger.info("接收短信任务:{}", message);
        try {
            smsRecordServiceImpl.sendSMS(message);
        } catch (Exception e) {
            logger.error("error: {}", e.getMessage(), e);
            logger.error("处理短信任务异常,消息:{}", message);
        } finally {
            channel.basicAck(deliveryTag, false);
        }

    }

    //邮件任务接收
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = MQConstant.MSGTOPIC_EXCHANGE, durable = "true"
                    , type = "topic"
            ),
            value = @Queue(value = MQConstant.GZGY_EMAIL_TOPIC, durable = "true"),
            key = MQConstant.GZGY_EMAIL_TOPIC
    ))
    public void handleEmailUpdateMsgQueue(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, @Header(value = AmqpHeaders.MESSAGE_ID, required = false) String messageId, Channel channel) throws Exception {
        EmailRequsetDto emailRequsetDto = JSON.parseObject(message, EmailRequsetDto.class);
        try {
//            Properties props = new Properties();
            logger.info("接收邮件任务:{}", emailRequsetDto.getMail());
//            QueryWrapper<EmailConfigEntity> wrapper = new QueryWrapper<>();
//            wrapper.eq("system_name",emailRequsetDto.getSystemName());
//            EmailConfigEntity emailConfigEntity = emailConfigMapper.selectOne(wrapper);
////
//
//            //javax.mail.Session mailSession;
//            javax.mail.Session mailSession = this.mailSessionMap.get(emailRequsetDto.getSystemName());
//            if(mailSession==null){
//                logger.info("进入登录邮箱流程");
//                synchronized (MQListener.class) {
//                    getMailSession(emailConfigEntity);
//                }
//            }
            //getMailSession(emailConfigEntity);

            //多进程发送
            //service.execute(new TreadEmailTask(emailRequsetDto,emailConfigEntity,emailRecordServiceImpl,this.mailSessionMap.get(emailRequsetDto.getSystemName()),this.mailTransportMap.get(emailRequsetDto.getSystemName())));
            //单线程
            emailRecordServiceImpl.sendEmail(message);
        } catch (Exception e) {
            logger.error("处理邮件任务异常,消息:{}",  emailRequsetDto.getMail());
            logger.error("error: {}", e.getMessage());
        } finally {
            logger.info("处理：", message);
            channel.basicAck(deliveryTag, false);
        }

    }
    public synchronized void getMailSession(EmailConfigEntity emailConfigEntity)throws Exception{
        Properties props = new Properties();

        String smtp =  emailConfigEntity.getHost(); // 设置发送邮件所用到的smtp
        String serverName = emailConfigEntity.getUsername();
        String serverPaswd = emailConfigEntity.getPassword();
        String port = emailConfigEntity.getPort();

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
        javax.mail.Session mailSession;
        // 设置邮件会话
        mailSession = javax.mail.Session.getInstance(props,
                (Authenticator) myEmailAuther);

        //设置debug打印信息
        //mailSession.setDebug(true);
        javax.mail.Transport transport = mailSession.getTransport("smtp");
        this.mailTransportMap.put(emailConfigEntity.getSystemName(),transport);
        this.mailSessionMap.put(emailConfigEntity.getSystemName(),mailSession);
    }

    //微信推送任务接收
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = MQConstant.MSGTOPIC_EXCHANGE, durable = "true"
                    , type = "topic"
            ),
            value = @Queue(value = MQConstant.GZGY_WECHAT_TOPIC, durable = "true"),
            key = MQConstant.GZGY_WECHAT_TOPIC
    ))
    public void handleWeChatUpdateMsgQueue(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, @Header(value = AmqpHeaders.MESSAGE_ID, required = false) String messageId, Channel channel) throws Exception {
        logger.info("接收微信推送任务:{}", message);
        try {
            //iWechatService.sendWeChat(message);
        } catch (Exception e) {
            logger.error("error: {}", e.getMessage(), e);
            logger.error("处理微信推送任务异常,消息:{}", message);
        } finally {
            channel.basicAck(deliveryTag, false);
        }

    }

}
