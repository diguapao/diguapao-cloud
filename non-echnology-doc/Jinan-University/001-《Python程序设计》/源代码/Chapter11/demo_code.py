# ===========   11.3	预编译   ===========
# import re
# regex_obj = re.compile(r'\d')
# words = 'Today is July 26, 2023.'
# print(regex_obj.findall(words))

# import re
# # 将正则表达式[a-z]+预编译为正则对象，同时指定匹配模式为re.I
# regex_one = re.compile(r'[a-z]+', re.I)
# words = 'Today is March 28, 2019.'
# print(regex_one.findall(words))


# ===========   11.4.1使用match()函数进行匹配   ===========
# import re
# date_one = "Today is July 26, 2023."
# date_two = "26 July 2023"
# print(re.match(r"\d", date_one))
# print(re.match(r"\d", date_two))


# ===========   11.4.2使用search()函数进行匹配   ===========
# import re
# info_one = "I was born in 2000."
# info_two = "20000505"
# print(re.search(r"\d", info_one))
# print(re.search(r"\D", info_two))


# ===========   11.5	匹配对象   ===========
# import re
# word = 'hello itheima'
# match_result = re.search(r'\whe\w', word)
# print(match_result)                 # 输出匹配对象
# print(match_result.group())       # 获取匹配对象包含的字符串
# print(match_result.start())       # 获取匹配对象的开始位置
# print(match_result.end())          # 获取匹配对象的结束位置
# print(match_result.span())         # 获取匹配对象的位置

# import re
# words = re.search("(h)(e)", 'hello heooo')
# print(words.group(1))              # 获取第1个子组的匹配结果

# import re
# words = re.search("(h)(e)", 'hello heooo')
# print(words.groups())


# ===========   11.6.1findall()函数   ===========
# import re
# string = "狗的英文：Dog，猫的英文：Cat。"
# reg_zhn = re.compile(r"[\u4e00-\u9fa5]+")
# print(re.findall(reg_zhn, string))


# ===========   11.6.2finditer()函数   ===========
# import re
# string = "狗的英文：Dog，猫的英文：Cat。"
# reg_zhn = re.compile(r"[a-zA-Z]+")  # 匹配所有英文字符
# result_info = re.finditer(reg_zhn, string)
# # print(result_info)
# # print(type(result_info))
#
# for i in result_info:
#     print(i)


# ===========   11.7	检索替换   ===========
# import re
# words = 'And slowly read,and dream of the soft look'
# result_one = re.sub(r'\s', '-', words)   # 使用sub()函数替换目标字符串
# print(result_one)
# result_two = re.subn(r'\s', '-', words)  # 使用subn()函数替换目标字符串
# print(result_two)


# ===========   11.9	文本分割   ===========
# import re
# words = 'And slowly read,and dream of the soft look'
# result = re.split(r'\s', words)	 # 以\s匹配的结果分割字符串words
# print(result)


# ===========   11.10	贪婪匹配   ===========
# import re
# words = 'And slowly read,and dream of the soft look'
# result = re.search(r'and\s.*', words)
# print(result.group())

# import re
# words = 'And slowly read,and dream of the soft look'
# result = re.search(r'and\s.*?', words)
# print(result.group())
#
# print(re.search(r'and\s.+', words).group())	  # 贪婪匹配
# print(re.search(r'and\s.+?', words).group())     # 非贪婪匹配

