import socket

print(' ===========   13.6.1	单进程非阻塞服务器   ===========')


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
