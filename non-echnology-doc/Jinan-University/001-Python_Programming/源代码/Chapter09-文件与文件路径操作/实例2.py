import json
class TelephoneBook:
    def show_menu(self):
        print("*" * 20)
        print("欢迎使用[通讯录] V1.0")
        print("1. 新建联系人")
        print("2. 查询联系人")
        print("0. 退出系统")
        print("*" * 20)

    def add_info(self):
        name_str = input("请输入姓名:")
        phone_num = input("请输入电话:")
        qq_num = input("请输入QQ号码:")
        mail_adr = input("请输入邮箱:")
        # 将数据封装到字典中
        card_dict = {"姓名": name_str, "手机号": phone_num,
                        "qq": qq_num, "mail": mail_adr}
        f = open("通讯录.txt", mode='a+', encoding='utf-8')
        # 将字典转换为str，然后再使用write()写入到通讯录的文本文件中
        f.write(str(card_dict) + '\n')
        f.close()
        print(f"成功添加{name_str}为联系人")

    def show_info(self):
        file = open("通讯录.txt", mode='r', encoding='utf-8')
        # 如果通讯录.txt文件不为空时，执行下面代码
        if len(file.read()) != 0:
            # 保证每次从开始位置读取
            file.seek(0, 0)
            # 读取通讯录.txt文件中的内容
            file_data = file.read()
            # 对字符串进行分隔
            split_info = file_data.split('\n')
            # 删除多余的字符串
            split_info.remove(split_info[len(split_info) - 1])
            name = input("请输入要查询的姓名：")
            name_li = []       # 用于存储联系人姓名的列表
            all_info_li = []   # 用于存储所有信息的列表
            for i in split_info:
                # 将单引号替换为双引号
                dict_info = json.loads(i.replace("\'", '\"'))
                all_info_li.append(dict_info)
                # 获取所有联系人的姓名
                name_li.append(dict_info['姓名'])
            if name in name_li:
                for person_info in all_info_li:
                    for title_key, name_value in person_info.items():
                        if name_value == name:
                            for title, info_value in person_info.items():
                                print(title + ":" + info_value)
            else:
                print('联系人不存在')
        else:
            print("通讯录为空")

    def main(self):
        while True:
            self.show_menu()
            action_str = input("请选择操作功能:")  # 判断用户输入的功能指令
            if action_str.isdigit() is True:
                if int(action_str) == 1:
                    self.add_info()
                elif int(action_str) == 2:
                    self.show_info()
                elif int(action_str) == 0:
                    break
            else:
                print('请输入正确的指令')