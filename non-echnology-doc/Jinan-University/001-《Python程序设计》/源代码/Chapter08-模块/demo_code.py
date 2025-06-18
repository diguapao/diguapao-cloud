print('===========   8.1.2模块的导入方式   ===========')
import time  # 导入一个模块

time.sleep(3)

from time import sleep as sl

sl(1)  # sl为sleep()函数的别名

print('===========   8.1.3常见的内置模块   ===========')
print('===========   1．	random模块   ===========')
import random

print(random.random())  # 生成一个0.0到1.0之间的随机浮点数

import random

print(random.randint(1, 8))  # 生成一个1到8之间的随机整数

import random

name_li = ["小坤", "小刚", "小明", "小晴"]
print(random.choice(name_li))  # 从name_li中获取一个随机元素

print('===========   2．	time模块   ===========')
import time

before = time.time()
result = pow(1000, 10000)  # 计算1000的10000次方
after = time.time()
interval = after - before
print(f"计算1000的10000次方，运行时长为{interval}秒")

import random, time

name_li1 = ["小飞", "小羽", "小韦", "小明", "小超"]
name_li2 = []
for i in range(len(name_li1)):
    people = random.choice(name_li1)  # 随机从列表name_li1中选择一个元素
    name_li1.remove(people)  # 移除已选择元素，避免出现重复元素，
    name_li2.append(people)  # 将选择出来的元素添加到列表name_li2中
    time.sleep(2)  # 让程序暂停执行两秒钟
    print(f"此时的成员有{name_li2}")

import time

print("返回以时间元组表示的当地时间，时间值是默认的：" + str(time.localtime()))  # 返回以时间元组表示的当地时间，时间值是默认当前时间
print("返回以时间元组表示的当地时间，时间值是指定的：" + str(time.localtime(34.54)))  # 返回以时间元组表示的当地时间，时间值是指定的

import time

print("返回格式化后的时间字符串1：" + time.strftime('%Y-%b-%d %H:%M:%S'))  # 返回格式化后的时间字符串
from datetime import datetime

print("返回格式化后的时间字符串2：" + time.strftime('%Y-%m-%d %H:%M:%S'))  # 获取当前时间并格式化为 yyyy-MM-dd HH:mm:ss
print("返回格式化后的时间字符串3：" + datetime.now().strftime('%Y-%m-%d %H:%M:%S.%f')[:-3])  # 获取当前时间并格式化为 yyyy-MM-dd HH:mm:ss.SSS

import time

print("返回格式化后的时间字符串4：" + time.asctime())  # 返回格式化后的时间字符串

import time

str_dt = "2023-02-25 17:43:54"
# 将时间字符串转换成时间元组
time_struct = time.strptime(str_dt, "%Y-%m-%d %H:%M:%S")
print("将时间字符串转换成时间元组：" + str(time_struct))
# 将时间元组转换成时间戳
timestamp = time.mktime(time_struct)
print("将时间元组转换成时间戳：" + str(timestamp))

print('===========   8.2	自定义模块   ===========')
import module_demo

module_demo.introduce()
print(module_demo.age)

from module_demo import introduce

introduce()

import sys

print("系统路径1：" + str(sys.path))

sys.path.append("E:\PythonProject\test")
print("系统路径2：" + str(sys.path))

import module_demo

module_demo.introduce()

print('===========   8.4.2包的导入   ===========')
import package_demo.module_demo

package_demo.module_demo.add(2, 3)

from package_demo import module_demo

module_demo.add(4, 5)
