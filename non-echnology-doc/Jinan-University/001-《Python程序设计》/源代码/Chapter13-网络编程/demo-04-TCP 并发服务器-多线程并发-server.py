import socket
import threading

print(' ===========   13.6.3	多线程并发服务器   ===========')


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
