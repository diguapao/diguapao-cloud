import socket

print(' ===========   13.3	基于UDP的网络聊天室   ===========')


def main():
    # 创建客户端的socket
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    # 向服务器发送数据
    print('----输入框----')
    while True:
        data = input()
        client_socket.sendto(data.encode("gb2312"), ("127.0.0.1", 3456))
        if data == '88':
            break

    # 关闭客户端的socket
    client_socket.close()


if __name__ == '__main__':
    main()
