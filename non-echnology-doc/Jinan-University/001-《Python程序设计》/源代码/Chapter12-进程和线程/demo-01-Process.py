import os
import time

print('# ===========   12.2.2通过Process类创建进程   ===========')
from multiprocessing import Process


# 进程要执行的目标函数
def do_task():
    print('子进程 %s' % os.getpid())  # 获取子进程的进程ID


# 创建子进程，指定该进程要执行的目标函数为do_task
process = Process(target=do_task)
# 运行子进程
process.run()

from multiprocessing import Process


# 定义一个继承自Process的类
class MyProcess(Process):
    # 重写__init__()方法，定义子进程的初始化逻辑
    def __init__(self, interval):
        super().__init__()  # 完成父类Process的初始化逻辑
        self.interval = interval  # 间隔秒数

    # 重写run()方法，定义子进程要执行的任务逻辑
    def run(self):
        time_start = time.time()  # 获取任务开始执行的时间戳
        time.sleep(self.interval)
        time_stop = time.time()  # 获取任务结束执行的时间戳
        print("子进程%s执行结束，耗时%0.2f秒" % (os.getpid(), time_stop - time_start))


if __name__ == '__main__':
    my_process = MyProcess(5)  # 根据MyProcess类创建子进程
    my_process.start()  # 启动进程
