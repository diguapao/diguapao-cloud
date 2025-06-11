from django.urls import path
from app02 import views
urlpatterns = [
    path('test/', views.index),
    path('blog/', views.blog, {'blog_id':3}),
    path('url-reverse/', views.get_url, name='url'),
]
