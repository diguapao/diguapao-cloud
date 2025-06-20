import os
import time
from multiprocessing import Pool

print('# ===========   12.2.3通过Pool类批量创建进程   ===========')


# 表示任务的函数
def work(num):
    print('进程%s：执行任务%d' % (os.getpid(), num))
    time.sleep(2)


if __name__ == '__main__':
    pool = Pool(processes=5)  # 创建进程池，指定最大进程数量为5
    print("非阻塞式地给进程池添加任务")
    for i in range(9):
        pool.apply_async(work, (i,))  # 非阻塞式地给进程池添加任务
    time.sleep(3)
    pool.close()  # 关闭进程池
    pool.join()  # 阻塞主进程
    # 主进程执行结束，子进程也已经全部执行结束，因为这里调用了 pool.join()
    print('主进程执行结束')
