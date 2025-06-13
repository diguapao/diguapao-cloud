count = 0    # 记录用户输错密码的次数
while count < 3:
    username = input("请输入您的账号：")
    pwd = input("请输入您的密码：")
    if username == 'admin' and pwd == '123':  # 账号与密码全部正确
        print('登录成功')
        break     # 结束循环
    else:  # 账号或密码错误
        print("用户名或密码错误")
        count += 1          # 错误次数累加一次
        # 判断输错密码的次数是否等于三次
        if count == 3:
            print("输入错误次数过多，请稍后再试")
        else:
            print(f"您还有{3-count}次机会")     # 显示剩余次数
