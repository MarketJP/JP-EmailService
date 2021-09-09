package com.gzgy.EmailService.common.enums;

/**
 * 对消息进行ACK或者NACK的控制标识
 */
enum MQMsgAction {
  ACCEPT,  // 处理成功
  RETRY,   // 可以重试的错误
}
