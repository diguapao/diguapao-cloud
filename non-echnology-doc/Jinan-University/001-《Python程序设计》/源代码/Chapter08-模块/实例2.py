import time  # 导入time模块
incomplete_sign = 50  # 下载总量
print('=' * 23 + '开始下载' + '=' * 25)
for i in range(incomplete_sign + 1):
    completed = "*" * i  # 已完成下载量
    incomplete = "." * (incomplete_sign - i)  # 未完成下载量
    percentage = (i / incomplete_sign) * 100  # 百分比数值
    print("\r{:.0f}%[{}{}]".format(percentage, completed,
                                         incomplete), end="")
    time.sleep(0.5)
print("\n" + '=' * 23 + '下载完成' + '=' * 25)
