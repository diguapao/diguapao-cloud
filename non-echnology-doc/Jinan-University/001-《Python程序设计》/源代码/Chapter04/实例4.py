import random
# 创建一个包含3个空列表的嵌套列表，每个空列表表示办公室
offices = [[], [], []]
# 创建列表，用于存储8位教师的姓名
names = ['张老师', '李老师', '赵老师', '高老师',
           '刘老师', '周老师', '王老师', '吴老师']
for name in names:
    # 随机生成办公室编号
    index = random.randint(0, 2)
    # 根据index获取空列表，再向空列表中添加教师的姓名
    offices[index].append(name)
# 输出分配完成的办公室
flag = 1
for tempNames in offices:
    print('办公室%d的人数为：%d' % (flag, len(tempNames)))
    flag += 1
    for name in tempNames:
        print("%s" % name, end=' ')
    print(" ")
