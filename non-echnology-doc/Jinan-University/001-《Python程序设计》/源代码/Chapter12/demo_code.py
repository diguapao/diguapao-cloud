# ===========   12.2.1通过fork()函数创建进程   ===========
# import os
# import time
# value = os.fork()        # 创建子进程
# if value == 0:           # 子进程执行if子句
#     print('---子进程---')
#     time.sleep(2)
# else:                      # 父进程执行else子句
#     print('---父进程---')
#     time.sleep(2)

# import os
# import time
# print('---第一次调用fork()---')
# value = os.fork()      # 创建子进程，此时进程的总数量为2
# if value == 0:          # 子进程执行if子句
#     print('---进程1---')
#     time.sleep(2)
# else:                     # 父进程执行else子句
#     print('---进程2---')
#     time.sleep(2)
# print('---第二次调用fork()---')
# value = os.fork()       # 创建子进程，此时进程的总数量为4
# if value == 0:           # 子进程执行if子句
#     print('---进程3---')
#     time.sleep(2)
# else:                      # 父进程执行else子句
#     print('---进程4---')
#     time.sleep(2)

# import os
# process = os.fork()    # 创建子进程
# if process == 0:
#     print('我是子进程%d，父进程是%d'%(os.getpid(), os.getppid()))
# else:
#     print('我是父进程%d, 子进程是%d'%(os.getpid(), process))


# ===========   12.2.2通过Process类创建进程   ===========
# from multiprocessing import Process
# import os
# # 进程要执行的目标函数
# def do_task():
#     print('子进程 %s' % os.getpid())  # 获取子进程的进程ID
# # 创建子进程，指定该进程要执行的目标函数为do_task
# process = Process(target=do_task)

# from multiprocessing import Process
# import time
# import os
# # 定义一个继承自Process的类
# class MyProcess(Process):
#     # 重写__init__()方法，定义子进程的初始化逻辑
#     def __init__(self, interval):
#         super().__init__()            # 完成父类Process的初始化逻辑
#         self.interval = interval     # 间隔秒数
#     # 重写run()方法，定义子进程要执行的任务逻辑
#     def run(self):
#         time_start = time.time()     # 获取任务开始执行的时间戳
#         time.sleep(self.interval)
#         time_stop = time.time()      # 获取任务结束执行的时间戳
#         print("子进程%s执行结束，耗时%0.2f秒" % (os.getpid(),
#                time_stop - time_start))
# if __name__ == '__main__':
#     my_process = MyProcess(5)     # 根据MyProcess类创建子进程
#     my_process.start()              # 启动进程


# ===========   12.2.3通过Pool类批量创建进程   ===========
# from multiprocessing import Pool
# pool = Pool(processes=5)

# from multiprocessing import Pool
# import time
# import os
# # 表示任务的函数
# def work(num):
#     print('进程%s：执行任务%d' % (os.getpid(), num))
#     time.sleep(2)
# if __name__ == '__main__':
#     pool = Pool(3)  # 创建进程池，指定最大进程数量为3
#     for i in range(9):
#         pool.apply_async(work, (i,))  # 非阻塞式地给进程池添加任务
#     time.sleep(3)
#     print('主进程执行结束')

# from multiprocessing import Pool
# import time
# import os
# # 表示任务的函数
# def work(num):
#     print('进程%s：执行任务%d' % (os.getpid(), num))
#     time.sleep(2)
# if __name__ == '__main__':
#     pool = Pool(3)  # 创建进程池，指定最大进程数量为3
#     for i in range(9):
#         pool.apply_async(work, (i,))  # 非阻塞式地给进程池添加任务
#     time.sleep(3)
#     pool.close()           # 关闭进程池
#     pool.join()            # 阻塞主进程
#     print('主进程执行结束')

# from multiprocessing import Pool
# import time
# import os
# def work(num):
#     print('进程%s： 执行任务%d'% (os.getpid(), num))
#     time.sleep(2)
# if __name__ == '__main__':
#     pool = Pool(3)                      # 创建进程池，指定最大进程数量为3
#     for i in range(9):
#         pool.apply(work, (i,))        # 阻塞式地给进程池添加任务
#     time.sleep(3)
#     print('主进程执行结束')


# ===========   12.3	进程间通信   ===========
# from multiprocessing import Process
# from multiprocessing import Queue
# def write_task(queue):
#     count = 10                            # 定义局部变量
#     queue.put(count, block=False)     # 将局部变量插入到队列中
# def read_task(queue):
#     print(queue.get(block=False))     # 从队列中读取数据
# if __name__ == '__main__':
#     queue = Queue()                      # 创建队列，队列的长度没有限制
#     # 创建两个进程分别执行任务函数
#     process_one = Process(target=write_task, args=(queue,))
#     process_another = Process(target=read_task, args=(queue,))
#     # 启动进程
#     process_one.start()
#     process_another.start()


# ===========   12.5.1线程的创建和启动   ===========
# from threading import *
# import time
# # 任务函数
# def task():
#     time.sleep(3)
#     print('子线程运行，名称为 %s' % current_thread().name)
#
# thread_one = Thread(target=task)  # 创建前台线程，并指定任务函数
# # 创建后台线程，并指定任务函数
# thread_two = Thread(target=task, daemon=True)
#
# from threading import *
# class MyThread(Thread):
#     def __init__(self, num):
#         super().__init__()                # 调用父类的构造方法完成初始化逻辑
#         self.name = '线程' + str(num)    # 设置线程的名称
#     def run(self):                         # 重写run()方法
#         time.sleep(3)
#         message = self.name + '运行'
#         print(message)
#
# for i in range(3):
#     thread_three = MyThread(i)
#
# # thread_one.start()
# # thread_two.start()
# for i in range(3):
#     thread_three = MyThread(i)
#     thread_three.start()


# ===========   12.5.1线程的创建和启动   ===========
# from threading import *
# import time
# def task():
#     time.sleep(2)
#     print('子线程%s运行结束' % current_thread().name)
# for i in range(5):
#     thread = Thread(target=task)
#     thread.start()       # 启动子线程
#     thread.join()        # 阻塞子线程
# print('主线程结束')


# ===========   12.6.2互斥锁   ===========
# from threading import *
# total_ticket = 100  # 总票数
# # 定义任务函数，用于实现卖票行为
# def sale_ticket():
#     global total_ticket
#     while total_ticket > 0:
#         mutex_lock.acquire()  # 上锁，将互斥锁状态改为锁定
#         if total_ticket > 0:
#             total_ticket -= 1
#             print('%s卖出一张票' % current_thread().name)
#         print('剩余票数： %d' % total_ticket)
#         mutex_lock.release()  # 解锁，将互斥锁状态改为非锁定
# if __name__ == '__main__':
#     mutex_lock = Lock()  # 创建互斥锁
#     # 创建线程，表示卖票窗口1
#     thread_one = Thread(target=sale_ticket, name='窗口1')
#     thread_one.start()
#     # 创建线程，表示卖票窗口2
#     thread_two = Thread(target=sale_ticket, name='窗口2')
#     thread_two.start()


# ===========   12.6.3死锁   ===========
# 1. 上锁与解锁次数不匹配
# from threading import *
# def do_work():
#     mutex_lock.acquire()  # 上锁，将互斥锁的状态修改为锁定
#     mutex_lock.acquire()  # 再次上锁
#     mutex_lock.release()  # 解锁，将互斥锁的状态修改为非锁定
# if __name__ == '__main__':
#     mutex_lock = Lock()
#     thread = Thread(target=do_work)
#     thread.start()

# 2.两个线程互相使用对方的互斥锁
# from threading import Thread, Lock
# import time
# class ThreadOne(Thread):
#     def run(self):
#         if lock_a.acquire():             # lock_a上锁
#             print(self.name + '：lock_a上锁')
#             time.sleep(1)
#             if lock_b.acquire():         # lock_b上锁
#                 print(self.name + '：lock_b解锁')
#                 lock_b.release()          # lock_b解锁
#             lock_a.release()              # lock_a解锁
# class ThreadTwo(Thread):
#     def run(self):
#         if lock_b.acquire():            # lock_b上锁
#             print(self.name + '：lock_b上锁')
#             time.sleep(1)
#             if lock_a.acquire():        # lock_a上锁
#                 print(self.name + '：lock_a解锁')
#                 lock_a.release()         # lock_a解锁
#             lock_b.release()             # lock_b解锁
# if __name__ == '__main__':
#     lock_a = Lock()
#     lock_b = Lock()
#     thread_one = ThreadOne(name='线程1')
#     thread_two = ThreadTwo(name='线程2')
#     thread_one.start()
#     thread_two.start()


# 解决两个线程互相使用对方的互斥锁
# from threading import Thread, Lock
# import time
# class ThreadOne(Thread):
#     def run(self):
#         if lock_a.acquire():             # lock_a上锁
#             print(self.name + '：lock_a上锁')
#             time.sleep(1)
#             if lock_b.acquire(timeout=2):    # lock_b上锁
#                 print(self.name + '：lock_b解锁')
#                 lock_b.release()          # lock_b解锁
#             lock_a.release()              # lock_a解锁
# class ThreadTwo(Thread):
#     def run(self):
#         if lock_b.acquire():            # lock_b上锁
#             print(self.name + '：lock_b上锁')
#             time.sleep(1)
#             if lock_a.acquire():        # lock_a上锁
#                 print(self.name + '：lock_a解锁')
#                 lock_a.release()         # lock_a解锁
#             lock_b.release()             # lock_b解锁
# if __name__ == '__main__':
#     lock_a = Lock()
#     lock_b = Lock()
#     thread_one = ThreadOne(name='线程1')
#     thread_two = ThreadTwo(name='线程2')
#     thread_one.start()
#     thread_two.start()


# ===========   12.6.4可重入锁   ===========
# from threading import Thread, RLock
# import time
# num = 0                          # 定义全局变量
# r_lock = RLock()               # 创建可重入锁
# class MyThread(Thread):
#     def run(self):
#         global num
#         time.sleep(1)
#         if r_lock.acquire():  # 将r_lock上锁
#             num = num + 1      # 修改全局变量
#             msg = self.name + '将num改为' + str(num)
#             print(msg)
#             r_lock.acquire()   # 将r_lock再次上锁
#             r_lock.release()   # 将r_lock解锁
#             r_lock.release()   # 将r_lock再次解锁
# if __name__ == '__main__':
#     for i in range(5):         # 创建5个线程
#         t = MyThread()
#         t.start()


# ===========   12.7.2通过Condition类实现线程同步   ===========
# import threading
# from threading import Thread, Condition
# class Account(object):
#     def __init__(self, account_no, balance):
#         self.account_no = account_no       # 账户编号
#         self._balance = balance             # 账户余额
#         self.cond = Condition()             # 创建表示条件变量的对象cond
#         self._flag = False                   # 标记是否已有存款
#     # 取钱操作
#     def draw_money(self, draw_amount):
#         # 上锁，相当于调用cond绑定锁的acquire()方法
#         self.cond.acquire()
#         try:
#             # 判断账户是否没有存款
#             if not self._flag:    # 没有存款
#                 self.cond.wait()  # 将阻塞取钱操作
#             else:
#                 print(threading.current_thread().name +
#                        " 取钱：" + str(draw_amount))
#                 self._balance -= draw_amount
#                 print("账户余额为： " + str(self._balance))
#                 # 将存款标记设为False
#                 self._flag = False
#                 # 唤醒其他线程
#                 self.cond.notify_all()
#         finally:
#             self.cond.release()   # 解锁
#     # 存钱操作
#     def deposit(self, deposit_amount):
#         # 上锁，相当于调用cond绑定锁的acquire()方法
#         self.cond.acquire()
#         try:
#             # 判断账户是否没有存款
#             if self._flag:         # 有存款
#                 self.cond.wait()  # 将阻塞存钱操作
#             else:
#                 print(threading.current_thread().name +
#                        " 存钱：" + str(deposit_amount))
#                 self._balance += deposit_amount
#                 print("账户余额为： " + str(self._balance))
#                 # 将存款的标记设为True
#                 self._flag = True
#                 # 唤醒其他线程
#                 self.cond.notify_all()
#         finally:
#             self.cond.release()  # 解锁
# # 创建一个账户
# acct = Account("123456" , 0)
# for i in range(5):
#     # 创建并启动线程，用于执行取钱的操作
#     threading.Thread(name="取款者", target=acct.draw_money,
#                         args=(500,)).start()
#     # 创建并启动线程，用于执行存钱的操作
#     threading.Thread(name="存款者", target=acct.deposit,
#                         args=(500,)).start()


# ===========   12.7.3通过Queue类实现线程同步   ===========
import threading
from queue import Queue
from threading import Thread, Lock
import time
class MyThread (Thread):
    def __init__(self, threadID, name, q):
        super().__init__()
        self.threadID = threadID
        self.name = name
        self.q = q
    def run(self):
        print(self.name + "开始 ")
        process_data(self.name, self.q)
        print(self.name + "结束 ")
def process_data(threadName, q):
    while not exit_flag:
        queueLock.acquire()
        if not workQueue.empty():
            data = q.get()
            queueLock.release()
            print("%s 取出元素 %s" % (threadName, data))
        else:
            queueLock.release()
        time.sleep(1)
exit_flag = 0   # 线程退出标记
threadList = ["Thread-1", "Thread-2", "Thread-3"]
nameList = ["One", "Two", "Three", "Four", "Five"]
queueLock = Lock()
workQueue = Queue(10)
threads = []
threadID = 1
# 创建新线程
for tName in threadList:
    thread = MyThread(threadID, tName, workQueue)
    thread.start()
    threads.append(thread)
    threadID += 1
# 填充队列
queueLock.acquire()
for word in nameList:
    workQueue.put(word)
    print("%s 存入元素 %s" % (threading.current_thread().name, word))
queueLock.release()
# 等待队列清空
while not workQueue.empty():
    pass
# 通知线程是时候退出
exit_flag = 1
# 等待所有线程完成
for t in threads:
    t.join()
print("主线程结束")
