package com.gzgy.EmailService.modules.sms.service;

//import com.gy.core.gymessage.inside.sms.dto.AliSmsRequest;
import com.gzgy.EmailService.common.base.BaseMsg;
import com.gzgy.EmailService.modules.sms.dto.AliSmsRequestDto;
import com.gzgy.EmailService.modules.sms.entity.SmsRecordEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangziping
 * @since 2020-03-24
 */
public interface ISmsRecordService extends IService<SmsRecordEntity> {
	public BaseMsg sendMQSMS(AliSmsRequestDto aliSmsRequestDto);
//	public BaseMsg sendMQSMS(AliSmsRequest aliSmsRequest);
	public void sendSMS(String message);
}
