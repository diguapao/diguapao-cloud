"""ttsx URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/2.2/topics/http/urls/
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
from django.urls import path
from goods.views import index,detail,goods
from cart.views import submit_order, submit_success, add_cart, show_cart,place_order,remove_cart
urlpatterns = [
    path('admin/', admin.site.urls),
    path('index/', index),
    path('detail/', detail),
    path('goods/', goods),
    path('cart/add_cart/', add_cart),
    path('cart/show_cart/', show_cart),
    path('cart/place_order/', place_order),
    path('cart/submit_order/', submit_order),
    path('cart/remove_cart/', remove_cart),
    path('cart/submit_success/', submit_success),
]
