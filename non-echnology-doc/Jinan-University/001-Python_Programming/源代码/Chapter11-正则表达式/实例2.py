import re
# 1.读取电影信息
file = open("电影.txt", 'r', encoding='utf-8')
data = file.read()
# 2.提取电影名称与评分信息
# 2.1 定义正则表达式，分别匹配电影名称、评分、排名
title = r'"title":"(.*?)"'
rating = r'"rating":\["(.*?)","\d+"\]'
rank = r'"rank":(\d+)'
# 2.2 预编译正则表达式
pattern_title = re.compile(title)
pattern_rating = re.compile(rating)
pattern_rank = re.compile(rank)
# 2.3 将正则表达式与电影信息进行匹配查找，匹配结果为列表类型
data_title = pattern_title.findall(data)
data_rating = pattern_rating.findall(data)
data_rank = pattern_rank.findall(data)
# 2.4 输出排在前20名电影的名称和评分
for i in range(20):
    print("排名：", data_rank[i] + "\t\t" + "电影名：" + data_title[i]
           + "\t\t" + "评分：" + data_rating[i])
file.close()
