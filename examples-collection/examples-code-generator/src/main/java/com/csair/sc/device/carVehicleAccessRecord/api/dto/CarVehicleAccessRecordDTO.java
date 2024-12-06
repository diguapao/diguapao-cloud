package com.csair.sc.device.carVehicleAccessRecord.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalDate;

/**
 * 车辆出入记录传输对象
 *
 * @author LiPiao
 * @version 1.0
 * @since 2024-11-22 11:12:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
// @EqualsAndHashCode(callSuper = true)
@Schema(description = "车辆出入记录传输对象")
public class CarVehicleAccessRecordDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @Schema(description = "主键ID")
    private Long id;
    /**
     * 园区ID
     */
    @Schema(description = "园区ID")
    private Long gardenId;
    /**
     * 停车场所在园区区域ID
     */
    @Schema(description = "停车场所在园区区域ID")
    private Long gardenAreaId;
    /**
     * 停车场出入口ID
     */
    @Schema(description = "停车场出入口ID")
    private Long vehicleParkAccessId;
    /**
     * 车牌号码
     */
    @Schema(description = "车牌号码")
    private String vehiclePlateNo;
    /**
     * 车辆类型：1=员工车，2=公务车，3=访客车
     */
    @Schema(description = "车辆类型：1=员工车，2=公务车，3=访客车")
    private Integer vehicleType;
    /**
     * 人员姓名
     */
    @Schema(description = "人员姓名")
    private String personnelName;
    /**
     * 人员类型：1=员工，2=访客
     */
    @Schema(description = "人员类型：1=员工，2=访客")
    private Integer personnelType;
    /**
     * 抓拍图片
     */
    @Schema(description = "抓拍图片")
    private String photoUrl;
    /**
     * 出入类型，1：入，2：出
     */
    @Schema(description = "出入类型，1：入，2：出")
    private Integer accessTye;
    /**
     * 通行时间
     */
    @Schema(description = "通行时间")
    private LocalDateTime accessTime;
    /**
     * 是否滞留
     */
    @Schema(description = "是否滞留")
    private Integer retention;
    /**
     * 滞留时长，单位：小时
     */
    @Schema(description = "滞留时长，单位：小时")
    private Short retentionDuration;
    /**
     * 滞留原因
     */
    @Schema(description = "滞留原因")
    private String retentionCause;
    /**
     * 是否强制出场
     */
    @Schema(description = "是否强制出场")
    private Integer forceLeave;
    /**
     * 处理人
     */
    @Schema(description = "处理人")
    private String processor;
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