package com.csair.sc.common.core.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 车辆出入记录VO
 *
 * @author DiGuaPao
 * @version 1.0
 * @since 2024-11-22 11:12:50
 */
@Data
@Schema(description = "分页结果")
public final class PageResult<T> implements Serializable {
    /**
     * 数据
     */
    @Schema(description = "数据", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<T> list;
    /**
     * 总数
     */
    @Schema(description = "总数", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long total;

    public PageResult() {
    }

    public PageResult(List<T> list, Long total) {
        this.list = list;
        this.total = total;
    }

    public PageResult(Long total) {
        this.list = new ArrayList<>();
        this.total = total;
    }

    /**
     * 返回空分页结果，不指定总数
     *
     * @return 空分页结果
     * @author DiGuaPao
     * @since 2024-11-22 11:12:50
     */
    public static <T> PageResult<T> empty() {
        return new PageResult<>(0L);
    }

    /**
     * 返回空分页结果，指定总数
     *
     * @return 空分页结果
     * @author DiGuaPao
     * @since 2024-11-22 11:12:50
     */
    public static <T> PageResult<T> empty(Long total) {
        return new PageResult<>(total);
    }

}
