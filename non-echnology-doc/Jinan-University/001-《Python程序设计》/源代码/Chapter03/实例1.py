# 接收4位数
num = int(input("请输入一个四位数："))
# 获取千位、百位、十位、个位上的数字
thousands_place = num // 1000          # 千位上的数字
hundreds_place = num // 100 % 10      # 百位上的数字
tens_place = num // 10 % 10            # 十位上的数字
ones_place = num % 10                   # 个位上的数字
# 组合新4位数
reverse_order = ones_place * 1000 + tens_place * 100 + \
                hundreds_place * 10 + thousands_place
# 判断原4位数与新4位数
if num == reverse_order:
    print(num, "是回文数")
else:
    print(num, "不是回文数")
