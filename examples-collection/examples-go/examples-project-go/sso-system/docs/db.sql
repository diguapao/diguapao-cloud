use sso;

CREATE SCHEMA IF NOT EXISTS sso COLLATE utf8mb4_general_ci;

# DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    username    varchar(50)  not null comment 'username',
    password    varchar(500) not null comment 'password',
    email       varchar(64)  not null comment 'email',
    enabled     tinyint(1)   not null comment 'enabled',
    create_by   VARCHAR(50) DEFAULT NULL COMMENT '创建人',
    update_by   VARCHAR(50) DEFAULT NULL COMMENT '更新人',
    create_time TIMESTAMP   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time TIMESTAMP   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE un_username (username),
    INDEX idx_update_time (update_time)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci
  row_format = DYNAMIC COMMENT ='用户';

