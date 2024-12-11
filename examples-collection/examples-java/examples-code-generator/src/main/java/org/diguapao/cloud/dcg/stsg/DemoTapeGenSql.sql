create table if not exists xxl_job_info
(
    id                        int auto_increment        primary key,
    job_group                 int                              not null comment '执行器主键ID',
    schedule_conf             varchar(128)                     not null comment '调度配置，值含义取决于调度类型',
    job_desc                  varchar(255)                     not null,
    add_time                  datetime                         null,
    update_time               datetime                         null,
    author                    varchar(64)                      null comment '作者',
    alarm_email               varchar(255)                     null comment '报警邮件',
    executor_route_strategy   varchar(50)                      null comment '执行器路由策略',
    executor_handler          varchar(255)                     null comment '执行器任务handler',
    executor_param            varchar(512)                     null comment '执行器任务参数',
    executor_block_strategy   varchar(50)                      null comment '阻塞处理策略',
    executor_timeout          int         default 0            not null comment '任务执行超时时间，单位秒',
    executor_fail_retry_count int         default 0            not null comment '失败重试次数',
    glue_type                 varchar(50)                      not null comment 'GLUE类型',
    glue_source               mediumtext                       null comment 'GLUE源代码',
    glue_remark               varchar(128)                     null comment 'GLUE备注',
    glue_updatetime           datetime                         null comment 'GLUE更新时间',
    child_jobid               varchar(255)                     null comment '子任务ID，多个逗号分隔',
    trigger_status            tinyint     default 0            not null comment '调度状态：0-停止，1-运行',
    trigger_last_time         bigint(13)  default 0            not null comment '上次调度时间',
    trigger_next_time         bigint(13)  default 0            not null comment '下次调度时间',
    schedule_type             varchar(50) default 'NONE'       not null comment '调度类型',
    misfire_strategy          varchar(50) default 'DO_NOTHING' not null comment '调度过期策略'
)
    row_format = DYNAMIC comment '调度任务信息';

