package com.gzgy.EmailService.modules.email.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class EmailManageDto {

    @ApiModelProperty(value = "重发id列表")
    private List<Integer> idList;

    @ApiModelProperty(value = "页码")
    private Integer page;

    @ApiModelProperty(value = "单页数量")
    private Integer pageCount;
}
