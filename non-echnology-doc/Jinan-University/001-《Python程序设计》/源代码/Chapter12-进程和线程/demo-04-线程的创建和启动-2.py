import time
from threading import *

print('# ===========   12.5.1线程的创建和启动   ===========')


def task():
    time.sleep(2)
    print('子线程%s运行结束' % current_thread().name)


for i in range(5):
    thread = Thread(target=task)
    thread.start()  # 启动子线程
    thread.join()  # 阻塞子线程

print('主线程结束')
