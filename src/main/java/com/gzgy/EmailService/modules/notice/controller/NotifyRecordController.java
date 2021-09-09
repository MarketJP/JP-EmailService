package com.gzgy.EmailService.modules.notice.controller;

import org.springframework.web.bind.annotation.*;
import com.gzgy.EmailService.modules.notice.service.INotifyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.gzgy.EmailService.modules.notice.entity.NotifyRecordEntity;
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
 * App推送记录 前端控制器
 * </p>
 *
 * @author huangziping
 * @since 2020-03-24
 */
@Api(tags="App推送记录类")
@RestController
@RequestMapping("/notice/notifyRecord")
public class NotifyRecordController {

	private static final Logger logger = LoggerFactory.getLogger(NotifyRecordController.class);

	@Autowired
	private INotifyRecordService notifyRecordService;

	@ApiOperation(value = "获取列表")
	@PostMapping("/list")
	public BaseMsg list(@RequestBody @Valid BasePageRequest<Object> request) {

		NotifyRecordEntity entity=new NotifyRecordEntity();

		QueryWrapper<NotifyRecordEntity> wrapper = new QueryWrapper<NotifyRecordEntity>(entity);
		IPage<NotifyRecordEntity> currentPage = PageUtils.buildPage(request);
		IPage<NotifyRecordEntity> selectPage = notifyRecordService.page(currentPage, wrapper);
        BasePageDataDTO<NotifyRecordEntity> dto = new BasePageDataDTO<NotifyRecordEntity>(selectPage.getRecords(), selectPage.getTotal());
		return BaseMsg.success(dto);
	}

	@ApiOperation(value = "根据id获取信息")
	@GetMapping
	public BaseMsg getInfo(@RequestParam @ApiParam(name = "id", value = "id", required = true) long id) {
		NotifyRecordEntity entity = notifyRecordService.getById(id);
		return BaseMsg.success(entity);
	}

	@ApiOperation(value = "根据id删除信息")
	@DeleteMapping
	public BaseMsg del(@RequestParam @ApiParam(name = "id", value = "id", required = true) long id) {
		if(notifyRecordService.removeById(id))
			return BaseMsg.success("删除成功");
		return BaseMsg.failed("删除失败");
	}

	@ApiOperation(value = "新增信息")
	@PostMapping
	public BaseMsg save(NotifyRecordEntity entity) {

		if (notifyRecordService.save(entity))
			return BaseMsg.success("保存成功");
		return BaseMsg.failed("保存失败");
	}

	@ApiOperation(value = "修改信息")
	@PutMapping
	public BaseMsg update(NotifyRecordEntity entity) {

		if (notifyRecordService.updateById(entity))
			return BaseMsg.success("保存成功");
		return BaseMsg.failed("保存失败");
	}


}
