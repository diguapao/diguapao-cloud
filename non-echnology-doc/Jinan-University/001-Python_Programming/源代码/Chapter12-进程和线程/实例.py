from  queue import Queue
import threading
import time

queue = Queue()                            # 创建队列对象

class Producer(threading.Thread):      # 代表生产者的线程
    def run(self):
        global queue
        count = 0                           # 初始的产品数量
        while True:
            for i in range(100):
                if queue.qsize() > 1000: # 若队列的大小比1000大，不做任何操作
                     pass
                else:
                     count += 1
                     message = '生成产品' + str(count)
                     queue.put(message)    # 把新生产的产品放到队列
                     print(message)
            time.sleep(1)

class Consumer(threading.Thread):       # 代表消费者的线程
    def run(self):
        global queue
        while True:
            for i in range(3):
                if queue.qsize() < 100:   # 若队列的大小比100小，不做任何操作
                    pass
                else:
                    # 从队列中销售产品
                    message = self.name + '消费了 ' + queue.get()
                    print(message)
            time.sleep(1)

if __name__ == '__main__':
    for i in range(500):
        queue.put('初始产品' + str(i))    # 往队列中放入初始产品500个
    for i in range(2):
        producer = Producer()
        producer.start()
    for i in range(5):
        consumer = Consumer()
        consumer.start()
