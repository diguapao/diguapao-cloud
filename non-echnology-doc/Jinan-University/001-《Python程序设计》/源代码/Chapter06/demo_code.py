# ===========   6.1.1函数的定义   ===========
def show_weather():
    print("*" * 13)
    print("日期：4月8日")
    print("温度：14~28℃")
    print("空气状况：良")
    print("*" * 13)

def modify_weather(today, temp, air_quality):
    print("*"*13)
    print(f"日期：{today}")
    print(f"温度：{temp}")
    print(f"空气状况：{air_quality}")
    print("*" * 13)


# ===========   6.1.2函数的调用   ===========
# show_weather()
# modify_weather('7月5日', '23~40℃', '优')


# ===========   6.2.1位置参数传递   ===========
# def division(num_one, num_two):
#     print(num_one)
#     print(num_two)
#     print(num_one / num_two)
# division(6, 2)


# ===========   6.2.2关键字参数传递   ===========
# def info(name, age, address):
#     print(f'姓名:{name}')
#     print(f'年龄:{age}')
#     print(f'地址:{address}')
# info(name="小婷", age=23, address="山东")


# ===========   6.2.3默认参数传递   ===========
# def connect(ip, port=3306):
#     print(f"连接地址为：{ip}")
#     print(f"连接端口号为：{port}")
#     print("连接成功")
# connect('127.0.0.1')                    	# 第一种，形参port使用默认值3306
# connect(ip='127.0.0.1', port=8080)  	# 第二种，形参使用传入值8080


# ===========   6.2.4参数打包传递   ===========
# def test(*args):
#     print(args)
# test(11, 22, 33, 44, 55)

# def test(**kwargs):
#     print(kwargs)
# test(a=11, b=22, c=33, d=44, e=55)


# ===========   6.2.5参数解包传递   ===========
def test(a, b, c, d, e):
    print(a, b, c, d, e)
# nums = (11, 22, 33, 44, 55)
# test(*nums)

# nums = {"a":11, "b":22, "c":33, "d":44, "e":55}
# test(**nums)


# ===========   6.3.1局部变量   ===========
# def use_var():
#     name = '欲穷千里目，更上一层楼' # 局部变量
#     print(name)      # 在函数内部访问局部变量
# use_var()
# print(name)          # 在函数外部访问局部变量


# ===========   6.3.2全局变量   ===========
# count =10             # 全局变量
# def use_var():
#     print(count)     # 在函数内部访问全局变量
# use_var()
# print(count)         # 在函数外部访问局部变量

# count = 10
# def use_var():
#     global count         # 声明全局变量
#     count += 10          # 修改全局变量
#     print(count)
# use_var()


# ===========   6.4.1匿名函数   ===========
# area = lambda a, h: (a * h) * 0.5
# print(area(3, 4))


# ===========   6.4.3递归函数   ===========
# def factorial(num):
#     if num == 1:
#         return 1                              # 边界条件
#     else:
#         return num * factorial(num - 1)       # 递归公式
# result = factorial(5)
# print(f"5的阶乘为{result}")


# ===========   6.5	常用的内置函数   ===========
# print(abs(-5))
# print(abs(3.14))
# print(abs(8 + 3j))

# print(ord('a'))        # 返回字符'a'对应的码值
# print(ord('A'))        # 返回字符'A'对应的码值

print(chr(97))        # 返回97对应的Unicode字符
print(chr(65))        # 返回65对应的Unicode字符
