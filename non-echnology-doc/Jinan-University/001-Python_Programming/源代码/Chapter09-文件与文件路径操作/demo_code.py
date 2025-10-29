print('# ===========   9.1.1打开文件   ===========')
txt_data1 = open('test_file.txt', 'r')
# txt_data2 = open('test_file.txt', ' w+')

print('# ===========   9.1.2关闭文件   ===========')
txt_data1.close()
# txt_data2.close()
with open('test_file.txt', 'w+') as file:
    print('我是with语句')

print('# ===========   9.2.1读取文件   ===========')
file_obj = open('test_file2.txt', mode='r')
print(file_obj.read(5))  # 读取五个字符的数据
print('--------------------')
print(file_obj.read(8))  # 继续读取五个字符的数据
print('--------------------')
print(file_obj.read())  # 读取剩余的全部数据
file_obj.close()

print("-------------------- 下面是避免中文乱码版本 --------------------")
file_obj = open('test_file3.txt', mode='r', encoding='utf-8')
print(file_obj.read(4))  # 读取五个字符的数据
print('--------------------')
print(file_obj.read(9))  # 继续读取五个字符的数据
print('--------------------')
print(file_obj.read())  # 读取剩余的全部数据
file_obj.close()

file_obj = open('test_file2.txt', mode='r')
print("读取一行数据：" + file_obj.readline())  # 读取一行数据
print("继续读取一行数据：" + file_obj.readline())  # 继续读取一行数据
file_obj.close()

file_obj = open('test_file3.txt', mode='r', encoding='utf-8')
print("读取全部数据(避免中文乱码)：")
print(file_obj.readlines())  # 读取全部数据
file_obj.close()

print('# ===========   9.3.1写入文件   ===========')
txt_data = open('test_file-w.txt', encoding='utf-8', mode='a+')
print(txt_data.write('\n北宋文学家、书法家、画家。'))
txt_data.close()

txt_data = open('test_file-w.txt', encoding='utf-8', mode='a+')
txt_data.writelines(['\n苏轼\n', '字子瞻\n', '号东坡居士'])
txt_data.close()

print('# ===========   9.4	文件的定位读写   ===========')
file = open('test_file-w.txt', mode='r', encoding='utf-8')
print("获取读写指针的位置：" + str(file.tell()))  # 获取读写指针的位置
file.read(7)  # 读取7个字符
print("读取7个字符，再次获取读写指针的位置：" + str(file.tell()))  # 再次获取读写指针的位置
file.close()

file = open('test_file-w.txt', mode='r', encoding='utf-8')
file.seek(15, 0)  # 将读写指针移动到文件开头偏移15个字节的位置
print("将读写指针移动到文件开头偏移15个字节的位置后读取7个字符：" + file.read(7))  # 读取7个字符
file.close()

print('# ===========   9.5.1文件的备份   ===========')
file_name = "test_file3.txt"
source_file = open(file_name, 'r', encoding='utf-8')  # 打开文件
all_data = source_file.read()  # 从文件中读取全部数据
flag = file_name.split('.')
new_file_name = flag[0] + "-备份" + ".txt"

# 文件存在则先删除掉
import os

if os.path.exists(new_file_name):
    os.remove(new_file_name)

new_file = open(new_file_name, 'w', encoding='utf-8')  # 创建新文件
new_file.write(all_data)  # 向新文件中写入数据
source_file.close()  # 关闭源文件
new_file.close()  # 关闭新文件

print('# ===========   9.5.2文件的重命名   ===========')
import os

os.rename("test_file.txt", "new_file.txt")
os.rename("new_file.txt", "test_file.txt")

print('# ===========   9.6.1创建目录   ===========')
import os

# 创建单层目录
os.mkdir('D:\ProgramDev2')
# 创建多层目录
os.makedirs('D:\ProgramDev2\python\Chapter09-文件与文件路径操作')

print('# ===========   9.6.2删除目录   ===========')
# 删除单层目录
os.rmdir('D:\ProgramDev2\python\Chapter09-文件与文件路径操作')
# 删除多层目录
import shutil

dir_path = r'D:\ProgramDev2'
shutil.rmtree(dir_path)  # 先删一次
if os.path.exists(dir_path):  # 再删一次
    try:
        shutil.rmtree(dir_path)
        print(f"目录 {dir_path} 及其所有内容已被删除")
    except PermissionError:
        print(f"没有权限删除目录 {dir_path} 或者某些文件正在被使用")
    except Exception as e:
        print(f"删除目录 {dir_path} 时发生未知错误: {e}")
else:
    print(f"目录 {dir_path} 不存在")

print('# ===========   9.6.3获取目录的文件列表   ===========')
import os

current_path = r"D:\DiGuaPao\gitee\diguapao-cloud\non-echnology-doc\Jinan-University\001-《Python程序设计》\源代码\Chapter09-文件与文件路径操作"
print(os.listdir(current_path))

print('# ===========   9.7.1相对路径与绝对路径   ===========')
import os

print("“new_file.txt”是绝对路径吗：" + str(os.path.isabs("new_file.txt")))
print('“D:\Python项目\\new_file.txt”是绝对路径吗：' + str(os.path.isabs("D:\Python项目\new_file.txt")))

import os

print("“new_file.txt”所在绝对路径：" + os.path.abspath("new_file.txt"))

print('# ===========   9.7.2获取当前路径   ===========')
import os

current_path = os.getcwd()
print("当前路径："+current_path)

print('# ===========   9.7.3检测路径的有效性   ===========')
import os

dir_path = input('请输入目录的名称：')
yes_or_no = os.path.exists(dir_path)  # 检测目录是否存在
if yes_or_no is False:  # 目录不存在
    os.mkdir(dir_path)  # 创建相应的目录
    new_file = open(os.getcwd() + '\\' + dir_path + "\\" +
                    "dir_demo.txt", 'w', encoding='utf-8')
    new_file.write("itcast")
    print("写入成功")
    new_file.close()
else:  # 目录存在
    print("该目录已存在")

print('# ===========   9.7.4路径的拼接   ===========')
import os

path_one = 'Python项目'
path_two = 'python_path'
splici_path = os.path.join(path_one, path_two)
print(splici_path)

import os

path_one = 'D:\Python项目'
path_two = ''
splicing_path = os.path.join(path_one, path_two)
print(splicing_path)
