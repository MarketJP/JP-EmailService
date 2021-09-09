package com.gzgy.EmailService.modules.email.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EmailRequsetDto {
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
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;
    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String content;
    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String mail;

    /**
     * 发送结果
     */
    @ApiModelProperty(value = "发送结果")
    private String result;
    /**
     * 多个邮箱单独发送
     */
    @ApiModelProperty(value = "多个邮箱单独发送开关")
    private String isByOne;

    /**
     * 重发次数
     */
    @ApiModelProperty(value = "重发次数")
    private int reNum;

    /**
     * 附件json
     */
    @ApiModelProperty(value = "附件Json")
    private String fileJson;
}
