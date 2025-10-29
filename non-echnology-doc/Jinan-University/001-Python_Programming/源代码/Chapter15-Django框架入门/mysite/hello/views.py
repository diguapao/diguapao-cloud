from django.shortcuts import render
from django import http


def index(request):
    return http.HttpResponse('hello world')
