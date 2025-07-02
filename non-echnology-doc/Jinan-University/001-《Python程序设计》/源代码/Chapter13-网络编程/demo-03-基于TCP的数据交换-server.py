import socket

print(' ===========   13.4	基于TCP的数据转换   ===========')


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
