person_info = []     # 定义一个列表，用于保存所有的联系人信息
# 打印手机通讯录的功能菜单
print("=" * 20)
print('欢迎使用通讯录：')
print("1.添加联系人")
print("2.查看通讯录")
print("3.删除联系人")
print("4.修改联系人")
print("5.查找联系人")
print("6.退出")
print("=" * 20)
while True:
    per_dict = {}
    fun_num = input('请输入功能序号：')
    if fun_num == '1':
        per_name = input('请输入联系人的姓名：')
        phone_num = input('请输入联系人的手机号：')
        per_email = input('请输入联系人的邮箱：')
        per_address = input('请输入联系人的地址：')
        # 判断输入的是否为空
        if per_name.strip() == '' or phone_num.strip() == '' \
                or per_email.strip() == '' or per_address.strip() == '':
            print('请输入正确信息')
            continue
        else:
            per_dict.update({'姓名': per_name,
                                '手机号': phone_num,
                                '电子邮箱': per_email,
                                '联系地址': per_address})
            person_info.append(per_dict)  # 保存到列表中
            print('保存成功')
    elif fun_num == '2':
        if len(person_info) == 0:
            print('通讯录无信息')
        for i in person_info:
            print('--*' * 6)
            for title, info in i.items():
                print(title + ':' + info)
            print('--*' * 6)
    elif fun_num == '3':  # 删除
        if len(person_info) != 0:
            del_name = input('请输入要删除的联系人姓名：')
            for i in person_info:
                if del_name in i.values():
                    person_info.remove(i)
                    print(person_info)
                    print('删除成功')
                else:
                    print('该联系人不在通讯录中')
        else:
            print('通讯录无信息')
    elif fun_num == '4':  # 修改
        if len(person_info) != 0:
            modi_info = input('请输入要修改的联系人姓名：')
            for i in person_info:
                if modi_info in i.values():
                    # 获取所在元组在列表中的索引位置
                    index_num = person_info.index(i)
                    dict_cur_perinfo = person_info[index_num]
                    for title, info in dict_cur_perinfo.items():
                        print(title + ':' + info)
                    modi_name = input('请输入新的姓名：')
                    modi_phone = input('请输入新的手机号：')
                    modi_email = input('请输入新的邮箱：')
                    modi_address = input('请输入新的地址：')
                    dict_cur_perinfo.update(姓名= modi_name)
                    dict_cur_perinfo.update(手机号= modi_phone)
                    dict_cur_perinfo.update(电子邮箱= modi_email)
                    dict_cur_perinfo.update(联系地址= modi_address)
                    print(person_info)
        else:
            print('通讯录无信息')
    elif fun_num == '5':  # 查找
        if len(person_info) != 0:
            query_name = input('请输入要查找的联系人姓名：')
            for i in person_info:
                if query_name in i.values():
                    index_num = person_info.index(i)
                    for title, info in person_info[index_num].items():
                        print(title + ':' + info)
                    break
            else:
                print('该联系人不在通讯录中')
        else:
            print('通讯录无信息')
    elif fun_num == '6':  # 退出
        break
