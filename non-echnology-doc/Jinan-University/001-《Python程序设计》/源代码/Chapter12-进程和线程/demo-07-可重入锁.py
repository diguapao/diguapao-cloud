import time

print('# ===========   12.6.4可重入锁   ===========')
from threading import Thread, RLock

num = 0  # 定义全局变量
r_lock = RLock()  # 创建可重入锁


class MyThread(Thread):
    def run(self):
        # 用于在 函数内部修改全局变量 的关键字声明
        global num

        time.sleep(1)

        if r_lock.acquire():  # 将r_lock上锁
            num = num + 1  # 修改全局变量
            msg = self.name + '将num改为' + str(num)
            print(msg)
            r_lock.acquire()  # 将r_lock再次上锁
            r_lock.release()  # 将r_lock解锁
            r_lock.release()  # 将r_lock再次解锁


if __name__ == '__main__':
    for i in range(5):  # 创建5个线程
        t = MyThread()  # 创建一个线程对象
        t.start()
