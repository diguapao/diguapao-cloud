import os
import time
from multiprocessing import Pool

print('# ===========   12.2.3通过Pool类批量创建进程   ===========')


def work(num):
    print('进程%s： 执行任务%d' % (os.getpid(), num))
    time.sleep(2)


if __name__ == '__main__':
    pool = Pool(processes=5)  # 创建进程池，指定最大进程数量为5
    print("阻塞式地给进程池添加任务")
    for i in range(9):
        pool.apply(work, (i,))  # 阻塞式地给进程池添加任务
    time.sleep(3)
    print('主进程执行结束')
