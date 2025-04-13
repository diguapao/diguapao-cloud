# -*- coding: utf-8 -*-
"""
测试 Redis Cluster 的负载均衡

Windows 下 python3 安装依赖
py -m pip install redis
py -m pip install redis-py-cluster
"""

from rediscluster import RedisCluster
import random

# 定义 Redis 集群的启动节点
startup_nodes = [
    {"host": "192.168.11.66", "port": "6379"},
    {"host": "192.168.11.66", "port": "6380"},
    {"host": "192.168.11.66", "port": "6381"},
]

# 连接到 Redis 集群
client = RedisCluster(
    startup_nodes=startup_nodes,
    decode_responses=True,
    password="redis",  # 如果没有密码，请删除此参数
)

# 写入 10000 条数据
for i in range(1000000):
    key = f"key{i}"  # 键
    value = f"value{random.randint(1, 1000)}"  # 值
    client.set(key, value)  # 写入数据

print("数据写入完成！")
