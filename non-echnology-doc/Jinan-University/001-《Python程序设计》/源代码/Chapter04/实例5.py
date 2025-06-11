uppercase_numbers = ("零", "壹", "贰", "叁", "肆","伍",
                         "陆", "柒", "捌", "玖")
number = input("请输入一个数字：")
for i in range(len(number)):
    print(uppercase_numbers[int(number[i])], end="")