# 接收三位数
num3 = int(input("请输入一个三位数："))
# 获取百位、十位、个位上的数字
hundreds_place = int(num3 // 100 % 10)   # 百位上的数字
ten_place = int(num3 / 10 % 10)           # 十位上的数字
one_place = int(num3 % 10)                 # 个位上的数字
# 组合新数字
new_num = hundreds_place ** 3 + ten_place ** 3 + one_place ** 3
# 比较原数字与新数字
result = new_num == num3
print(result)
