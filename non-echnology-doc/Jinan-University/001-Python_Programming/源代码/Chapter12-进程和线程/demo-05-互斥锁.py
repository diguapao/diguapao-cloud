from threading import *

print('# ===========   12.6.2互斥锁   ===========')

total_ticket = 100  # 总票数


# 定义任务函数，用于实现卖票行为
def sale_ticket():
    # 用于在 函数内部修改全局变量 的关键字声明
    global total_ticket
    while total_ticket > 0:
        mutex_lock.acquire()  # 上锁，将互斥锁状态改为锁定
        if total_ticket > 0:
            total_ticket -= 1
            print('%s卖出一张票' % current_thread().name)
        print('剩余票数： %d' % total_ticket)
        mutex_lock.release()  # 解锁，将互斥锁状态改为非锁定


if __name__ == '__main__':
    mutex_lock = Lock()  # 创建互斥锁
    # 创建线程，表示卖票窗口1
    thread_one = Thread(target=sale_ticket, name='窗口1')
    thread_one.start()
    # 创建线程，表示卖票窗口2
    thread_two = Thread(target=sale_ticket, name='窗口2')
    thread_two.start()
