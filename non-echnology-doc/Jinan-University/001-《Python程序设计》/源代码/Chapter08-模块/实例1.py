import random
def verifycode():
    code_list = ''
    # 每一位字符都有三种可能，分别是大写字母、小写字母、数字
    for i in range(6):                # 控制验证码的字符个数
        # 随机生成一个状态码，1表示大写字母，2表示小写字母，3表示数字
        state = random.randint(1, 3)
        if state == 1:  # 大写字母
            first_kind = random.randint(65, 90)
            random_uppercase = chr(first_kind) # 使用chr()函数获取对应ASCII值
            code_list = code_list + random_uppercase
        elif state == 2:
            second_kinds = random.randint(97, 122)  # 小写字母
            random_lowercase = chr(second_kinds)
            code_list = code_list + random_lowercase
        elif state == 3:  # 数字
            third_kinds = random.randint(0, 9)
            code_list = code_list + str(third_kinds)
    return code_list
result = verifycode()
print(result)
