print(' ===========   13.2.1	socket套接字   ===========')
import socket

# 创建socket对象，指定socket的类型为流式套接字
socket_tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
# 创建socket对象，指定socket的类型为数据报式套接字
socket_udp = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

print(' ===========   13.3	基于UDP的网络聊天室   ===========')
import socket


def main():
    # 创建服务器的socket
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    # 绑定地址
    server_socket.bind(("", 3456))
    # 接收数据并输出
    print("-------UDP聊天室-------")
    while True:
        recv_info = server_socket.recvfrom(1024)
        address = recv_info[1][0] + ':' + str(recv_info[1][1])
        print("%s" % address)
        print("%s" % recv_info[0].decode("gb2312"))
    # 关闭服务器的socket
    server_socket.close()


if __name__ == '__main__':
    main()

import socket


def main():
    # 创建客户端的socket
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    # 向服务器发送数据
    print('----输入框----')
    while True:
        data = input()
        client_socket.sendto(data.encode("gb2312"),
                             ("172.16.43.31", 3456))
        if data == '88':
            break
    # 关闭客户端的socket
    client_socket.close()


if __name__ == '__main__':
    main()

print(' ===========   13.4	基于TCP的数据转换   ===========')
import socket


def main():
    # 创建套接字server_socket
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    # 绑定地址
    server_socket.bind(("", 5678))
    # 设置最大连接数
    server_socket.listen(5)
    # 创建连接
    client_socket, address = server_socket.accept()
    print('-----TCP数据转换器-----')
    while True:
        # 接收数据
        recv_info = client_socket.recv(1024).decode('gb2312')
        string_address = address[0] + ':' + str(address[1])
        print(string_address)
        print("待处理数据：%s" % recv_info)
        # 数据处理
        if recv_info:
            data = recv_info.upper()
            client_socket.send(data.encode('gb2312'))
            print("处理结果：%s" % data)
        else:
            print('exit')
        break
    client_socket.close()
    # 关闭套接字server_socket
    server_socket.close()


if __name__ == '__main__':
    main()

import socket


def main():
    # 创建套接字client_socket
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    # 请求连接
    client_socket.connect(('172.16.43.31', 5678))
    # 发送数据
    while True:
        data = input("-----待处理数据------\n")
        client_socket.send(data.encode('gb2312'))
        recv_info = client_socket.recv(1024).decode('gb2312')
        print("------处理结果-------\n%s" % recv_info)
    # 关闭套接字client_socket
    client_socket.close()


if __name__ == '__main__':
    main()

print(' ===========   13.6	TCP并发服务器   ===========')
while True:
    # 在循环中处理连接请求
    new_socket, address = server_socket.accept()
    while True:
        # 保持不断接收数据
        recv_data = new_socket.recv(1024)

print(' ===========   13.6.1	单进程非阻塞服务器   ===========')
import socket


def main():
    # 创建服务器的套接字server_socket
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    # 重复使用绑定的信息
    server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    local_addr = ('', 6789)
    # 绑定本地IP地址以及端口
    server_socket.bind(local_addr)
    # 将server_socket变为监听套接字
    server_socket.listen(3)
    # 将server_socket设置为非阻塞
    server_socket.setblocking(False)
    # 定义一个列表，用于保存所有已经连接的客户端的信息
    client_address_list = []
    while True:
        # 等待一个新的客户端到来
        try:
            new_socket, client_address = server_socket.accept()
        except:
            pass
        else:
            print("一个新的客户端到来：%s" % str(client_address))
            # 将新套接字new_socket设置为非阻塞
            new_socket.setblocking(False)
            # 将本次建立连接后获取的套接字添加到已连接客户端列表中
            client_address_list.append((new_socket, client_address))
        for client_socket, client_address in client_address_list:
            try:
                recv_info = client_socket.recv(1024).decode('gb2312')
            except:
                pass
            else:
                if len(recv_info) > 0:
                    # 数据处理
                    print('待处理数据：%s' % recv_info)
                    data = recv_info.upper()
                    client_socket.send(data.encode('gb2312'))
                    print('处理结果：%s' % data)
                else:
                    # 断开连接
                    client_socket.close()
                    # 将套接字从已连接客户端列表移除
                    client_address_list.remove((client_socket,
                                                client_address))
                    print("%s 已断开连接" % str(client_address))
    server_socket.close()


if __name__ == '__main__':
    main()

import socket


def main():
    # 创建客户端的套接字client_socket
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    # 请求连接
    client_socket.connect(('172.16.43.31', 6789))
    # 发送数据
    while True:
        data = input("-----待处理数据------\n")
        client_socket.send(data.encode('gb2312'))
        recv_info = client_socket.recv(1024).decode('gb2312')
        print("------处理结果-------\n%s" % recv_info)
    # 关闭客户端的套接字
    server_socket.close()


if __name__ == '__main__':
    main()

print(' ===========   13.6.2	多进程并发服务器   ===========')
# ===========  服务器的代码
import socket
import multiprocessing


# 与客户端进行交互
def deal_with_client(new_socket, client_address):
    while True:
        recv_data = new_socket.recv(1024).decode('gb2312')
        if len(recv_data) > 0:
            print('待处理数据：%s:%s' % (str(client_address), recv_data))
            data = recv_data.upper()
            new_socket.send(data.encode('gb2312'))
        else:
            print('客户端[%s]已关闭' % str(client_address))
            break
    new_socket.close()


def main():
    # 创建服务器套接字server_socket
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    # 端口快速重启用
    server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    local_address = ('', 8081)
    server_socket.bind(local_address)
    server_socket.listen(5)
    print('-----服务器------')
    try:
        while True:
            # 处理客户端连接请求
            new_socket, client_address = server_socket.accept()
            print('一个新的客户端到达[%s]' % str(client_address))
            # 创建子进程与客户端进行交互
            client = multiprocessing.Process(target=deal_with_client,
                                             args=(new_socket, client_address))
            client.start()
            # 关闭父进程中的套接字new_socket
            new_socket.close()
    finally:
        # 关闭服务器套接字
        server_socket.close()


if __name__ == '__main__':
    main()

# ===========  客户端的代码
import socket


def main():
    # 创建套接字client _socket
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    # 请求连接
    client_socket.connect(('172.16.43.31', 8081))
    # 发送数据
    while True:
        data = input("-----待处理数据------\n")
        client_socket.send(data.encode('gb2312'))
        recv_info = client_socket.recv(1024).decode('gb2312')
        print("------处理结果-------\n%s" % recv_info)
    # 关闭聊天室套接字server_socket
    server_socket.close()


if __name__ == '__main__':
    main()

print(' ===========   13.6.3	多线程并发服务器   ===========')
import socket
import threading


# 与客户端进行交互
def deal_with_client(new_socket, client_address):
    while True:
        recv_data = new_socket.recv(1024).decode('gb2312')
        if len(recv_data) > 0:
            print('待处理数据%s:%s' % (str(client_address), recv_data))
            data = recv_data.upper()
            new_socket.send(data.encode('gb2312'))
        else:
            print('客户端[%s]已关闭' % str(client_address))
            break
    new_socket.close()


def main():
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    local_address = ('', 8082)
    server_socket.bind(local_address)
    server_socket.listen(5)
    print('-----服务器-----')
    try:
        while True:
            new_socket, client_address = server_socket.accept()
            print('一个新的客户端到达[%s]' % str(client_address))
            # 创建线程，与客户端进行交互
            client = threading.Thread(target=deal_with_client,
                                      args=(new_socket, client_address))
            client.start()
    finally:
        server_socket.close()


if __name__ == '__main__':
    main()
