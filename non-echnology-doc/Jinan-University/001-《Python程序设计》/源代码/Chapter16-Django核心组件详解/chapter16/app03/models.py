from django.db import models
class BookInfo(models.Model):
    name = models.CharField(max_length=20, verbose_name="名称")
    pub_date = models.DateField(verbose_name="发布日期")
    read_count = models.IntegerField(default=0, verbose_name="阅读量")
    comment_count = models.IntegerField(default=0, verbose_name="评论量")
    is_delete = models.BooleanField(default=False, verbose_name="逻辑删除")
    def __str__(self):
        return self.name

class Country(models.Model):
    country_code = models.CharField(max_length=20)
    country_name = models.CharField(max_length=50)
class City(models.Model):
    city_name = models.CharField(max_length=20)
    city_area = models.IntegerField(default=0)
    city_nation = models.ForeignKey(Country, on_delete=models.CASCADE)

class President(models.Model):
    president_name = models.CharField(max_length=20)
    president_gender = models. CharField(max_length=10)
    president_nation = models.OneToOneField(Country)
class Teachers(models.Model):
    name = models.CharField(max_length=10)
class Students(models.Model):
    name = models.CharField(max_length=10)
    classes = models.ManyToManyField(Teachers)
