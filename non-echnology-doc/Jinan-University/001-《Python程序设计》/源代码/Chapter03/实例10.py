setting_num = int(input("请输入设定数字:"))      # 设定数字
guess_count = 1                                     # 猜测次数
while guess_count <= 5:
    guess_num = int(input("请输入猜测数字:"))     # 猜测数字
    if 1 <= guess_num <= 100:
        if guess_num == setting_num:             # 判断猜测数字与设定数字是否相等
            print("恭喜你，猜对了！已经猜测的次数是：", guess_count)
            break                                     # 跳出整个循环
        elif guess_num > setting_num:            # 判断猜测数字是否大于设定数字
            print("猜大了！已经猜测的次数是：", guess_count)
        else:
            print("猜小了！已经猜测的次数是：", guess_count)
        guess_count = guess_count + 1            # 猜测次数累加一次
    else:
        print("数字输入不合理，请输入1~100的数字")
