import time
import threading
from queue import Queue
from threading import Thread, Lock

print('# ===========   12.7.3通过Queue类实现线程同步   ===========')


class MyThread(Thread):
    def __init__(self, thread_id, name, q):
        super().__init__()
        self.threadId = thread_id
        self.name = name
        self.q = q

    def run(self):
        print(self.name + "开始 ")
        process_data(self.name, self.q)
        print(self.name + "结束 ")


def process_data(thread_name, q):
    while not exit_flag:
        queueLock.acquire()
        if not workQueue.empty():
            data = q.get()
            queueLock.release()
            print("%s 取出元素 %s" % (thread_name, data))
        else:
            queueLock.release()
        time.sleep(1)


exit_flag = 0  # 线程退出标记
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
