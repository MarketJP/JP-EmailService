package com.gzgy.EmailService.modules.sms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 短信签名表
 * </p>
 *
 * @author huangziping
 * @since 2020-03-24
 */
@Data
@Accessors(chain = true)
@TableName("gy_sms_sign")
public class SmsSignEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标识ID
     */
	@TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value = "标识ID")
	private Long id;
    /**
     * 短信签名
     */
    @ApiModelProperty(value = "短信签名")
	private String signName;
    /**
     * 所属系统,common代表所有系统共用
     */
    @ApiModelProperty(value = "所属系统,common代表所有系统共用")
	private String systemName;
    /**
     * 所属平台，如common代表所有平台共用,app,web,wechat(微信公众号)
     */
    @ApiModelProperty(value = "所属平台，如common代表所有平台共用,app,web,wechat(微信公众号)")
	private String platform;
    /**
     * 是否公用，1是，0否
     */
    @ApiModelProperty(value = "是否公用，1是，0否")
	private Integer isCommon;
    /**
     * 备用字段
     */
    @ApiModelProperty(value = "备用字段")
	private String remark;



}
