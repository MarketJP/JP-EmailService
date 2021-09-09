package com.gzgy.EmailService.modules.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class MQConfirm implements RabbitTemplate.ConfirmCallback {

    private static Logger logger = LoggerFactory.getLogger(MQListener.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);            //指定 ConfirmCallback
    }
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("CallBackConfirm UUID: " + correlationData.getId());

        if(ack) {
            System.out.println("CallBackConfirm 消息消费成功！");
        }else {
            System.out.println("CallBackConfirm 消息消费失败！");
        }

        if(cause!=null) {
            System.out.println("CallBackConfirm Cause: " + cause);
        }
    }
}
