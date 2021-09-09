package com.gzgy.EmailService.modules.notice.entity;

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
 * App推送记录
 * </p>
 *
 * @author huangziping
 * @since 2020-03-24
 */
@Data
@Accessors(chain = true)
@TableName("gy_notify_record")
public class NotifyRecordEntity implements Serializable {

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
     * 内容
     */
    @ApiModelProperty(value = "内容")
	private String content;
    /**
     * 推送标签
     */
    @ApiModelProperty(value = "推送标签")
	private String extras;
    /**
     * 推送标识
     */
    @ApiModelProperty(value = "推送标识")
	private String signature;
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
     * 推送平台类型，1极光，2华为，3小米
     */
    @ApiModelProperty(value = "推送平台类型，1极光，2华为，3小米")
	private String pushType;
    /**
     * push状态，默认成功，0成功，1失败
     */
    @ApiModelProperty(value = "push状态，默认成功，0成功，1失败")
	private Integer pushStatus;
    /**
     * 失败原因
     */
    @ApiModelProperty(value = "失败原因")
	private String failReason;
    /**
     * 目标App系统简称
     */
    @ApiModelProperty(value = "目标App系统简称")
	private String toSystemName;



}
