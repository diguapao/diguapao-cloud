import socket

print(' ===========   13.2.1	socket套接字   ===========')

# 创建socket对象，指定socket的类型为流式套接字
socket_tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
# 创建socket对象，指定socket的类型为数据报式套接字
socket_udp = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
