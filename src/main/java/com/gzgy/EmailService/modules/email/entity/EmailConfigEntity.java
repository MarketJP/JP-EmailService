package com.gzgy.EmailService.modules.email.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 邮件发送记录
 * </p>
 *
 * @author huangziping
 * @since 2020-05-21
 */
@Data
@Accessors(chain = true)
@TableName("gy_email_config")
public class EmailConfigEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 标识ID
     */
	@TableId(value="id", type= IdType.AUTO)
    @ApiModelProperty(value = "标识ID")
	private Long id;
    /**
     * 系统编码
     */
    @ApiModelProperty(value = "系统编码")
	private String systemName;
    /**
     * 发送帐户
     */
    @ApiModelProperty(value = "发送帐户")
	private String username;
    /**
     * 发送编码
     */
    @ApiModelProperty(value = "发送编码")
	private String defaultEncoding;
    /**
     * 发送邮箱密码
     */
    @ApiModelProperty(value = "发送邮箱密码")
	private String password;
    /**
     * 邮件协议smtp
     */
    @ApiModelProperty(value = "邮件协议smtp")
	private String host;
    /**
     * 邮件端口
     */
    @ApiModelProperty(value = "邮件端口")
	private String port;

    /**
     * 发送人名称
     */
    @ApiModelProperty(value = "发送人名称")
    private String fromName;



}
