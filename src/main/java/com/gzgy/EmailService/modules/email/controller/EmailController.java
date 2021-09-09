package com.gzgy.EmailService.modules.email.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gzgy.EmailService.common.base.BaseMsg;
import com.gzgy.EmailService.modules.email.dto.EmailRequsetDto;
import com.gzgy.EmailService.modules.email.entity.EmailRecordEntity;
import com.gzgy.EmailService.modules.email.service.IEmailRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 邮件发送记录 前端控制器
 * </p>
 *
 * @author huangziping
 * @since 2020-03-24
 */
@Api(tags="邮件发送类")
@RestController
@RequestMapping("/email/do")
public class EmailController {

	private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

	@Autowired
	private IEmailRecordService emailRecordService;

	@ApiOperation(value = "发送邮件")
	@PostMapping("/send")
	public BaseMsg send(@RequestBody EmailRequsetDto emailRequsetDto) {
		BaseMsg result = emailRecordService.sendMQEmail(emailRequsetDto);
		return  result;
	}

	@ApiOperation(value = "邮件列表")
	@PostMapping("/getList")
	public BaseMsg getlist(@RequestBody EmailRequsetDto emailRequsetDto,
						   @RequestParam(name="page", defaultValue="1") Integer page,
						   @RequestParam(name="pageSzie", defaultValue="1") Integer pageSzie
	) {
		IPage<EmailRecordEntity> result = emailRecordService.getList(emailRequsetDto,page,pageSzie);
		return  BaseMsg.success(result);
	}

	@ApiOperation(value = "列表重发")
	@PostMapping("/listRepeat")
	public BaseMsg listRepeat(@RequestBody
						   @RequestParam(name="id", defaultValue="1") String id
	) {
		BaseMsg result = emailRecordService.sendMQEmailById(id);
		return  result;
	}


}
