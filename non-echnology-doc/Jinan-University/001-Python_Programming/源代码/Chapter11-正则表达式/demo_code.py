print(""" 
    正则	含义
    \d	匹配任意一个数字字符（0-9）
    \D	匹配任意一个非数字字符
    \w	匹配字母、数字、下划线
    \W	匹配非字母、非数字、非下划线
    \s	匹配空白符（空格、换行、制表符等）
    \S	匹配非空白符
""")

print('# ===========   11.3	预编译   ===========')
import re

regex_obj = re.compile(r'\d')
words = 'Today is July 26, 2023.'
print("找出“Today is July 26, 2023.”中的全部数字：" + str(regex_obj.findall(words)))

import re

# 将正则表达式[a-z]+预编译为正则对象，同时指定匹配模式为re.I
regex_one = re.compile(r'[a-z]+', re.I)
words = 'Today is March 28, 2019.'
print("将正则表达式[a-z]+预编译为正则对象，同时指定匹配模式为re.I，然后找出“Today is March 28, 2019.”中全部字母：" + str(regex_one.findall(words)))

print('# ===========   11.4.1使用match()函数进行匹配   ===========')
import re

date_one = "Today is July 26, 2023."
date_two = "26 July 2023"
print("使用正则表达式，尝试从字符串“Today is July 26, 2023.”的开头匹配一个数字字符（0-9）：" + str(re.match(r"\d", date_one)))
print("使用正则表达式，尝试从字符串“26 July 2023”的开头匹配一个数字字符（0-9）：" + str(re.match(r"\d", date_two).group()))

print('# ===========   11.4.2使用search()函数进行匹配   ===========')
import re

info_one = "I was born in 2000."
info_two = "20000505"
print("匹配任意一个数字字符（即不是 0~9 的字符）：" + re.search(r"\d", info_one).group(0))
print("匹配任意一个数字字符（即不是 0~9 的字符）：" + re.search(r"\d", info_two).group(0))
print("匹配任意一个非数字字符（即不是 0~9 的字符）：" + str(re.search(r"\D", info_two)))

print('# ===========   11.5	匹配对象   ===========')
import re

word = 'hello itheima'
match_result = re.search(r'\whe\w', word)  # 寻找这样一个子串：它以任意单词字符开始，接着是 "he"，然后又是一个任意单词字符
print(match_result)  # 输出匹配对象
print(match_result.group())  # 获取匹配对象包含的字符串
print(match_result.start())  # 获取匹配对象的开始位置
print(match_result.end())  # 获取匹配对象的结束位置
print(match_result.span())  # 获取匹配对象的位置

import re

# “(h)(e)”是一个带有两个捕获组（Capturing Groups）的正则表达式。在字符串 'hello heooo' 中查找第一个出现的 'he'，并把 'h' 和 'e' 分别作为两个捕获组
words = re.search("(h)(e)", 'hello heooo')
print(words.group(1))  # 获取第1个子组的匹配结果

import re

words = re.search("(h)(e)", 'hello heooo')
print(words.groups())

print('# ===========   11.6.1findall()函数   ===========')
import re

string = "狗的英文：Dog，猫的英文：Cat。"
# 使用 re.compile() 函数创建一个正则表达式对象，匹配一个或多个连续的中文汉字，\u4e00-\u9fa5 是 Unicode 编码范围，涵盖了所有常用的中文汉字（大约2万多个）
reg_zhn = re.compile(r"[\u4e00-\u9fa5]+")
print(re.findall(reg_zhn, string))

print('# ===========   11.6.2finditer()函数   ===========')
import re

string = "狗的英文：Dog，猫的英文：Cat。"
reg_zhn = re.compile(r"[a-zA-Z]+")  # 匹配所有(一个或多个连续的)英文字符
# 使用 finditer() 查找所有匹配项，返回一个迭代器（iterator），其中每个元素是一个 re.Match 对象，每个 Match 对象包含了匹配的内容、位置等信息
# 与 re.findall() 不同的是，finditer() 更适合处理大数据，因为它不是一次性返回列表，而是按需生成
result_info = re.finditer(reg_zhn, string)
print(result_info)
print(type(result_info))

for i in result_info:
    print(i.group())  # 输出匹配到的具体英文单词

print('# ===========   11.7	检索替换   ===========')
import re

words = 'And slowly read,and dream of the soft look'
result_one = re.sub(r'\s', '-', words)  # 使用sub()函数替换目标字符串，将空白字符串（空格、换行、制表符等）替换为短杠(-)。
print(result_one)
# 使用subn()函数搜索与 pattern 正则表达式匹配的所有子串，并用 repl 替换它们，
# 然后返回一个元组 (new_string, number_of_subs_made)，其中 new_string 是替换后的字符串，number_of_subs_made 是实际发生的替换次数
result_two = re.subn(r'\s', '-', words)
print(result_two)

print('# ===========   11.9	文本分割   ===========')
import re

words = 'And slowly read,and dream of the soft look'
result = re.split(r'\s', words)  # 以\s匹配的结果分割字符串words
print(result)

print('# ===========   11.10	贪婪匹配   ===========')
import re

words = 'And slowly read,and dream of the soft look'
# 在字符串中查找第一个出现的 'and'，后面紧跟着一个空白字符，然后是任意多个字符（直到行尾）。
# \s	匹配一个空白字符（如空格、换行、制表符等）
# .*	匹配任意字符（除换行外），尽可能多地匹配（贪婪模式）
result = re.search(r'and\s.*', words)
print(result.group())

import re

words = 'And slowly read,and dream of the soft look'
# .*?	非贪婪模式，匹配任意字符（除换行外），但尽可能少地匹配
result = re.search(r'and\s.*?', words)
print(result.group())
# \s：匹配一个空白字符（如空格、换行、制表符等）。
# .：匹配除了换行符以外的任意单个字符。
# +：表示前面的元素（在这里是.，即任何字符）可以出现一次或多次。
# ?：将 + 变为非贪婪模式，这意味着它会尽可能少地匹配字符，直到找到下一个符合条件的部分。
print(re.search(r'and\s.+', words).group())  # 贪婪匹配
print(re.search(r'and\s.+?', words).group())  # 非贪婪匹配
