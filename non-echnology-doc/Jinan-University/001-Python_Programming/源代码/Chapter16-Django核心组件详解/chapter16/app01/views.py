from django.http import HttpResponse


def show_mobile(request, phone_num):
    return HttpResponse(f'手机号码为：{phone_num}')
