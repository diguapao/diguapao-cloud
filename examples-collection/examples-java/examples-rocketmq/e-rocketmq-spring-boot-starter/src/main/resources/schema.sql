create table t_order
(
    order_id             INTEGER
        primary key autoincrement,
    order_no             VARCHAR(32)    not null
        unique,
    status               VARCHAR(20)    not null,
    user_id              BIGINT         not null,
    user_name            VARCHAR(100),
    user_phone           VARCHAR(11),
    merchant_id          BIGINT,
    merchant_name        VARCHAR(100),
    total_quantity       INTEGER        default 0,
    original_amount      DECIMAL(10, 2) default 0,
    discount_amount      DECIMAL(10, 2) default 0,
    shipping_fee         DECIMAL(10, 2) default 0,
    pay_amount           DECIMAL(10, 2) not null,
    pay_method           INTEGER,
    transaction_id       VARCHAR(100),
    pay_time             DATETIME,
    pay_expire_time      DATETIME,
    receiver_name        VARCHAR(100),
    receiver_phone       VARCHAR(11),
    receiver_address     VARCHAR(500),
    logistics_company    VARCHAR(100),
    tracking_no          VARCHAR(100),
    ship_time            DATETIME,
    actual_delivery_time DATETIME,
    create_time          DATETIME       default (datetime('now', 'localtime')),
    update_time          DATETIME       default (datetime('now', 'localtime')),
    complete_time        DATETIME,
    cancel_time          DATETIME,
    cancel_reason        VARCHAR(200),
    user_remark          VARCHAR(1000),
    ext_data             TEXT           default '{}',
    version              INTEGER        default 0,
    deleted              TINYINT(1)     default 0
);

create index idx_order_cancel_time
    on t_order (cancel_time);

create index idx_order_create_time
    on t_order (create_time);

create index idx_order_no
    on t_order (order_no);

create index idx_order_pay_time
    on t_order (pay_time);

create index idx_order_user_status
    on t_order (user_id, status);

create table t_order_item
(
    item_id         INTEGER
        primary key autoincrement,
    order_id        BIGINT       not null
        references t_order
            on delete cascade,
    order_no        VARCHAR(32)  not null
        unique,
    product_id      BIGINT       not null,
    sku_id          BIGINT,
    product_name    VARCHAR(200) not null,
    product_image   VARCHAR(500),
    specifications  VARCHAR(500),
    unit_price      DECIMAL(10, 2) default 0,
    quantity        INTEGER        default 1,
    total_price     DECIMAL(10, 2) default 0,
    discount_amount DECIMAL(10, 2) default 0,
    actual_amount   DECIMAL(10, 2) default 0,
    commented       BOOLEAN        default 0,
    deleted         INTEGER        default 0
);

create index idx_order_item_order_id
    on t_order_item (order_id);

create index idx_order_item_product_id
    on t_order_item (product_id);

create table t_order_log
(
    log_id            INTEGER
        primary key autoincrement,
    order_id          BIGINT      not null
        references t_order
            on delete cascade,
    order_no          VARCHAR(32) not null
        unique,
    operation_type    VARCHAR(50) not null,
    from_status       VARCHAR(20),
    to_status         VARCHAR(20),
    operation_content VARCHAR(1000),
    operator_type     VARCHAR(20) not null,
    operator_id       BIGINT,
    operator_name     VARCHAR(100),
    ip_address        VARCHAR(50),
    operation_time    DATETIME default (datetime('now', 'localtime')),
    operation_detail  TEXT     default '{}'
);

create index idx_order_log_operation_time
    on t_order_log (operation_time);

create index idx_order_log_order_id
    on t_order_log (order_id);

