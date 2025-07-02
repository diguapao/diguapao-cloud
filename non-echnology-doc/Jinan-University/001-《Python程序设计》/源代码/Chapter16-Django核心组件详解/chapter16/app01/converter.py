from django.urls import register_converter
class MyConverter:
    regex = '1[3-9]\d{9}'           	# 匹配规则
    # 将匹配到的字符串转换成要传递到视图中的类型
    def to_python(self, value):
        return value
    # 将Python数据类型转换为URL模式中使用的字符串
    def to_url(self, value):
        return value
# 注册自定义路由转换器，并指定该转换器的名称
register_converter(MyConverter, 'mobile')
