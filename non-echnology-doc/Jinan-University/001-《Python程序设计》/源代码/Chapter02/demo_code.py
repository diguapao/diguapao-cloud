# ===========   2.1	数字类型   ===========
# com_one = 3 + 2j                 # 按照一般复数格式创建复数
# print(com_one)
# com_two = complex(3, 2)        # 使用内置函数complex()创建复数
# print(com_two)

# bol_one = bool(None)          # 检测None的布尔值
# print(bol_one)
# bol_two = bool(0)              # 检测整数0的布尔值
# print(bol_two)
# bol_thr = bool([])             # 检测空列表[]的布尔值
# print(bol_thr)
# bol_fou = bool(2)              # 检测整数2的布尔值
# print(bol_fou)


# ===========   2.2.1	算术运算符   ===========
# result_one = 3 + 3 + 2j         # 整型数据与复数类型数据相加
# print(result_one)
# result_two = 3 * 4.5             # 整型数据与浮点型数据相乘
# print(result_two)
# result_thr = 5.5 - 2 + 3j       # 浮点型数据与复数类型数据相减
# print(result_thr)
# result_fou = True + 1 + 2j      # 布尔类型的数据与复数类型的数据相加
# print(result_fou)


# ===========   2.2.3	赋值运算符   ===========
# a = 3
# b = 5
# a += b
# print(a)
# print(b)


# ===========   2.2.4	逻辑运算符   ===========
# result_one = 0 or 3 + 5          # 左操作数布尔值为False
# print(result_one)
# result_two = 3 or 0               # 左操作数布尔值为True
# print(result_two)

# result_one = 3 - 3 and 5               # 左操作数布尔值为False
# print(result_one)
# result_two = 3 - 4 and 5               # 左操作数布尔值为True
# print(result_two)

# result_one = not 3 - 5              # 操作数布尔值为True
# print(result_one)
# result_two = not False              # 操作数的值为False
# print(result_two)


# ===========   2.2.5	成员运算符   ===========
# words = 'abcdefg'            # 定义一个变量，保存字符串类型的数据
# print('f' in words)          # 检测'f'是否在字符串中
# print('c' not in words)     # 检测'c'是否在字符串中


# ===========   2.2.6	位运算符   ===========
# num_one = 10
# num_two = 11
# result_one = num_one << 2        # num_one的二进制数按位左移两位
# print(result_one)
# result_two = num_one >> 2        # num_one的二进制数按位右移两位
# print(result_two)
# result_thr = num_one & 2         # num_one与2的二进制数进行按位与运算
# print(result_thr)
# result_fou = num_one | 2         # num_one与2的二进制数进行按位或运算
# print(result_fou)
# result_fiv = num_one ^ num_two  # num_one与num_two的二进制数进行按位异或运算
# print(result_fiv)
# result_six = ~num_one             # num_one的二进制数进行按位取反运算
# print(result_six)


# ===========   2.4.1	字符串的创建   ===========
# single_symbol = '学海无涯'  # 使用单引号创建字符串
# double_symbol = "知识无底"  # 使用双引号创建字符串
# three_symbol = """学知不足，
#                   业精于勤"""  # 使用三引号创建字符串

# mixture_string = "Let's go"     # 单引号、双引号混合使用
# double_string = "Let"s go"      # 双引号嵌套双引号，程序运行出现错误信息
# single_string = 'Let's go'      # 单引号嵌套单引号，程序运行出现错误信息

# double_symbol = "学知不足，\n业精于勤"


# ===========   2.4.2	字符串的格式化   ===========
# name = "小明"
# result = "你好，我叫%s。" % name
# print(result)

# name = "小明"
# age = 12
# result = "你好，我叫%s，今年我%d岁了。" % (name, age)
# print(result)

# name = "李强"   # 变量name是字符串类型
# age = "12"      # 变量age是字符串类型
# result = "你好，我叫%s，今年我%d岁了。" % (name, age)
# print(result)

# name = "小明"
# result = "你好！我的名字是{}。".format(name)
# print(result)

# name = "小明"
# age = 21
# result_one = "你好！我的名字是{}，今年我{}岁了。".format(name, age)
# print(result_one)
# result_two = "你好！我的名字是{1}，今年我{0}岁了。".format(age, name)
# print(result_two)

# pi = 3.1415
# result = "{:.2f}".format(pi)
# print(result)

# num = 1
# result = "{:0>3d}".format(num)
# print(result)

# num = 0.1
# result = "{:.0%}".format(num)
# print(result)

# address = '北京'
# result = f'欢迎来到{address}。'
# print(result)

# name = '小天'
# age = 20
# gender = '男'
# result = f'我的名字是{name},今年{age}岁了,我的性别是{gender}。'
# print(result)


# ===========   2.4.3	字符串的常见操作   ===========
# str_one = "乘风破浪会有时,"
# str_two = "直挂云帆济沧海。"
# result = str_one + str_two
# print(result)

# word = "日月之行，若出其中；星汉灿烂，若出其里；"
# result_one = word.replace("；", "。")     # 使用"。"替换"；"，不指定替换次数
# print(result_one)
# result_two = word.replace("；", "。", 1)  # 使用"。"替换"；"，指定替换次数为1
# print(result_two)

# word = "1 2 3 4 5"
# result_one = word.split()            # 使用空字符分割字符串，不限制分割次数
# print(result_one)
# word = "a,b,c,d,e"
# result_two = word.split(",")         # 使用逗号分割字符串，不限制分割次数
# print(result_two)
# result_thr = word.split(",", 3)      # 使用空字符分割字符串，最多分割3次
# print(result_thr)

# word = "  勤而行之  "
# result = word.strip()                    # 去除字符串两侧的空格
# print(result)

# word = "**勤而行之**"
# result = word.strip("*")                 # 去除字符串两侧的星号
# print(result)


# ===========   2.4.4	字符串的索引与切片   ===========
# str_python = "python"
# str_python[0]					# 使用正向索引获取字符p
# str_python[-6]					# 使用反向索引获取字符p

str_python = "python"
# result_one = str_python[0:4]     # 从索引为0处开始，到索引为4处结束，步长为1
# print(result_one)
# result_two = str_python[0:4:2]   # 从索引为0处开始，到索引为4处结束，步长为2
# print(result_two)

# # 从索引为3处开始，到索引为0处结束，步长为-1
# result_thr = str_python[3:0:-1]
# print(result_thr)
# # 从索引为-1处开始，到索引为-6处结束，步长为-2
# result_fou = str_python[-1::-2]
# print(result_fou)


# ===========   2.5.1	类型转换函数   ===========
# result_one = int(3.6)		        # 将浮点型数据转换为整型
# print(result_one)
# result_two = int(False)			    # 将布尔类型数据转换为整型
# print(result_two)
# result_thr = float(3)			    # 将整型数据转换为转浮点型
# print(result_thr)
# result_fou = float("3.1415926")     # 将字符串转换为转浮点型
# print(result_fou)

str_01 = "2"
str_02 = "5"
sum = int(str_01)+ int(str_02)
# print(sum)

# result_one = type(str_01)
# print(result_one)
# result_two = type(str_02)
# print(result_two)
# result_thr = type(sum)
# print(result_thr)


