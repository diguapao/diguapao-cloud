# 手机充值


def phone_recharge():
    phone_num = input("请输入需要充值的手机号码：")
    recharge_amount = input("请输入需要充值的金额：")
    print("手机号码", phone_num, "成功充值", recharge_amount, "元")


# 打印购物小票
def print_shopping_receipts():
    dh = "DH202503230001"
    print("··················································")
    print("单号：", dh)
    print("时间：", "2025-03-23 22:23:89", end="\n\n")

    print("··················································")
    print("名称", "\t\t数量", "单价", "金额")
    print("金士顿U盘8G\t", 1, 40.00, 40.00)
    print("胜创造16GTF卡\t", 1, 50.00, 50.00)
    print("读卡器\t\t", 1, 5.00, 8.00)
    print("网线两米\t\t", 1, 5.00, 5.00, end="\n\n")

    print("··················································")
    print("总数：", 4, "\t总额", 103.00)
    print("折后总额：", 103.00)
    print("实收：", 103.00, "\t找零", 0.00)
    print("收银：", "管理员")

    print("\n··················································")


# 逻辑运算符
def demo01():
    result_one = 0 or 3 + 5  # 左操作数布尔值为 False
    print(result_one)
    result_two = 3 or 0  # 左操作数布尔值为 True
    print(result_two)

    result_three = 3 - 3 and 5  # 左操作数布尔值为 False
    print(result_three)
    result_four = 3 - 4 and 5  # 左操作数布尔值为 True
    print(result_four)

    result_five = not 3 - 5  # 操作数布尔值为 True
    print(result_five)
    result_six = not False  # 操作数布尔值为 False
    print(result_six)


# 成员运算符
def demo2():
    words = "abcdefghijklmnopqrstuvwxyz"  # 保存字符串类型数据的变量
    print("f" in words)  # 检测字符串中是否有 f
    print("R" not in words)  # 检测字符串中是否没有 R


# 位运算
def demo3():
    num_one = 10
    num_two = 11
    print("左移两位：", num_one << 2)
    print("右移两位：", num_one >> 2)
    print("按位与2：", num_one & 2)
    print("按位与2：", num_one | 2)
    print("按位异或：", num_one ^ num_two)
    print("按位取反：", ~num_one)

    # 二进制转十进制
    print("1010 转为十进制：", int("1010", 2))


# 回文数检测
def demo4():
    # 接收4位数
    num = int(input("请输入一个四位数:"))
    # 获取千位、百位、十位、个位上的数字
    thousands_place = num // 1000  # 千位上的数字
    hundreds_place = num // 100 % 10  # 百位上的数字
    tens_place = num // 10 % 10  # 十位上的数字
    ones_place = num % 10  # 个位上的数字

    # 组合新4位数
    reverse_order = ones_place * 1000 + tens_place * 100 + \
                    hundreds_place * 10 + thousands_place
    # 判断原4位数与新4位数
    if num == reverse_order:
        print(num, "是回文数")
    else:
        print(num, "不是回文数")


if __name__ == '__main__':
    demo4()
