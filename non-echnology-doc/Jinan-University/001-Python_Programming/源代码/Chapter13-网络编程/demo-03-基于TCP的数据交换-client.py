import socket

print(' ===========   13.4	基于TCP的数据转换   ===========')


def main():
    # 创建套接字client_socket
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    # 请求连接
    client_socket.connect(('127.0.0.1', 5678))
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
