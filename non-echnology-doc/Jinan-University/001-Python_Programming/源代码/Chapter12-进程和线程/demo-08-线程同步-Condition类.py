import threading
from threading import Condition

print('# ===========   12.7.2通过Condition类实现线程同步   ===========')


class Account(object):
    def __init__(self, account_no, balance):
        self.account_no = account_no  # 账户编号
        self._balance = balance  # 账户余额
        self.cond = Condition()  # 创建表示条件变量的对象cond
        self._flag = False  # 标记是否已有存款

    # 取钱操作
    def draw_money(self, draw_amount):
        # 上锁，相当于调用cond绑定锁的acquire()方法
        self.cond.acquire()
        try:
            # 判断账户是否没有存款
            if not self._flag:  # 没有存款
                self.cond.wait()  # 将阻塞取钱操作
            else:
                print(threading.current_thread().name + " 取钱：" + str(draw_amount))
                self._balance -= draw_amount
                print("账户余额为： " + str(self._balance))
                # 将存款标记设为False
                self._flag = False
                # 唤醒其他线程
                self.cond.notify_all()
        finally:
            self.cond.release()  # 解锁

    # 存钱操作
    def deposit(self, deposit_amount):
        # 上锁，相当于调用cond绑定锁的acquire()方法
        self.cond.acquire()
        try:
            # 判断账户是否没有存款
            if self._flag:  # 有存款
                self.cond.wait()  # 将阻塞存钱操作
            else:
                print(threading.current_thread().name + " 存钱：" + str(deposit_amount))
                self._balance += deposit_amount
                print("账户余额为： " + str(self._balance))
                # 将存款的标记设为True
                self._flag = True
                # 唤醒其他线程
                self.cond.notify_all()
        finally:
            self.cond.release()  # 解锁


# 创建一个账户
acct = Account("123456", 0)
for i in range(5):
    # 创建并启动线程，用于执行取钱的操作
    threading.Thread(name="取款者", target=acct.draw_money, args=(500,)).start()
    # 创建并启动线程，用于执行存钱的操作
    threading.Thread(name="存款者", target=acct.deposit, args=(500,)).start()
