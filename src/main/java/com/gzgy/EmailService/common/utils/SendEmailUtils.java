package com.gzgy.EmailService.common.utils;


import com.alibaba.fastjson.JSON;
import com.gzgy.EmailService.modules.email.dto.FileDto;
import com.gzgy.EmailService.modules.email.entity.EmailConfigEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.activation.DataHandler;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class SendEmailUtils {
    private static final Logger logger = LoggerFactory.getLogger(SendEmailUtils.class);


    public synchronized static Boolean sendmail(EmailConfigEntity emailConfigEntity, String subject,List<String> to,
                                String text, String mimeType,String fromEmail,Session mailSession,javax.mail.Transport transport) throws Exception {

        // MIME邮件对象
        javax.mail.internet.MimeMessage mimeMsg;
        // 设置传输协议
        //javax.mail.Transport transport = mailSession.getTransport("smtp");
        // 设置from、to等信息
        mimeMsg = new javax.mail.internet.MimeMessage(mailSession);

        if (!fromEmail.isEmpty()) {
            InternetAddress sentFrom = new InternetAddress(fromEmail);
            mimeMsg.setFrom(sentFrom); // 设置发送人地址
        }

        InternetAddress[] sendTo = new InternetAddress[to.size()];
        for (int i = 0; i < to.size(); i++) {
            System.out.println("发送到:" + to.get(i));
            sendTo[i] = new InternetAddress(to.get(i));
        }

        mimeMsg.setRecipients(javax.mail.internet.MimeMessage.RecipientType.TO,
                sendTo);
        mimeMsg.setSubject(subject, emailConfigEntity.getDefaultEncoding());

        MimeBodyPart messageBodyPart1 = new MimeBodyPart();
        messageBodyPart1.setContent(text, mimeType);

        Multipart multipart = new MimeMultipart();// 附件传输格式
        multipart.addBodyPart(messageBodyPart1);

//        for (int i = 0; i < filenames.length; i++) {
//            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
//            // 选择出每一个附件名
//            String filename = filenames[i].split(",")[0];
//            System.out.println("附件名：" + filename);
//            String displayname = filenames[i].split(",")[1];
//            // 得到数据源
//            FileDataSource fds = new FileDataSource(filename);
//            // 得到附件本身并至入BodyPart
//            messageBodyPart2.setDataHandler(new DataHandler(fds));
//            // 得到文件名同样至入BodyPart
//            messageBodyPart2.setFileName(MimeUtility.encodeText(displayname));
//            multipart.addBodyPart(messageBodyPart2);
//        }
        mimeMsg.setContent(multipart);
        // 设置信件头的发送日期
        mimeMsg.setSentDate(new Date());
        mimeMsg.saveChanges();
        // 发送邮件
        transport.send(mimeMsg);
        //试试线程池
        //transport.close();
        return true;
    }

    public synchronized static Boolean sendmail(EmailConfigEntity emailConfigEntity, String subject,List<String> to,
                                                String text, String mimeType,String fromEmail,Session mailSession) throws Exception {

        // MIME邮件对象
        javax.mail.internet.MimeMessage mimeMsg;
        // 设置传输协议
        javax.mail.Transport transport = mailSession.getTransport("smtp");
        // 设置from、to等信息
        mimeMsg = new javax.mail.internet.MimeMessage(mailSession);

        if (!fromEmail.isEmpty()) {
            InternetAddress sentFrom = new InternetAddress(fromEmail);
            mimeMsg.setFrom(sentFrom); // 设置发送人地址
        }

        InternetAddress[] sendTo = new InternetAddress[to.size()];
        for (int i = 0; i < to.size(); i++) {
            System.out.println("发送到:" + to.get(i));
            sendTo[i] = new InternetAddress(to.get(i));
        }

        mimeMsg.setRecipients(javax.mail.internet.MimeMessage.RecipientType.TO,
                sendTo);
        mimeMsg.setSubject(subject, emailConfigEntity.getDefaultEncoding());

        MimeBodyPart messageBodyPart1 = new MimeBodyPart();
        messageBodyPart1.setContent(text, mimeType);

        Multipart multipart = new MimeMultipart();// 附件传输格式
        multipart.addBodyPart(messageBodyPart1);

//        for (int i = 0; i < filenames.length; i++) {
//            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
//            // 选择出每一个附件名
//            String filename = filenames[i].split(",")[0];
//            System.out.println("附件名：" + filename);
//            String displayname = filenames[i].split(",")[1];
//            // 得到数据源
//            FileDataSource fds = new FileDataSource(filename);
//            // 得到附件本身并至入BodyPart
//            messageBodyPart2.setDataHandler(new DataHandler(fds));
//            // 得到文件名同样至入BodyPart
//            messageBodyPart2.setFileName(MimeUtility.encodeText(displayname));
//            multipart.addBodyPart(messageBodyPart2);
//        }
        mimeMsg.setContent(multipart);
        // 设置信件头的发送日期
        mimeMsg.setSentDate(new Date());
        mimeMsg.saveChanges();
        // 发送邮件
        transport.send(mimeMsg);
        //试试线程池
        //transport.close();
        return true;
    }

    public static Boolean sendmail(EmailConfigEntity emailConfigEntity, String subject,List<String> to,
                                                String text, String mimeType,String fromEmail,Session mailSession, String fileJson) throws Exception {

        // MIME邮件对象
        javax.mail.internet.MimeMessage mimeMsg;
        // 设置传输协议
        javax.mail.Transport transport = mailSession.getTransport("smtp");
        // 设置from、to等信息
        mimeMsg = new javax.mail.internet.MimeMessage(mailSession);

        if (!fromEmail.isEmpty()) {
            InternetAddress sentFrom = new InternetAddress(fromEmail);
            mimeMsg.setFrom(sentFrom); // 设置发送人地址
        }

        InternetAddress[] sendTo = new InternetAddress[to.size()];
        for (int i = 0; i < to.size(); i++) {
            System.out.println("发送到:" + to.get(i));
            sendTo[i] = new InternetAddress(to.get(i));
        }

        mimeMsg.setRecipients(javax.mail.internet.MimeMessage.RecipientType.TO,
                sendTo);
        mimeMsg.setSubject(subject, emailConfigEntity.getDefaultEncoding());

        MimeBodyPart messageBodyPart1 = new MimeBodyPart();
        messageBodyPart1.setContent(text, mimeType);

        Multipart multipart = new MimeMultipart();// 附件传输格式
        multipart.addBodyPart(messageBodyPart1);
        List<FileDto> fileList = new ArrayList<>();
        if (fileJson != null)
            fileList = JSON.parseArray(fileJson, FileDto.class);

        for (FileDto file : fileList) {
            MimeBodyPart messageBodyPart2 = new MimeBodyPart();
            URL url = new URL(file.getPath());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(60 * 1000);
            connection.setReadTimeout(30 * 1000);

            // 得到附件本身并至入BodyPart
            if(connection.getResponseCode()==200){
                messageBodyPart2.setDataHandler(new DataHandler(url));
                messageBodyPart2.setFileName(MimeUtility.encodeText(file.getName()));
                multipart.addBodyPart(messageBodyPart2);
            }
        }

        mimeMsg.setContent(multipart);
        // 设置信件头的发送日期
        mimeMsg.setSentDate(new Date());
        mimeMsg.saveChanges();
        // 发送邮件
        transport.send(mimeMsg);
        //试试线程池
        transport.close();
        return true;
    }
}
