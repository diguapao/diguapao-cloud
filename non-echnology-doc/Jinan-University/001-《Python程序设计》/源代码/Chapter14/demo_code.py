# ===========   14.2.3 pymysql的常用对象   ===========
# conn = pymysql.connect(
#         host='localhost',            # IP地址
#         user='root',                  # 用户名
#         password  ='123456',         # 用户密码
#         database  ='dbtest',         # 数据库名称
#         charset='utf8')               # 编码方式


# ===========   14.2.4 pymysql的基本使用   ===========
# import pymysql
# # 创建连接
# conn = pymysql.connect(
#     host='localhost',    # IP地址
#     user='root',          # 用户名
#     password='123456',   # 用户密码
#     charset='utf8'        # 通信采用的编码方式
# )
#
# # 获取游标
# cursor = conn.cursor()
# # 创建数据库
# sql_create = 'create database if not exists dbtest'
# cursor.execute(sql_create)
# # 创建数据表
# sql_use = 'use dbtest'
# cursor.execute(sql_use)
# sql_table = 'create table if not exists employees(emID int primary ' \
#     'key, emName varchar(20), emLevel varchar(20), emDepID varchar(20))'
# cursor.execute(sql_table)
#
# # 插入数据
# sql = "insert into employees (emID, emName, emLevel, emDepID) " \
#       "values (%d, '%s', %d, %d)"
# data = (15, '小明', 3, 3)
# cursor.execute(sql % data)
# conn.commit()
#
# # 修改数据
# sql = "update employees set emName = '%s' where emID = %d"
# data = ('小红', 15)
# cursor.execute(sql % data)
# conn.commit()
#
# # 查询数据
# sql = 'select emID, emName from employees where emDepID = 3'
# cursor.execute(sql)
# for row in cursor.fetchall():
#     print("员工ID：%d 姓名：'%s'" % row)
# print('财务部一共有%d个员工' % cursor.rowcount)
#
# # 删除数据
# sql = 'delete from employees where emID = %d limit %d'
# data = (15, 1)
# cursor.execute(sql % data)
# conn.commit()
# print('共删除%d条数据' % cursor.rowcount)
#
# cursor.close()                # 关闭游标
# conn.close()                  # 关闭连接


# ===========   14.3.3pymongo常用对象   ===========
# client = MongoClient()
# client = MongoClient('localhost', 27017)
# client = MongoClient('mongodb://localhost:27017')
#
# data_base = client.db_name
# data_base = client['db_name']
#
# collection = db.test_collection
# collection = db['test-collection']

# # 向集合中插入多条文档
# collection.insert_many([{'x': i} for i in range(2)])
# # 查询集合中的多条文档
# cursor_obj = collection.find({'x': 1})
# # 遍历取出每条文档
# for document in cursor_obj:
#     print(document)


# ===========   14.3.4 pymongo的基本使用   ===========
# import pymongo
# # 创建连接
# client = pymongo.MongoClient(host='localhost', port=27017)
#
# # 创建数据库school
# db_obj = client.school
# # 创建集合student
# coll_obj = db_obj.student
#
# # 向集合student中插入文档
# coll_obj.insert_one({'学号':1, '姓名':'小明', '性别':'男'})
# coll_obj.insert_many([{'学号': 2, '姓名': '小兰', '性别': '女'},
#                           {'学号': 3, '姓名': '小花', '性别': '女'},
#                           {'学号': 4, '姓名': '小刚', '性别': '男'},
#                           {'学号': 5, '姓名': '小志', '性别': '男'},
#                           {'学号': 6, '姓名': '小白', '性别': '男'}])
# print('集合中共有%d个文档' % coll_obj.count_documents({}))
#
# # 更新集合student中的一条文档
# coll_obj.update_one({'学号': 6},{'$set':{'性别': '女'}})
#
# # 删除集合student中的一条文档
# coll_obj.delete_one({'性别':'女'})
# print('集合中共有%d个文档' % coll_obj.count_documents({}))
#
# # 查询集合student中的性别为女的文档
# result = coll_obj.find({'性别':'女'})
# for doc in result:
#     print(doc)


# ===========   14.4.4 redis的基本使用   ===========
# import redis
# # 创建StrictRedis对象，与Redis服务器建立连接
# sr = redis.StrictRedis()
#
# # 添加键值对
# result = sr.set('py1', 'Tom')
# # 输出响应结果，若添加成功则返回True，否则返回False
# print(result)
#
# # 设置键py1的值，若键已经存在修改值，若键不存在增加键值对对
# result = sr.set('py1', 'Jerry')
# # 输出响应结果，若修改成功则返回True，否则返回False
# print(result)
#
# # 获取键py1的值
# result = sr.get('py1')
# # 若键存在则输出键对应的值，否则输出None
# print(result)
#
# # 删除键py1的值
# result = sr.delete('py1')
# # 输出响应结果，若删除成功则返回受影响的键数，否则返回0
# print(result)
