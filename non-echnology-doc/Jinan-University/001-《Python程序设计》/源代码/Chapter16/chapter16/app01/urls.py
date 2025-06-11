from django.urls import path
from app01 import views, converter
urlpatterns = [
    path('mobile/<mobile:phone_num/>', views.show_mobile)
]
