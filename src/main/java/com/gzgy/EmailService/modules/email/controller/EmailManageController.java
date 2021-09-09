package com.gzgy.EmailService.modules.email.controller;


import com.gzgy.EmailService.common.base.BaseMsg;
import com.gzgy.EmailService.modules.email.dto.EmailManageDto;
import com.gzgy.EmailService.modules.email.service.IEmailRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags="邮件记录管理类")
@RestController
@RequestMapping("/email/manage")
@CrossOrigin
public class EmailManageController {

    @Autowired
    private IEmailRecordService emailRecordService;

    @ApiOperation(value = "获取发送失败邮件列表")
    @PostMapping("/getFailureEmailList")
    public BaseMsg getFailureEmailList(@RequestBody EmailManageDto emailManageDto) {
         return emailRecordService.getFailureEmailList(emailManageDto);
    }

    @ApiOperation(value = "重新发送失败邮件")
    @PostMapping("/resendFailureEmail")
    public BaseMsg resendFailureEmail(@RequestBody EmailManageDto emailManageDto) {
        return emailRecordService.resendFailureEmail(emailManageDto);
    }

    @ApiOperation(value = "删除发送失败邮件")
    @PostMapping("/deleteFailureEmail")
    public BaseMsg deleteFailureEmail(@RequestBody EmailManageDto emailManageDto) {
        return emailRecordService.deleteFailureEmail(emailManageDto);
    }
}
