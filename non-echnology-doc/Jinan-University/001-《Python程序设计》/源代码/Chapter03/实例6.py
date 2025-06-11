# 接收密码
raw_data = input('请输入密码：')
num_asc = 0     # 定义变量，用于累加ASCII码的值
str_pwd = ''    # 定义变量，用于拼接ASCII码的值
for i in raw_data:
    ascii_val = ord(i)                 # 获取每个数字对应的ASCII码值
    num_asc = ascii_val + num_asc    # 将所有ASCII码的值累加求和
    str_pwd += str(ascii_val)         # 拼接ASCII码值
    reversal_num = str_pwd[::-1]     # 将拼接的ASCII值反转
    encryption_num = int(reversal_num) + num_asc # 将反转结果与累加结果相加
print(f"加密后的密码为：{encryption_num}")
