package com.gzgy.EmailService.modules.sms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 *
 * </p>
 *
 * @author huangziping
 * @since 2020-03-24
 */
@Data
@Accessors(chain = true)
@TableName("gy_sms_record")
public class SmsRecordEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标识ID
     */
	@TableId(value="id", type= IdType.AUTO)
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
	private String phone;

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
     * 发送结果(0成功,非0失败)
     */
    @ApiModelProperty(value = "发送结果(0成功,非0失败)")
	private Integer result;
    /**
     * 结果消息
     */
    @ApiModelProperty(value = "结果消息")
	private String message;
    /**
     * 短信ID(短信平台返回的ID)
     */
    @ApiModelProperty(value = "短信ID(短信平台返回的ID)")
	private String smsid;
    /**
     * 创建人ID
     */
    @ApiModelProperty(value = "创建人ID")
	private Long creator;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;
    /**
     * 修改人ID
     */
    @ApiModelProperty(value = "修改人ID")
	private Long modifier;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
	private LocalDateTime modifyTime;
    /**
     * 额外字段，存放自定义信息
     */
    @ApiModelProperty(value = "额外字段，存放自定义信息")
	private String extras;
    /**
     * 失败原因
     */
    @ApiModelProperty(value = "失败原因")
	private String failReason;



}
