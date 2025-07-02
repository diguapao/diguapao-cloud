# 接收三角形的三条边长
one_len = float(input('输入三角形第一边长: '))
two_len = float(input('输入三角形第二边长: '))
three_len = float(input('输入三角形第三边长: '))
# 根据公式计算三角形的半周长
perimeter = (one_len + two_len + three_len) / 2
# 根据公式计算三角形的面积
area = (perimeter * (perimeter - one_len) *
        (perimeter - two_len) * (perimeter - three_len)) ** 0.5
print('三角形面积为', area)
