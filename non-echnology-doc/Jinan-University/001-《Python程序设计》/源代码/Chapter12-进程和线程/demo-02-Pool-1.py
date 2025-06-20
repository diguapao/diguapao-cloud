import os
import time
from multiprocessing import Pool

print('# ===========   12.2.3通过Pool类批量创建进程   ===========')


# 表示任务的函数
def work(num):
    print('进程%s：执行任务%d' % (os.getpid(), num))
    time.sleep(2)


# 如下方式，定义在全局作用域中，运行时可能导致错误，尤其在 Windows 上（可能出现“无限创建进程”的问题）。
# pool = Pool(processes=5)

if __name__ == '__main__':
    pool = Pool(3)  # 创建进程池，指定最大进程数量为3
    print("非阻塞式地给进程池添加任务")
    for i in range(9):
        pool.apply_async(work, (i,))  # 非阻塞式地给进程池添加任务
    time.sleep(3)
    # 主进程执行结束，子进程也会结束
    print('主进程执行结束')
