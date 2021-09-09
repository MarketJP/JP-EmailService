package com.gzgy.EmailService.modules.email.entity;

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
 * 邮件发送记录
 * </p>
 *
 * @author huangziping
 * @since 2020-03-24
 */
@Data
@Accessors(chain = true)
@TableName("gy_email_record")
public class EmailRecordEntity implements Serializable {

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
     * 发送结果(0成功,非0失败)
     */
    @ApiModelProperty(value = "发送结果(0成功,非0失败)")
	private Integer result;
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

    @ApiModelProperty(value = "附件json")
    private String fileJson;



}
