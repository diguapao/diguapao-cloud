import pymysql

def print_menu():
    print("----功能选择----")
    print("1.注册")
    print("2.登录")

# 用户注册
def mysql_registered(conn, cur):
    # 1.获取有效用户名
    sql_select = 'select * from py_users where uname=%s'
    uname = input('用户名：')
    while True:
        # 执行SQL语句
        params = [uname]
        result = cur.execute(sql_select, params)
        if result == 1:
            print('用户名已存在，请重新输入')
            uname = input('用户名：')
        else:
            break
    # 2.获取密码
    upwd = input('密  码：')
    # 3.在数据库插入用户名和密码
    sql_insert = 'insert into py_users(uname,upwd) values(%s,%s)'
    params = [uname, upwd]
    result = cur.execute(sql_insert, params)
    conn.commit()
    # 4.提示注册结果
    if result == 1:
        print('注册成功')
    else:
        print('注册失败')
    cur.close()

def mysql_login(conn, cur):
    result = 0
    while not result:
        uname = input('用户名：')
        sql = 'select upwd from py_users where uname=%s'
        params = [uname]
        result = cur.execute(sql, params)
        if result == 1:  # 用户名存在
            mysql_upwd = cur.fetchone()
            while True:
                upwd = input('密码：')
                if upwd == mysql_upwd[0]:
                    print('登录成功')
                    break
                else:
                    print('密码错误，请重新输入')
        else:  # 用户名不存在
            print('用户名不存在')
    # 关闭连接
    cur.close()

def main():
    # 1.打印功能菜单
    print_menu()
    # 2.选择功能
    sel = input("注册（1）or 登录（2）？")
    # 连接数据库
    conn = pymysql.connect(host='localhost', port=3306,
                               database='python', user='root',
                               password='123456', charset='utf8')
    cur = conn.cursor()
    if sel == '1':
        mysql_registered(conn, cur)
    elif sel == '2':
        mysql_login(conn, cur)
if __name__ == '__main__':
    main()
