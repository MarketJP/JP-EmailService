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
 * 推送设备标识表（标识各业务系统与被推送手机的对应关系）
 * </p>
 *
 * @author huangziping
 * @since 2020-03-24
 */
@Data
@Accessors(chain = true)
@TableName("gy_notify_hw_identification")
public class NotifyHwIdentificationEntity implements Serializable {

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
     * 设备（手机类型）简称
     */
    @ApiModelProperty(value = "设备（手机类型）简称")
	private String diviceName;
    /**
     * 关联的标识
     */
    @ApiModelProperty(value = "关联的标识")
	private String associationIdentifier;
    /**
     * 唯一的识别码
     */
    @ApiModelProperty(value = "唯一的识别码")
	private String identCode;
    /**
     * 是否已经失效,0有效，1失效
     */
    @ApiModelProperty(value = "是否已经失效,0有效，1失效")
	private String isValidate;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;
    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
	private LocalDateTime modifyTime;



}
