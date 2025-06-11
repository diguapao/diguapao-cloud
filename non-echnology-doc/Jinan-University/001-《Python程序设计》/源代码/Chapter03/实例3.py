# 接收身高和体重
height = float(input('请输入您的身高(m)：'))
weight = float(input('请输入您的体重(kg)：'))
# 根据公式计算BMI指数
bmi = weight / height / height
print('您的BMI指数的值为%.2f' % bmi)
# 判断BMI指数所属的分类
if bmi < 18.5:
    print('过轻')
elif 18.5 <= bmi < 24:
    print('正常')
elif 24 <= bmi < 28:
    print('过重')
elif 28 <= bmi <= 32:
    print('肥胖')
else:
    print('非常肥胖')
