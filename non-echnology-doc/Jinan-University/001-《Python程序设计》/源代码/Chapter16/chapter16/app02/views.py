from django import http
from django.shortcuts import reverse

def index(request):
    return http.HttpResponse('Route distribution successful!')

def blog(request, blog_id):
    return http.HttpResponse(f'参数blog_id值为：{blog_id}')

def get_url(request):
    return http.HttpResponse(f"反向解析的URL为：{reverse('url')}")
