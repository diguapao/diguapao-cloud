import time

exam_time = input("请输入高考时间（格式如2022-06-07）：")
# 将时间字符串转换成时间元组
future_time = time.strptime(exam_time, '%Y-%m-%d')
# 获取当天日期
current_time = time.localtime(time.time())
# 根据时间戳求时间差
future = time.mktime(future_time)  # 获取高考时间的时间戳
current = time.mktime(current_time)  # 获取当前时间的时间戳
difference = future - current
# 将秒数换算成天数
days = difference / 60 / 60 / 24
print("=" * 30)
print(f"{future_time.tm_year} 年高考时间是", time.strftime("%Y年%m月%d日", future_time))
print(f"今天是 {time.strftime('%Y年%m月%d日', current_time)}")
print(f"距离 {future_time.tm_year} 年高考还有 {int(days)} 天")
print("=" * 30)
