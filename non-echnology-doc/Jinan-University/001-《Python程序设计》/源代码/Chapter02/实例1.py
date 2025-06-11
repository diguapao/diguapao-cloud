# 接收用户输入的身高和体重
height = input('请输入您的身高(m)：')
weight = input('请输入您的体重(kg)：')
# 转换身高和体重数据的类型
height = float(height)
weight = float(weight)
# 根据公式计算BMI指数
bmi = weight / height / height
print('您的BMI指数的值为:', bmi)
