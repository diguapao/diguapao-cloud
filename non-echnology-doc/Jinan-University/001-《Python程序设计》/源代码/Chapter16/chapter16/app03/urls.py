from django.urls import path
from app03 import views
urlpatterns = [
    path('', views.index),
    path('about/', views.MyView.as_view()),
    path('paging/', views.paging),
]
