package com.gzgy.EmailService.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value="分页数据类")
public class BasePageDataDTO<T> implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -8485858681401048006L;

	/* 一页显示条数 */
	@ApiModelProperty(value="一页显示条数")
	private int size;
	/* 当前页 */
	@ApiModelProperty(value="当前页")
	private int current;
	/** 返回数据总数 */
	@ApiModelProperty(value="数据总数")
	private Long count;
	/** 返回数据列表 */
	@ApiModelProperty(value="数据")
	private List<T> records;

	public BasePageDataDTO() {
		super();
		records = new ArrayList<T>();
	}

	public BasePageDataDTO(List<T> records, Long count) {
		this.records = records;
		this.count = count;
	}

	public BasePageDataDTO(int size, int current, Long count, List<T> records) {
		this.size = size;
		this.current = current;
		this.count = count;
		this.records = records;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}
}
