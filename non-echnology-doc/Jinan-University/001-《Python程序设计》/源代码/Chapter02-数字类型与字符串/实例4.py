# 接收用户的基本信息
name = input('请输入您的姓名：')            # 姓名
position = input('请输入您的职位：')       # 职位
phone = input('请输入您的电话：')           # 电话
email = input('请输入您的邮箱：')           # 邮箱
# 插入用户的基本信息
print('=============================')
print(f'姓名：{name}')         # 使用{name}标注姓名所在的位置
print(f'职位：{position}')     # 使用{position}标注职位所在的位置
print(f'电话：{phone}')        # 使用{phone}标注电话所在的位置
print(f'邮箱：{email}')        # 使用{email}标注邮箱所在的位置
print('=============================')
