package com.gzgy.EmailService.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value="分页请求父类类")
public class BasePageRequest<T> implements Serializable {

    private static final long serialVersionUID = -8485858681401048006L;

    @NotNull
    @ApiModelProperty(value="是否分页")
    private Boolean ifPage ;
    @ApiModelProperty(value="页码")
    private Integer page;
    @ApiModelProperty(value="页数量")
    private Integer limit;
    @Valid
    @ApiModelProperty(value="查询条件")
    private T data;
    public Boolean getIfPage() {
        return ifPage;
    }

    public void setIfPage(Boolean ifPage) {
        this.ifPage = ifPage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
