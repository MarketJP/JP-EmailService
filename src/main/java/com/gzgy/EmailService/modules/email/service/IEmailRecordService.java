package com.gzgy.EmailService.modules.email.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gzgy.EmailService.common.base.BaseMsg;
import com.gzgy.EmailService.modules.email.dto.EmailManageDto;
import com.gzgy.EmailService.modules.email.dto.EmailRequsetDto;
import com.gzgy.EmailService.modules.email.entity.EmailConfigEntity;
import com.gzgy.EmailService.modules.email.entity.EmailRecordEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.mail.Session;

/**
 * <p>
 * 邮件发送记录 服务类
 * </p>
 *
 * @author huangziping
 * @since 2020-03-24
 */
public interface IEmailRecordService extends IService<EmailRecordEntity> {
    public BaseMsg sendMQEmail(EmailRequsetDto emailRequsetDto);
    public void sendEmail(String message);
    public void sendEmail(EmailRequsetDto emailRequsetDto, EmailConfigEntity emailConfigEntity, Session session,javax.mail.Transport transport)throws Exception;
    public IPage<EmailRecordEntity> getList(EmailRequsetDto emailRequsetDto,Integer page ,Integer pageSize);
    public BaseMsg sendMQEmailById(String id);
    //获取所有未发送成功邮件列表
    public BaseMsg getFailureEmailList(EmailManageDto emailManageDto);
    //根据id列表重新发送邮件
    public BaseMsg resendFailureEmail(EmailManageDto emailManageDto);
    //根据id列表删除发送邮件
    public BaseMsg deleteFailureEmail(EmailManageDto emailManageDto);
}
