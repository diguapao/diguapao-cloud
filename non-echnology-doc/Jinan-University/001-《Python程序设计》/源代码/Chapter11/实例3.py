import re
def user_registration():
    print("注册提示：")
    print("用户名长度为6~10个字符，可使用汉字、字母、数字、下滑线开头\n"
          "密码长度为6~10个字符，包含大小写字母及下划线，需以字母开头\n"
          "手机号为中国大陆手机号")
    user_name = input("请输入用户名：")
    user_pwd = input("请输入密码：")
    user_phone_num = input("请输手机号：")
    while True:
        # 用户名长度为6~10个字符包含汉字、大小写字母、和下划线
        reg_user = re.compile(r"^[\u4E00-\u9FA5A-Za-z0-9_]{6,10}$")
        # 密码长度为6~10个字符必须以字母开头，包含字母数字下划线
        reg_pwd = re.compile(r"^[a-zA-Z]\w{5,9}$")
        # 手机号码匹配规则
        reg_phone = re.compile(r'^1[03456789]\d{9}$')
        if re.findall(reg_user, user_name):
            if re.findall(reg_pwd, user_pwd):
                if re.findall(reg_phone, user_phone_num):
                    print("注册成功")
                    break
                else:
                    print("手机号码格式不正确")
                    user_phone_num = input("请重新输入手机号：")
            else:
                user_pwd = input("请重新输入密码：")
        else:
            user_name = input("请重新输入用户名：")
if __name__ == '__main__':
    user_registration()
