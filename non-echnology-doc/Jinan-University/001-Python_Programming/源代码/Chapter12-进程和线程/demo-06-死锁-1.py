from threading import *

print('# ===========   12.6.3死锁   ===========')


# 1.上锁与解锁次数不匹配导致的死锁
# Lock 是一种 不可重入锁（Non-reentrant Lock）
# 同一个线程 只能成功调用一次 .acquire()
# 如果在同一个线程中多次调用 .acquire() 而没有释放（.release()），就会导致该线程自己卡住 —— 即死锁
def do_work():
    mutex_lock.acquire()  # 上锁，将互斥锁的状态修改为锁定
    mutex_lock.acquire()  # 再次上锁
    mutex_lock.release()  # 解锁，将互斥锁的状态修改为非锁定


if __name__ == '__main__':
    mutex_lock = Lock()
    thread = Thread(target=do_work)
    thread.start()
