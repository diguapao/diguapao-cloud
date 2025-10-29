import time
from threading import Thread, Lock

print('# ===========   12.6.3死锁   ===========')


# 2.两个线程互相使用对方的互斥锁
class ThreadOne(Thread):
    def run(self):
        if lock_a.acquire():  # lock_a上锁
            print(self.name + '：lock_a上锁')
            time.sleep(1)
            if lock_b.acquire():  # lock_b上锁
                print(self.name + '：lock_b解锁')
                lock_b.release()  # lock_b解锁
            lock_a.release()  # lock_a解锁


class ThreadTwo(Thread):
    def run(self):
        if lock_b.acquire():  # lock_b上锁
            print(self.name + '：lock_b上锁')
            time.sleep(1)
            if lock_a.acquire():  # lock_a上锁
                print(self.name + '：lock_a解锁')
                lock_a.release()  # lock_a解锁
            lock_b.release()  # lock_b解锁


if __name__ == '__main__':
    lock_a = Lock()
    lock_b = Lock()
    thread_one = ThreadOne(name='线程1')
    thread_two = ThreadTwo(name='线程2')
    thread_one.start()
    thread_two.start()
