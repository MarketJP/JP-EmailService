package com.gzgy.EmailService.common.enums;

/**
 * MQ常量
 */
public class MQConstant {

    // Rabbit 队列
    public static final String MSGTOPIC_EXCHANGE = "GY_MSG_EXCHANGE";
    //队列名
    public static final String GZGY_SMS_TOPIC = "gzgy.sms.topic";//处理短信队列
    public static final String GZGY_WECHAT_TOPIC = "gzgy.wechat.topic";//处理微信推送队列
    public static final String GZGY_EMAIL_TOPIC = "gzgy.email.topic";//处理邮箱队列
    public static final String GZGY_NOTICE_TOPIC = "gzgy.notice.topic";//处理app推送队列

}
