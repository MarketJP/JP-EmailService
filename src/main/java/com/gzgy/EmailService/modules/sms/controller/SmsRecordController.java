package com.gzgy.EmailService.modules.sms.controller;

import org.springframework.web.bind.annotation.*;
import com.gzgy.EmailService.modules.sms.service.ISmsRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.gzgy.EmailService.modules.sms.entity.SmsRecordEntity;
import javax.validation.Valid;
import com.gzgy.EmailService.common.base.BasePageRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gzgy.EmailService.common.utils.PageUtils;
import com.gzgy.EmailService.common.base.BaseMsg;
import com.gzgy.EmailService.common.base.BasePageDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping("/sms/smsRecord")
public class SmsRecordController {

	private static final Logger logger = LoggerFactory.getLogger(SmsRecordController.class);

	@Autowired
	private ISmsRecordService smsRecordService;

	@ApiOperation(value = "获取列表")
	@PostMapping("/list")
	public BaseMsg list(@RequestBody @Valid BasePageRequest<Object> request) {

		SmsRecordEntity entity=new SmsRecordEntity();

		QueryWrapper<SmsRecordEntity> wrapper = new QueryWrapper<SmsRecordEntity>(entity);
		IPage<SmsRecordEntity> currentPage = PageUtils.buildPage(request);
		IPage<SmsRecordEntity> selectPage = smsRecordService.page(currentPage, wrapper);
        BasePageDataDTO<SmsRecordEntity> dto = new BasePageDataDTO<SmsRecordEntity>(selectPage.getRecords(), selectPage.getTotal());
		return BaseMsg.success(dto);
	}

	@ApiOperation(value = "根据id获取信息")
	@GetMapping
	public BaseMsg getInfo(@RequestParam @ApiParam(name = "id", value = "id", required = true) long id) {
		SmsRecordEntity entity = smsRecordService.getById(id);
		return BaseMsg.success(entity);
	}

	@ApiOperation(value = "根据id删除信息")
	@DeleteMapping
	public BaseMsg del(@RequestParam @ApiParam(name = "id", value = "id", required = true) long id) {
		if(smsRecordService.removeById(id))
			return BaseMsg.success("删除成功");
		return BaseMsg.failed("删除失败");
	}

	@ApiOperation(value = "新增信息")
	@PostMapping
	public BaseMsg save(@RequestBody SmsRecordEntity entity) {

		if (smsRecordService.save(entity))
			return BaseMsg.success("保存成功");
		return BaseMsg.failed("保存失败");
	}

	@ApiOperation(value = "修改信息")
	@PutMapping
	public BaseMsg update( @RequestBody SmsRecordEntity entity) {

		if (smsRecordService.updateById(entity))
			return BaseMsg.success("保存成功");
		return BaseMsg.failed("保存失败");
	}


}
