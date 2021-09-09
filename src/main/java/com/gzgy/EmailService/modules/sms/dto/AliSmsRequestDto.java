package com.gzgy.EmailService.modules.sms.dto;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Map;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AliSmsRequestDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 标识ID
     */
    @ApiModelProperty(value = "标识ID")
    private Long id;
    /**
     * 系统简称
     */
    @ApiModelProperty(value = "系统简称")
    private String systemName;
    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String phones;

    /**
     * 短信模版参数
     */
    @ApiModelProperty(value = "短信模版参数")
    private String templateParam;
    /**
     * 短信模版编码
     */
    @ApiModelProperty(value = "短信模版编码")
    private String templateCode;

    /**
     * 短信签名
     */
    @ApiModelProperty(value = "短信签名")
    private String signName;
    /**
     * 备用字段
     */
    @ApiModelProperty(value = "备用字段")
    private Map<String, String> map;
    /**
     * 备用字段
     */
    @ApiModelProperty(value = "备用字段")
    private Map<String, Object> extras;

//    public String getSystemName() {
//        return this.systemName;
//    }
//
//    public void setSystemName(String systemName) {
//        this.systemName = systemName;
//    }
//
//    public String getPhones() {
//        return this.phones;
//    }
//
//    public void setPhones(String phones) {
//        this.phones = phones;
//    }
//
//    public Map<String, String> getMap() {
//        return this.map;
//    }
//
//    public String getTemplateCode() {
//        return this.templateCode;
//    }
//
//    public void setTemplateCode(String templateCode) {
//        this.templateCode = templateCode;
//    }
//
//    public void setMap(Map<String, String> map) {
//        this.map = map;
//    }
//
//    public Map<String, Object> getExtras() {
//        return this.extras;
//    }
//
//    public void setExtras(Map<String, Object> extras) {
//        this.extras = extras;
//    }
//
//    public String getSignName() {
//        return signName;
//    }
//
//    public void setSignName(String signName) {
//        this.signName = signName;
//    }
//
//    public String getTemplateParam() {
//        return templateParam;
//    }
//
//    public void setTemplateParam(String templateParam) {
//        this.templateParam = templateParam;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
}
