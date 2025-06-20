import re
n = input("请输入手机号码：")
if re.match(r'1[345789]\d{9}', n):
    print(n, "是合法的手机号码")
    # 中国移动：
    if re.match(r"13[456789]\d{8}", n) or \
            re.match(r"15[012789]\d{8}", n) or \
            re.match(r"147\d{8}|178\d{8}", n) or \
            re.match(r"18[23478]\d{8}", n):
        print("运营商：中国移动")
    # 中国联通
    elif re.match(r'13[012]\d{8}', n) or \
            re.match(r"18[56]\d{8}", n) or \
            re.match(r"15[56]\d{8}", n) or \
            re.match(r"176\d{8}", n) or \
            re.match(r"145\d{8}", n):
        print("运营商：中国联通")
    else:
        # 中国电信
        print("运营商：中国电信")
else:
    print("请输入正确的手机号")
