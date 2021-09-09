package com.gzgy.EmailService.common.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.gzgy.EmailService.modules.sms.controller.SmsController;
import com.gzgy.EmailService.modules.sms.dto.AliSmsRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SendSmsUtils {
    private static final Logger logger = LoggerFactory.getLogger(SmsController.class);

    //产品域名,开发者无需替换
    @Value("${aliyun.sms.domain}")
    private String domain;

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    @Value("${aliyun.sms.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.sms.accessKeySecret}")
    private String accessKeySecret;

    public CommonResponse sendSMS(AliSmsRequestDto dto) {

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(domain);
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");

        //对应入参
        request.putQueryParameter("PhoneNumbers", dto.getPhones());
        request.putQueryParameter("SignName", dto.getSignName());
        request.putQueryParameter("TemplateCode", dto.getTemplateCode());
        request.putQueryParameter("TemplateParam", dto.getTemplateParam());
//        request.putQueryParameter("PhoneNumbers", "15013059002");
//        request.putQueryParameter("TemplateCode", "SMS_188040154");
//        request.putQueryParameter("TemplateParam", "{ \"code\":\"5663334\"}");
        CommonResponse response = new CommonResponse();
        try {
            response = client.getCommonResponse(request);
           logger.info(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return response;
    }
}
