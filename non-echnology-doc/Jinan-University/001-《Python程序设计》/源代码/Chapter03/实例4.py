# 表示是否有车票，True代表有车票
is_ticket = True
# 表示是否携带刀具，True代表携带刀具
is_knife = True
if is_ticket == True:  # 有车票
    print("有车票，可以进站")
    if is_knife == False:  # 没有携带刀具
        print("通过安检")
        print("终于可以见到Ta了，美滋滋~~~")
    else:  # 携带刀具
        print("没有通过安检")
        print("携带了刀具，等待工作人员处理...")
else:  # 没有车票
    print("没有车票，不能进站")
    print("下次见了，一票难求啊~~~~(>_<)~~~~")
