package com.gzgy.EmailService.common.utils;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;

import java.util.UUID;

public class MQUtil {

    private MQUtil(){};

    /**
     *获取消息属性处理类:可以设置消息的请求头,messageId(uuid作为消息唯一标识)等,作为发送消息convertAndSend方法参数之一
     * @return MessagePostProcessor
     */
    public static MessagePostProcessor getMessagePostProcessor(){
        MessagePostProcessor messagePostProcessor = new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                String messageId = UUID.randomUUID().toString();
                message.getMessageProperties().setMessageId(messageId);
                return message;
            }
        };
        return messagePostProcessor;
    }

    /*//ACK或者NCK处理消息
    public void messageAckOrNack(@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
                                 @Header(value = AmqpHeaders.MESSAGE_ID, required = false) String messageId, Channel channel, MQMsgAction
                                         action, String message, Long msgRetryMaxCount, Long sleepTime) throws IOException, InterruptedException {
        if (action == MQMsgAction.ACCEPT) {//处理完消息ACK签收
            channel.basicAck(deliveryTag, false);
        } else if (action == MQMsgAction.RETRY) {//处理消息过程异常
            try {
                if (msgRetryMaxCount == -1L) {//无限次重试 ??
                    TimeUnit.SECONDS.sleep(sleepTime);
                    //踢回队列继续发送过来
                    channel.basicNack(deliveryTag, false, true);
                    return;
                }
                //控制重试次数及重试时间间隔
                long retryCount;
                if (messageId == null) {//子系统没有发messageId,messageTag则作为唯一标识
                    String messageTag = DigestUtils.md5Hex(message);
                    retryCount = redisService.incr(RsConstant.MQ_MESSAGE_TAG_KEY_PRE, messageTag);
                } else {
                    retryCount = redisService.incr(RsConstant.MQ_MESSAGE_TAG_KEY_PRE, messageId);
                }
                if (retryCount <= msgRetryMaxCount && retryCount != 0L) {//重试x次
                    TimeUnit.SECONDS.sleep(sleepTime);
                    channel.basicNack(deliveryTag, false, true);
                } else {//消息去到死信队列
                    channel.basicNack(deliveryTag, false, false);
                }
            } catch (Exception e) {//再异常,直接去死信
                channel.basicNack(deliveryTag, false, false);

            }

        }
    }*/
}
