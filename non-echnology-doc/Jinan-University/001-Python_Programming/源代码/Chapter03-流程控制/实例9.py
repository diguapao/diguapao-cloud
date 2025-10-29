for i in range(1, 10):     # 控制行数
    for j in range(1, i + 1):   # 控制列数
        # 输出表达式
        print(str(j) + str("*") + str(i) + "=" + str(i * j), end="\t")
    print()  # 换行输出
