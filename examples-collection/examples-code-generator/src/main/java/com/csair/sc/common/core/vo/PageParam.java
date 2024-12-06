package com.csair.sc.common.core.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页参数
 *
 * @author LiPiao
 * @version 1.0
 * @since 2024-11-22 11:12:50
 */
@Data
@Schema(description = "分页参数")
public class PageParam implements Serializable {

    private static final Integer PAGE_NO = 1;
    private static final Integer PAGE_SIZE = 10;

    /**
     * 每页条数 - 不分页
     * <p>
     * 例如说，导出接口，可以设置 {@link #pageSize} 为 -1 不分页，查询所有数据。
     */
    public static final Integer PAGE_SIZE_NONE = -1;

    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为 1")
    @Schema(description = "页码，从 1 开始。查列表时忽略该参数。", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer pageNo = PAGE_NO;


    @NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "每页条数最小值为 1")
    @Max(value = 1000, message = "每页条数最大值为 1000")
    @Schema(description = "每页条数，最大值为 1000。查列表时忽略该参数，若列表总数据超过1000，请通过分页列表接口获取数据。", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer pageSize = PAGE_SIZE;

}
