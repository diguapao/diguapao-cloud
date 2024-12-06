package com.demo.xxljob.xxlJobInfo.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 调度任务信息 传输对象
 *
 * @author DiGuaPao
 * @version 1.0
 * @since 2024-11-06 18:55:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
// @EqualsAndHashCode(callSuper = true)
@Schema(description = "调度任务信息 传输对象")
public class XxlJobInfoDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @Schema(description = "")
    private Integer id;
    /**
     * 执行器主键ID
     */
    @Schema(description = "执行器主键ID")
    private Integer jobGroup;
    /**
     * 调度配置，值含义取决于调度类型
     */
    @Schema(description = "调度配置，值含义取决于调度类型")
    private String scheduleConf;
    /**
     * 
     */
    @Schema(description = "")
    private String jobDesc;
    /**
     * 
     */
    @Schema(description = "")
    private LocalDateTime addTime;
    /**
     * 作者
     */
    @Schema(description = "作者")
    private String author;
    /**
     * 报警邮件
     */
    @Schema(description = "报警邮件")
    private String alarmEmail;
    /**
     * 执行器路由策略
     */
    @Schema(description = "执行器路由策略")
    private String executorRouteStrategy;
    /**
     * 执行器任务handler
     */
    @Schema(description = "执行器任务handler")
    private String executorHandler;
    /**
     * 执行器任务参数
     */
    @Schema(description = "执行器任务参数")
    private String executorParam;
    /**
     * 阻塞处理策略
     */
    @Schema(description = "阻塞处理策略")
    private String executorBlockStrategy;
    /**
     * 任务执行超时时间，单位秒
     */
    @Schema(description = "任务执行超时时间，单位秒")
    private Integer executorTimeout;
    /**
     * 失败重试次数
     */
    @Schema(description = "失败重试次数")
    private Integer executorFailRetryCount;
    /**
     * GLUE类型
     */
    @Schema(description = "GLUE类型")
    private String glueType;
    /**
     * GLUE源代码
     */
    @Schema(description = "GLUE源代码")
    private String glueSource;
    /**
     * GLUE备注
     */
    @Schema(description = "GLUE备注")
    private String glueRemark;
    /**
     * GLUE更新时间
     */
    @Schema(description = "GLUE更新时间")
    private LocalDateTime glueUpdatetime;
    /**
     * 子任务ID，多个逗号分隔
     */
    @Schema(description = "子任务ID，多个逗号分隔")
    private String childJobid;
    /**
     * 调度状态：0-停止，1-运行
     */
    @Schema(description = "调度状态：0-停止，1-运行")
    private Byte triggerStatus;
    /**
     * 上次调度时间
     */
    @Schema(description = "上次调度时间")
    private Long triggerLastTime;
    /**
     * 下次调度时间
     */
    @Schema(description = "下次调度时间")
    private Long triggerNextTime;
    /**
     * 调度类型
     */
    @Schema(description = "调度类型")
    private String scheduleType;
    /**
     * 调度过期策略
     */
    @Schema(description = "调度过期策略")
    private String misfireStrategy;
    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createBy;
    /**
     * 修改人
     */
    @Schema(description = "修改人")
    private String updateBy;
    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private LocalDateTime updateTime;
}