package com.demo.xxljob.xxlJobInfo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 调度任务信息 实体类
 *
 * @author DiGuaPao
 * @version 1.0
 * @since 2024-11-06 18:55:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("xxl_job_info")
@Schema(description = "xxl_job_info 表")
// @EqualsAndHashCode(callSuper = true)
public class XxlJobInfo extends Model<XxlJobInfo> {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "")
    private Integer id;
    /**
     * 执行器主键ID
     */
    @TableField("job_group")
    @Schema(description = "执行器主键ID")
    private Integer jobGroup;
    /**
     * 调度配置，值含义取决于调度类型
     */
    @TableField("schedule_conf")
    @Schema(description = "调度配置，值含义取决于调度类型")
    private String scheduleConf;
    /**
     * 
     */
    @TableField("job_desc")
    @Schema(description = "")
    private String jobDesc;
    /**
     * 
     */
    @TableField("add_time")
    @Schema(description = "")
    private LocalDateTime addTime;
    /**
     * 作者
     */
    @TableField("author")
    @Schema(description = "作者")
    private String author;
    /**
     * 报警邮件
     */
    @TableField("alarm_email")
    @Schema(description = "报警邮件")
    private String alarmEmail;
    /**
     * 执行器路由策略
     */
    @TableField("executor_route_strategy")
    @Schema(description = "执行器路由策略")
    private String executorRouteStrategy;
    /**
     * 执行器任务handler
     */
    @TableField("executor_handler")
    @Schema(description = "执行器任务handler")
    private String executorHandler;
    /**
     * 执行器任务参数
     */
    @TableField("executor_param")
    @Schema(description = "执行器任务参数")
    private String executorParam;
    /**
     * 阻塞处理策略
     */
    @TableField("executor_block_strategy")
    @Schema(description = "阻塞处理策略")
    private String executorBlockStrategy;
    /**
     * 任务执行超时时间，单位秒
     */
    @TableField("executor_timeout")
    @Schema(description = "任务执行超时时间，单位秒")
    private Integer executorTimeout;
    /**
     * 失败重试次数
     */
    @TableField("executor_fail_retry_count")
    @Schema(description = "失败重试次数")
    private Integer executorFailRetryCount;
    /**
     * GLUE类型
     */
    @TableField("glue_type")
    @Schema(description = "GLUE类型")
    private String glueType;
    /**
     * GLUE源代码
     */
    @TableField("glue_source")
    @Schema(description = "GLUE源代码")
    private String glueSource;
    /**
     * GLUE备注
     */
    @TableField("glue_remark")
    @Schema(description = "GLUE备注")
    private String glueRemark;
    /**
     * GLUE更新时间
     */
    @TableField("glue_updatetime")
    @Schema(description = "GLUE更新时间")
    private LocalDateTime glueUpdatetime;
    /**
     * 子任务ID，多个逗号分隔
     */
    @TableField("child_jobid")
    @Schema(description = "子任务ID，多个逗号分隔")
    private String childJobid;
    /**
     * 调度状态：0-停止，1-运行
     */
    @TableField("trigger_status")
    @Schema(description = "调度状态：0-停止，1-运行")
    private Byte triggerStatus;
    /**
     * 上次调度时间
     */
    @TableField("trigger_last_time")
    @Schema(description = "上次调度时间")
    private Long triggerLastTime;
    /**
     * 下次调度时间
     */
    @TableField("trigger_next_time")
    @Schema(description = "下次调度时间")
    private Long triggerNextTime;
    /**
     * 调度类型
     */
    @TableField("schedule_type")
    @Schema(description = "调度类型")
    private String scheduleType;
    /**
     * 调度过期策略
     */
    @TableField("misfire_strategy")
    @Schema(description = "调度过期策略")
    private String misfireStrategy;
    /**
     * 创建人
     */
    @Schema(description = "创建人")
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    /**
     * 修改人
     */
    @Schema(description = "修改人")
    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;
    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;
}