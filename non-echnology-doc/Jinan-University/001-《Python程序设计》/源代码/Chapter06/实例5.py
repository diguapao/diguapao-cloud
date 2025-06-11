# 计算x的y次方
def power(x, y):
    if y: # y大于0
        return x * power(x, y - 1)
    else: # y等于0
        return 1
# 计算1.0和1.1的100次方的差
print(power(1.1, 100) - power(1.0, 100))
