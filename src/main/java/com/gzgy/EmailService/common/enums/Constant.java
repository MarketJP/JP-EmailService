package com.gzgy.EmailService.common.enums;

public class Constant {

    // Redis 存储前缀 SMS:短信
    public static final String SMS = "iDoctor_IC_SMS:";
    // Redis 存储前缀 请求限制
    public static final String REQUEST_LIMIT = "iDoctor_REQ_LIMIT:";
    // Redis 存储前缀 检测授权token
    public static final String AUTH_TOKEN = "iDoctor_AuthToken:";


    // 短信过期时间
    public static final int MESSAGE_TIMEOUT = 5;

    /**userType;//用户类型,1-医生，2-护士，3-管理员，4-医技人员，5-药师，9-院长*/
    public static final int USER_TYPE_DOCTOR = 1;

}
