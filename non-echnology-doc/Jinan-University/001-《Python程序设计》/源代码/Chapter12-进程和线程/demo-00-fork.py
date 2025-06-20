print('# ===========   12.2.1通过fork()函数创建进程（Windows平台不可用）   ===========')
import os
import time

# 创建子进程（Windows平台不可用）
value = os.fork()
if value == 0:  # 子进程执行if子句
    print('---子进程---')
    time.sleep(2)
else:  # 父进程执行else子句
    print('---父进程---')
    time.sleep(2)

import os
import time

print('---第一次调用fork()---')
value = os.fork()  # 创建子进程，此时进程的总数量为2（Windows平台不可用）
if value == 0:  # 子进程执行if子句
    print('---进程1---')
    time.sleep(2)
else:  # 父进程执行else子句
    print('---进程2---')
    time.sleep(2)
print('---第二次调用fork()---')
value = os.fork()  # 创建子进程，此时进程的总数量为4（Windows平台不可用）
if value == 0:  # 子进程执行if子句
    print('---进程3---')
    time.sleep(2)
else:  # 父进程执行else子句
    print('---进程4---')
    time.sleep(2)

import os

process = os.fork()  # 创建子进程（Windows平台不可用）
if process == 0:
    print('我是子进程%d，父进程是%d' % (os.getpid(), os.getppid()))
else:
    print('我是父进程%d, 子进程是%d' % (os.getpid(), process))
