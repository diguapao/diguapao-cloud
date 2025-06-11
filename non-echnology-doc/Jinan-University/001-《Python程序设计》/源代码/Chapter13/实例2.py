# ============= 服务器的代码
# 导入模块
import socket
import os, sys
# 创建TCP服务器socket
tcp_server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
# 绑定端口
tcp_server_socket.bind(("", 8080))
# 设置监听，把服务器socket由主动套接字改成被动套接字，只能接收客户端的连接请求
tcp_server_socket.listen(128)
while True:
    # 接收客户端信息
    client_socket, client_ip = tcp_server_socket.accept()
    print("客户端：", client_ip, "连接")
    # 接收下载信息
    file_name_data = client_socket.recv(1024)
    # 解码下载信息
    file_name = file_name_data.decode()
    # 判断文件是否存在
    try:
        with open(file_name, "rb") as file:
            while True:
                # 读取文件数据
                file_data = file.read(1024)
                # 数据长度不为0表示还有数据没有写入
                if file_data:
                    client_socket.send(file_data)
                # 数据为0表示传输完成
                else:
                    print(file_name, "传输成功")
                    break
    except Exception as e:
        print("传输异常：", e)
    # 关闭客户端连接
    client_socket.close()


# ============= 客户端的代码
# 导入模块
import socket, os
# 创建套接字
tcp_client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
# 连接IP地址和端口
tcp_client_socket.connect(("127.0.0.1", 8080))
down_path = input("请输入文件路径：\n")
file_name = input("请输入文件名：\n")
all_path = os.path.join(down_path, file_name)
# 文件名编码
tcp_client_socket.send(all_path.encode())
# 判断文件是否存在
all_path = os.path.join(down_path, file_name)
try:
    all_files = os.listdir(down_path)
    if file_name in all_files:
        try:
            # 文件传输
            with open(os.getcwd() + '\\' + file_name, "wb") as file:
                while True:
                    # 接收数据
                    file_data = tcp_client_socket.recv(1024)
                    # 数据长度不为0写入文件
                    if file_data:
                        file.write(file_data)
                    # 数据长度为0表示下载完成
                    else:
                        break
        # 下载出现异常时捕获异常
        except Exception as e:
            print("下载异常", e)
        # 无异常则下载成功
        else:
            print(file_name, "下载成功")
        # 关闭客户端
        tcp_client_socket.close()
    else:
        print('指定文件不存在')
except FileNotFoundError as e:
    print('指定路径不存在')
