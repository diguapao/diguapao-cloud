for i in range(1, 101):
    # 检测数字是否有7
    is_include = str(i).find("7") == -1
    # 检测数字是否是7的倍数
    is_multiple = i % 7 != 0
    if is_include and is_multiple:  # 数字里面没有7且不是7的倍数
        print(i, end="、")             # 直接输出数字，末尾是顿号
    elif not is_include or not is_multiple:  # 数字里面有7或是7的倍数
        print("*", end='、')           # 直接输出*，末尾是顿号
