from .models import RegisterUser
from django.shortcuts import render,redirect

def index(request):
    login_msg = "恭喜！登录成功"
    return render(request, 'index.html',{'login_msg':login_msg})

def login(request):
    if request.method == 'GET':
        return render(request, 'login.html')
    if request.method == "POST":
        # 如果是post请求方法方式获取用户输入的账号密码
        userEmail = request.POST.get('username')
        userPassword = request.POST.get('password')
        # 
        # 查询数据库中的账号密码
        try:
            user = RegisterUser.objects.get(reg_mail=userEmail)
            if userPassword == user.reg_pwd:
                return redirect('/index/')
            else:
                error_msg = '密码错误'
                return render(request, 'login.html', {'error_msg': error_msg})
        except:
            error_msg = '用户名不存在'
            return render(request, 'login.html', {'error_msg': error_msg})


def register(request):
    if request.method == 'POST':
        userEmail = request.POST.get('userEmail')
        userPassword = request.POST.get('userPassword')
        userRePzassword = request.POST.get('userRePassword')
        # 如果注册的用户名已存在，进行提示
        try:
            user = RegisterUser.objects.get(reg_mail=userEmail)
            if user:
                msg = '用户名已存在'
                return render(request, 'register.html', {'msg': msg})
        except:
            # 判断两次输入的密码是否一致
            if userPassword != userRePzassword:
                error_msg = '密码不一致'
                return render(request, 'register.html', {'error_msg': error_msg})
            else:
                # 将注册信息写入register表中
                register = RegisterUser()
                register.reg_mail = userEmail
                register.reg_pwd = userPassword
                register.save()

                return redirect('/login/')
    else:
        return render(request, 'register.html')
