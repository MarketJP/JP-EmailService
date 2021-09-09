package com.gzgy.EmailService.modules.threadtask;

import com.gzgy.EmailService.modules.email.dto.EmailRequsetDto;
import com.gzgy.EmailService.modules.email.entity.EmailConfigEntity;
import com.gzgy.EmailService.modules.email.service.impl.EmailRecordServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Session;

public class TreadEmailTask implements Runnable{

    private static Logger logger = LoggerFactory.getLogger(TreadEmailTask.class);

    private EmailRecordServiceImpl emailRecordServiceImpl;
    private EmailRequsetDto emailRequsetDto;
    private EmailConfigEntity emailConfigEntity;
    private Session mailsession;
    private javax.mail.Transport transport;

    public TreadEmailTask(EmailRequsetDto emailRequsetDto,EmailConfigEntity emailConfigEntity,EmailRecordServiceImpl emailRecordServiceImpl,Session mailsession,javax.mail.Transport transport) {
        this.emailRequsetDto = emailRequsetDto;
        this.emailRecordServiceImpl = emailRecordServiceImpl;
        this.emailConfigEntity = emailConfigEntity;
        this.mailsession = mailsession;
        this.transport = transport;
    }

    @Override
    public void run() {
        //this.emailRecordService = SpringContextsUtil.getBean(EmailRecordServiceImpl.class);
        try {
            emailRecordServiceImpl.sendEmail(emailRequsetDto,emailConfigEntity,mailsession,transport);
        }catch (Exception e){}
    }
}
