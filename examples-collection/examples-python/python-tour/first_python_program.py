# 手机充值
import datetime


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


if __name__ == '__main__':
    print_shopping_receipts()
