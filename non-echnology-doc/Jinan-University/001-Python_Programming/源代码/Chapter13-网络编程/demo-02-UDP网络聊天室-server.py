import socket

print(' ===========   13.3	基于UDP的网络聊天室   ===========')


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
