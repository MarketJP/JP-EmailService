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
 * 短信模版表
 * </p>
 *
 * @author huangziping
 * @since 2020-03-24
 */
@Data
@Accessors(chain = true)
@TableName("gy_sms_template")
public class SmsTemplateEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标识ID
     */
	@TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value = "标识ID")
	private Long id;
    /**
     * 短信模版描述
     */
    @ApiModelProperty(value = "短信模版描述")
	private String description;
    /**
     * 短信模版code
     */
    @ApiModelProperty(value = "短信模版code")
	private String templateCode;
    /**
     * 短信模版内容
     */
    @ApiModelProperty(value = "短信模版内容")
	private String templateContent;
    /**
     * 唯一查找标识
     */
    @ApiModelProperty(value = "唯一查找标识")
	private String symbol;
    /**
     * 是否公用，1是，2否
     */
    @ApiModelProperty(value = "是否公用，1是，2否")
	private Integer isCommon;
    /**
     * 备用字段
     */
    @ApiModelProperty(value = "备用字段")
	private String remark;



}
