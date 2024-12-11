CREATE TABLE IF NOT EXISTS car_vehicle_access_record
(
    id                     BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    garden_id              BIGINT      NOT NULL COMMENT '园区ID',
    garden_area_id         BIGINT      NOT NULL COMMENT '停车场所在园区区域ID',
    vehicle_park_access_id BIGINT      NOT NULL COMMENT '停车场出入口ID',
    vehicle_plate_no       VARCHAR(16) NOT NULL COMMENT '车牌号码',
    vehicle_type           TINYINT      DEFAULT NULL COMMENT '车辆类型：1=员工车，2=公务车，3=访客车',
    personnel_name         VARCHAR(20)  DEFAULT NULL COMMENT '人员姓名',
    personnel_type         TINYINT      DEFAULT NULL COMMENT '人员类型：1=员工，2=访客',
    photo_url              VARCHAR(512) DEFAULT NULL COMMENT '抓拍图片',
    access_tye             TINYINT     NOT NULL COMMENT '出入类型，1：入，2：出',
    access_time            TIMESTAMP   NOT NULL COMMENT '通行时间',
    retention              TINYINT      DEFAULT NULL COMMENT '是否滞留',
    retention_duration     SMALLINT     DEFAULT NULL COMMENT '滞留时长，单位：小时',
    retention_cause        VARCHAR(128) DEFAULT NULL COMMENT '滞留原因',
    force_leave            TINYINT      DEFAULT NULL COMMENT '是否强制出场',
    processor              VARCHAR(32)  DEFAULT NULL COMMENT '处理人',
    create_by              VARCHAR(50)  DEFAULT NULL COMMENT '创建人',
    update_by              VARCHAR(50)  DEFAULT NULL COMMENT '更新人',
    create_time            TIMESTAMP    DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time            TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_garden_id (garden_id),
    INDEX idx_garden_area_id (garden_area_id),
    INDEX idx_update_time (update_time)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  row_format = DYNAMIC COMMENT ='车辆出入记录';