package com.csair.sc.device.carVehicleAccessRecord.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.time.LocalDateTime;
import java.time.LocalDate;

/**
 * 车辆出入记录实体类
 *
 * @author DiGuaPao
 * @version 1.0
 * @since 2024-11-22 11:12:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@TableName("car_vehicle_access_record")
@Schema(description = "车辆出入记录表")
// @EqualsAndHashCode(callSuper = true)
public class CarVehicleAccessRecord extends Model<CarVehicleAccessRecord> {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "主键ID")
    private Long id;
    /**
     * 园区ID
     */
    @TableField("garden_id")
    @Schema(description = "园区ID")
    private Long gardenId;
    /**
     * 停车场所在园区区域ID
     */
    @TableField("garden_area_id")
    @Schema(description = "停车场所在园区区域ID")
    private Long gardenAreaId;
    /**
     * 停车场出入口ID
     */
    @TableField("vehicle_park_access_id")
    @Schema(description = "停车场出入口ID")
    private Long vehicleParkAccessId;
    /**
     * 车牌号码
     */
    @TableField("vehicle_plate_no")
    @Schema(description = "车牌号码")
    private String vehiclePlateNo;
    /**
     * 车辆类型：1=员工车，2=公务车，3=访客车
     */
    @TableField("vehicle_type")
    @Schema(description = "车辆类型：1=员工车，2=公务车，3=访客车")
    private Integer vehicleType;
    /**
     * 人员姓名
     */
    @TableField("personnel_name")
    @Schema(description = "人员姓名")
    private String personnelName;
    /**
     * 人员类型：1=员工，2=访客
     */
    @TableField("personnel_type")
    @Schema(description = "人员类型：1=员工，2=访客")
    private Integer personnelType;
    /**
     * 抓拍图片
     */
    @TableField("photo_url")
    @Schema(description = "抓拍图片")
    private String photoUrl;
    /**
     * 出入类型，1：入，2：出
     */
    @TableField("access_tye")
    @Schema(description = "出入类型，1：入，2：出")
    private Integer accessTye;
    /**
     * 通行时间
     */
    @TableField("access_time")
    @Schema(description = "通行时间")
    private LocalDateTime accessTime;
    /**
     * 是否滞留
     */
    @TableField("retention")
    @Schema(description = "是否滞留")
    private Integer retention;
    /**
     * 滞留时长，单位：小时
     */
    @TableField("retention_duration")
    @Schema(description = "滞留时长，单位：小时")
    private Short retentionDuration;
    /**
     * 滞留原因
     */
    @TableField("retention_cause")
    @Schema(description = "滞留原因")
    private String retentionCause;
    /**
     * 是否强制出场
     */
    @TableField("force_leave")
    @Schema(description = "是否强制出场")
    private Integer forceLeave;
    /**
     * 处理人
     */
    @TableField("processor")
    @Schema(description = "处理人")
    private String processor;
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