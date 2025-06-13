friends = []   # 保存所有的好友信息
# 输出功能菜单
print("欢迎使用好友系统")
print("1：添加好友")
print("2：删除好友")
print("3：备注好友")
print("4：展示好友")
print("5：退出")
# 根据用户输入执行相应操作
while True:
    num = int(input("请输入您的选项："))
    if num == 1:   # 添加好友
        add_friend = input("请输入要添加的好友：")
        friends.append(add_friend)   # 将好友姓名添加到列表friends中
        print('好友添加成功')
    elif num == 2:  # 删除好友
        del_friend = input("请输入删除好友姓名：")
        friends.remove(del_friend)   # 将好友姓名从列表friends中删除
        print("删除成功")
    elif num == 3:  # 备注好友
        before_friend = input("请输入要修改的好友姓名：")
        after_friend = input("请输入修改后的好友姓名：")
        # 获取要修改好友姓名的索引friend_index
        friend_index = friends.index(before_friend)
        # 将索引friend_index对应的元素进行修改
        friends[friend_index] = after_friend
        print("备注成功")
    elif num == 4:   # 展示好友
        if len(friends) == 0:
            print("好友列表为空")
        else:
            # 遍历列表，输出列表中的每个好友姓名
            for i in friends:
                print(i)
    elif num == 5:  # 退出
        break
