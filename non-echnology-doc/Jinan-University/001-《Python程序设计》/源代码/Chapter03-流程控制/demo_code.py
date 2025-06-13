print('===========   3.1.1	if语句   ===========')
age = 5
if age >= 3:  # 判断变量age的值是否大于或等于3
    print("可以上幼儿园了")

print('===========   3.1.2	if-else语句   ===========')
u_name = input("请输入用户名：")
pwd = input("请输入密码：")
if u_name == "admin" and pwd == "123":
    print("登录成功！即将进入主界面。")
else:
    print("您输入的用户名或密码错误，请重新输入。")

print('===========   3.1.3	if-elif-else语句   ===========')
score = int(input("请输入您的会员积分："))
if score == 0:
    print("注册会员")
elif 0 < score <= 2000:
    print("铜牌会员")
elif 2000 < score <= 10000:
    print("银牌会员")
elif 10000 < score <= 30000:
    print("金牌会员")
else:
    print('钻石会员')

print('===========   3.2.1	条件嵌套的格式   ===========')
year = int(input("请输入年份："))
month = int(input("请输入月份:"))
if month in [1, 3, 5, 7, 8, 10, 12]:
    print(f"{year}年{month}月有31天")
elif month in [4, 6, 9, 11]:
    print(f"{year}年{month}月有30天")
elif month == 2:
    if year % 400 == 0 or (year % 4 == 0 and year % 100 != 0):
        print(f"{year}年{month}月有29天")
    else:
        print(f"{year}年{month}月有28天")

print('===========   3.3.1	for语句   ===========')
words = "学海无涯"
for c in words:
    print(c)

for i in range(3):
    print("Hello")

print('===========   3.3.4	while语句   ===========')
i = 1
result = 1
while i <= 10:
    result *= i
    i += 1
print(result)

print(' ===========   3.4.1	while循环嵌套   ===========')
i = 1
while i <= 5:
    j = 1
    while j <= i:
        print("* ", end=' ')
        j += 1
    print(end="\n")
    i += 1

print('===========   3.4.2	for循环嵌套   ===========')
for i in range(1, 6):
    for j in range(i):
        print("*", end=' ')
    print()

print('===========   3.5.1	break语句   ===========')
words = "千里之行始于足下"
for word in words:
    print("--------")
    if word == "行":
        break
    print(word)

i = 0
max = 5
while i < 10:
    i += 1
    print("--------")
    if i == max:
        break
    print(i)

print('===========   3.5.2	continue语句   ===========')
words = "千里之行始于足下"
for word in words:
    print("--------")
    if word == "行":
        continue
    print(word)
