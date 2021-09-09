package com.gzgy.EmailService.modules.notice.controller;

import org.springframework.web.bind.annotation.*;
import com.gzgy.EmailService.modules.notice.service.INotifyHwIdentificationService;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.gzgy.EmailService.modules.notice.entity.NotifyHwIdentificationEntity;
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
 * 推送设备标识表（标识各业务系统与被推送手机的对应关系） 前端控制器
 * </p>
 *
 * @author huangziping
 * @since 2020-03-24
 */
@Api(tags="推送设备标识表（标识各业务系统与被推送手机的对应关系）类")
@RestController
@RequestMapping("/notice/notifyHwIdentification")
public class NotifyHwIdentificationController {

	private static final Logger logger = LoggerFactory.getLogger(NotifyHwIdentificationController.class);

	@Autowired
	private INotifyHwIdentificationService notifyHwIdentificationService;

	@ApiOperation(value = "获取列表")
	@PostMapping("/list")
	public BaseMsg list(@RequestBody @Valid BasePageRequest<Object> request) {

		NotifyHwIdentificationEntity entity=new NotifyHwIdentificationEntity();

		QueryWrapper<NotifyHwIdentificationEntity> wrapper = new QueryWrapper<NotifyHwIdentificationEntity>(entity);
		IPage<NotifyHwIdentificationEntity> currentPage = PageUtils.buildPage(request);
		IPage<NotifyHwIdentificationEntity> selectPage = notifyHwIdentificationService.page(currentPage, wrapper);
        BasePageDataDTO<NotifyHwIdentificationEntity> dto = new BasePageDataDTO<NotifyHwIdentificationEntity>(selectPage.getRecords(), selectPage.getTotal());
		return BaseMsg.success(dto);
	}

	@ApiOperation(value = "根据id获取信息")
	@GetMapping
	public BaseMsg getInfo(@RequestParam @ApiParam(name = "id", value = "id", required = true) long id) {
		NotifyHwIdentificationEntity entity = notifyHwIdentificationService.getById(id);
		return BaseMsg.success(entity);
	}

	@ApiOperation(value = "根据id删除信息")
	@DeleteMapping
	public BaseMsg del(@RequestParam @ApiParam(name = "id", value = "id", required = true) long id) {
		if(notifyHwIdentificationService.removeById(id))
			return BaseMsg.success("删除成功");
		return BaseMsg.failed("删除失败");
	}

	@ApiOperation(value = "新增信息")
	@PostMapping
	public BaseMsg save(NotifyHwIdentificationEntity entity) {

		if (notifyHwIdentificationService.save(entity))
			return BaseMsg.success("保存成功");
		return BaseMsg.failed("保存失败");
	}

	@ApiOperation(value = "修改信息")
	@PutMapping
	public BaseMsg update(NotifyHwIdentificationEntity entity) {

		if (notifyHwIdentificationService.updateById(entity))
			return BaseMsg.success("保存成功");
		return BaseMsg.failed("保存失败");
	}


}
