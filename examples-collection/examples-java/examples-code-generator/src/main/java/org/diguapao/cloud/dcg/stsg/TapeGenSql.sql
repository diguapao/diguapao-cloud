CREATE TABLE IF NOT EXISTS car_vehicle_blacklist
(
    `id`                  BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    `garden_id`           BIGINT      NOT NULL COMMENT '园区ID',
    `garden_area_id`      BIGINT      NOT NULL COMMENT '停车场所在园区区域ID',
    `location_id`         BIGINT      NOT NULL COMMENT '停车场所在园区区域具体的位置ID',
    `device_id`           VARCHAR(64) NOT NULL COMMENT '物联网平台-设备唯一标识',
    `garden_name`         varchar(64)  DEFAULT NULL COMMENT '园区名称',
    `garden_area_name`    varchar(64)  DEFAULT NULL COMMENT '停车场名称(与位置所在园区区域名称等价)',
    `location_name`       varchar(64)  DEFAULT NULL COMMENT '车场出入口名称(与停车场所在园区区域具体的位置名称等价)',
    `vehicle_plate_no`    VARCHAR(16) NOT NULL COMMENT '车牌号码（需先要存在于表 car_vehicle）',
    `vehicle_plate_color` TINYINT      DEFAULT NULL COMMENT '车牌颜色，参见枚举：com.csair.sc.common.core.enums.VehiclePlateColorEnum',
    `vehicle_plate_type`  TINYINT      DEFAULT NULL COMMENT '车牌类型，参见枚举：com.csair.sc.common.core.enums.VehiclePlateTypeEnum',
    `vehicle_color` 	  varchar(20)  DEFAULT null comment '车辆颜色',
    `period_validity_sta` TIMESTAMP    DEFAULT NULL COMMENT '有效期-开始日期',
    `period_validity_end` TIMESTAMP    DEFAULT NULL COMMENT '有效期-截止日期',
    `remark`              VARCHAR(256) DEFAULT NULL COMMENT '区域说明',
    `create_by`           VARCHAR(50)  DEFAULT NULL COMMENT '创建人',
    `update_by`           VARCHAR(50)  DEFAULT NULL COMMENT '更新人',
    `create_time`         TIMESTAMP    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`         TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_garden_id (garden_id),
    INDEX idx_garden_area_id (garden_area_id),
    INDEX idx_vehicle_plate_no (vehicle_plate_no),
    INDEX idx_period_validity_sta (period_validity_sta),
    INDEX idx_period_validity_end (period_validity_end),
    INDEX idx_update_time (update_time)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  row_format = DYNAMIC COMMENT ='车辆黑名单信息表';