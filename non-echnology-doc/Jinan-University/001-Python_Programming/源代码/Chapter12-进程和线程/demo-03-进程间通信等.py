from multiprocessing import Queue
from multiprocessing import Process

print('# ===========   12.3	进程间通信   ===========')


def write_task(queue):
    count = 10  # 定义局部变量
    print("将局部变量插入到队列中")
    queue.put(count, block=False)  # 将局部变量插入到队列中


def read_task(queue):
    print("从队列中读取数据")
    print(queue.get(block=False))  # 从队列中读取数据


if __name__ == '__main__':
    queue = Queue()  # 创建队列，队列的长度没有限制
    # 创建两个进程分别执行任务函数
    process_one = Process(target=write_task, args=(queue,))
    process_another = Process(target=read_task, args=(queue,))
    # 启动进程
    process_one.start()
    process_another.start()
