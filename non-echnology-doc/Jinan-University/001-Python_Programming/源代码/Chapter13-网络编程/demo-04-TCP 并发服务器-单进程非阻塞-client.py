import socket


def main():
    # 创建客户端的套接字client_socket
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    # 请求连接
    client_socket.connect(('127.0.0.1', 6789))
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
