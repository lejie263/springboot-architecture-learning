package com.fuguojie.springboot.architecture.learning.common.vodomain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * <p>页面分页参数传输模型</p>
 * PageDto
 *
 * @author fuguojie
 * @version 1.0
 * @date 2018/11/29 11:00
 * @since JDK 1.8
 */
@ToString
@Getter
@Setter
public class PageDto {

    /**
     * 当前页
     */
    @Min(1)
    private int pageNum = 1;

    /**
     * 每页大小，最小1，最大1024
     */
    @Min(1)
    @Max(1024)
    private int pageSize = 10;

    /**
     * 排序字段
     */
    private String sortKey;

    /**
     * 排序字段
     * 默认降序
     */
    private boolean sortAscend;

}
