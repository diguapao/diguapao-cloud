# ===========   9.1.1打开文件   ===========
txt_data = open('test_file.txt', 'r')
# txt_data = open('test_file.txt', ' w+')


# ===========   9.1.2关闭文件   ===========
# txt_data.close()
# with open('test_file.txt', 'w+') as file:
#     print('我是with语句')


# ===========   9.2.1读取文件   ===========
# file_obj = open('test_file.txt', mode='r')
# print(file_obj.read(5))   # 读取五个字符的数据
# print('--------------------')
# print(file_obj.read(8))   # 继续读取五个字符的数据
# print('--------------------')
# print(file_obj.read())    # 读取剩余的全部数据
# file_obj.close()

# file_obj = open('test_file.txt', mode='r')
# print(file_obj.readline())   # 读取一行数据
# print('--------------------')
# print(file_obj.readline())   # 继续读取一行数据
# file_obj.close()

# file_obj = open('test_file.txt', mode='r')
# print(file_obj.readlines())      # 读取全部数据
# file_obj.close()


# ===========   9.3.1写入文件   ===========
# txt_data = open('test_file.txt', encoding='utf-8', mode='a+')
# print(txt_data.write('\n北宋文学家、书法家、画家。'))
# txt_data.close()

# txt_data = open('test_file.txt', encoding='utf-8', mode='a+')
# txt_data.writelines(['苏轼\n', '字子瞻\n', '号东坡居士'])
# txt_data.close()


# ===========   9.4	文件的定位读写   ===========
# file = open('test_file.txt', mode='r', encoding='utf-8')
# print(file.tell())      # 获取读写指针的位置
# file.read(7)             # 读取7个字符
# print(file.tell())      # 再次获取读写指针的位置
# file.close()

# file = open('test_file.txt', mode='r', encoding='utf-8')
# file.seek(15, 0)          # 将读写指针移动到文件开头偏移15个字节的位置
# print(file.read(7))       # 读取7个字符
# file.close()


# ===========   9.5.1文件的备份   ===========
# file_name = "test_file.txt"
# source_file = open(file_name, 'r', encoding='utf-8')     # 打开文件
# all_data = source_file.read()        # 从文件中读取全部数据
# flag = file_name.split('.')
# new_file_name = flag[0]+"备份"+".txt"
# new_file = open(new_file_name,'w', encoding='utf-8')     # 创建新文件
# new_file.write(all_data)             # 向新文件中写入数据
# source_file.close()                   # 关闭源文件
# new_file.close()                       # 关闭新文件


# ===========   9.5.2文件的重命名   ===========
# import os
# os.rename("txt_file.txt", "new_file.txt")


# ===========   9.6.1创建目录   ===========
# import os
# os.mkdir('D:\ProgramDev2\python')


# ===========   9.6.2删除目录   ===========
# os.rmdir('D:\ProgramDev2\python')
#
# import shutil
# shutil.rmtree('D:\ProgramDev2\python')


# ===========   9.6.3获取目录的文件列表   ===========
# import os
# current_path = r"D:\Python项目"
# print(os.listdir(current_path))


# ===========   9.7.1相对路径与绝对路径   ===========
# import os
# print(os.path.isabs("new_file.txt"))
# print(os.path.isabs("D:\Python项目\new_file.txt"))

# import os
# print(os.path.abspath("new_file.txt"))


# ===========   9.7.2获取当前路径   ===========
# import os
# current_path = os.getcwd()
# print(current_path)


# ===========   9.7.3检测路径的有效性   ===========
# import os
# dir_path = input('请输入目录的名称：')
# yes_or_no = os.path.exists(dir_path)  # 检测目录是否存在
# if yes_or_no is False:    # 目录不存在
#     os.mkdir(dir_path)     # 创建相应的目录
#     new_file = open(os.getcwd() + '\\' + dir_path + "\\" +
#                     "dir_demo.txt", 'w', encoding='utf-8')
#     new_file.write("itcast")
#     print("写入成功")
#     new_file.close()
# else:   # 目录存在
#    print("该目录已存在")


# ===========   9.7.4路径的拼接   ===========
# import os
# path_one = 'Python项目'
# path_two = 'python_path'
# splici_path = os.path.join(path_one, path_two)
# print(splici_path)

# import os
# path_one = 'D:\Python项目'
# path_two = ''
# splicing_path = os.path.join(path_one, path_two)
# print(splicing_path)

