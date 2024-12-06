package com.demo.common.core.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 调度任务信息 VO
 *
 * @author DiGuaPao
 * @version 1.0
 * @since 2024-11-06 18:55:29
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
     * @since 2024-11-06 18:55:29
     */
    public static <T> PageResult<T> empty() {
        return new PageResult<>(0L);
    }

    /**
     * 返回空分页结果，指定总数
     *
     * @return 空分页结果
     * @author DiGuaPao
     * @since 2024-11-06 18:55:29
     */
    public static <T> PageResult<T> empty(Long total) {
        return new PageResult<>(total);
    }

}
