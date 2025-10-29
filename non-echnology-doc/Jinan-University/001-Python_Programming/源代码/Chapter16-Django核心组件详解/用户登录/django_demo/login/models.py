from django.db import models
class RegisterUser(models.Model):
    # 注册邮箱 blank=false表示不能为空
    reg_mail = models.CharField(max_length=100, blank=False)
    # 注册密码
    reg_pwd = models.CharField(max_length=100, blank=False)
