from socket import *
import threading
# 创建互斥锁
lock = threading.Lock()
# 记录计算机中开放端口的数量
open_num = 0
# 线程列表
threads = []
def port_scanner(host, port):
    global open_num
    try:
        # 创建表示套接字的socket对象，通信方式为流式套接字
        s = socket(AF_INET, SOCK_STREAM)
        # 根据指定的地址向服务器发起连接请求
        s.connect((host, port))
        # 上锁
        lock.acquire()
        open_num += 1
        print('[+] %d open' % port)
        # 解锁
        lock.release()
        # 关闭套接字
        s.close()
    except:
        pass
def main():
    # 设置网络操作的默认超时时间，时长为1秒
    setdefaulttimeout(1)
    for p in range(1, 65534):
        # 创建线程
        t = threading.Thread(target=port_scanner, args=('127.0.0.1', p))
        # 将线程加入到列表中
        threads.append(t)
        # 启动线程
        t.start()
    for t in threads:
        t.join()
    print('[*] 扫描完成！')
    print('[*] 一共有 %d 个开放端口 ' % (open_num))
if __name__ == '__main__':
    main()
