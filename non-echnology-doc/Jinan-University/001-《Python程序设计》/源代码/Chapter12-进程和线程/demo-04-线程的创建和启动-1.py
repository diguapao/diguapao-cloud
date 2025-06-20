import time
from threading import *

print('# ===========   12.5.1线程的创建和启动   ===========')


# 任务函数
def task():
    time.sleep(3)
    print('子线程运行，名称为 %s' % current_thread().name)


# 创建前台线程，并指定任务函数
thread_one = Thread(target=task)
# 创建后台线程，并指定任务函数
thread_two = Thread(target=task, daemon=True)


class MyThread(Thread):
    def __init__(self, num):
        super().__init__()  # 调用父类的构造方法完成初始化逻辑
        self.name = '线程' + str(num)  # 设置线程的名称

    def run(self):  # 重写run()方法
        time.sleep(3)
        message = "\n" + self.name + '运行'
        print(message)


# 启动线程
# thread_one.start()
# thread_two.start()

for i in range(3):
    thread_three = MyThread(i)
    print("线程" + str(i) + "启动")
    thread_three.start()
