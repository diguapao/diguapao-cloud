"""
URL configuration for chapter16 project.

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/4.2/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import path, include

# from app02 import views
# app02_extra_patterns = [
#     path('test02/', views.index),
# ]

urlpatterns = [
    path('admin/', admin.site.urls),
    path('app01/', include('app01.urls')),
    path('app02/', include('app02.urls')),  # 引入app02应用的URLconf

    # path('app02/', include(app02_extra_patterns)),  # 引入URL模式列表
    path('app03/', include('app03.urls')),
]


