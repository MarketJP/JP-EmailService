package com.gzgy.EmailService.modules.sms.controller;

import com.gzgy.EmailService.modules.sms.service.ISmsRecordService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huangziping
 * @since 2020-03-24
 */
@Api(tags="短信类")
@RestController
@RequestMapping("/sms/do")
public class SmsController {

	private static final Logger logger = LoggerFactory.getLogger(SmsController.class);

	@Autowired
	private ISmsRecordService smsRecordService;
	/*
	 {
	"phones":"15013059002",
	"templateParam":"{ \"code\":\"5201314\"}",
	"templateCode":"SMS_188040154",
	"signName":"国控医疗管理",
	"systemName":"gypatient"
	}
	 */
//	@ApiOperation(value = "发送短信验证码")
//	@PostMapping("/send")
//	public BaseMsg senSMS(@RequestBody AliSmsRequestDto request) {
//
//		BaseMsg result = smsRecordService.sendMQSMS(request);
//		return result;
//	}


}
