from django.template.loader import get_template
from django.http import HttpResponse
# def index(request):
#     temp_obj = get_template("index.html")           # 加载模板
#     response = temp_obj.render(request=request)    # 渲染模板
#     return HttpResponse(response)

def index(request):
    num = 100
    num_dict = {'a': 1, 'b': 2, 'c': 3}
    num_list = [1, 2, 3, 4, 5]
    temp_obj = get_template("index.html")          # 加载模板
    # 渲染模板，指定上下文数据
    response = temp_obj.render(request=request,
                 context={'num': num, 'num_dict': num_dict,
                            'num_list': num_list})
    return HttpResponse(response)


# import time
# def curr_time(request):
#     current_time = time.time()
#     local_time = time.localtime(current_time)
#     now = time.strftime("%Y-%m-%d %H:%M:%S", local_time)
#     response = "<html><body>当前时间：%s</body></html>" % now
#     return HttpResponse(response)


import time
# def curr_time(request):
#     # 获取当前时间
#     current_time = time.time()
#     local_time = time.localtime(current_time)
#     now = time.strftime("%Y-%m-%d %H:%M:%S", local_time)
#     context = {'now': now}                 # 上下文数据
#     temp_obj = get_template("time.html")
#     # 渲染模板，指定上下文数据
#     response = temp_obj.render(request=request, context=context)
#     return HttpResponse(response)


from django.shortcuts import render
def curr_time(request):
    # 获取当前时间
    current_time = time.time()
    local_time = time.localtime(current_time)
    now = time.strftime("%Y-%m-%d %H:%M:%S", local_time)
    context = {'now': now}
    # 渲染模板，指定模板名称，以及填充模板时使用的上下文数据
    return render(request, "time.html", context)


from django.views import View
class MyView(View):
    def get(self, request):
        return HttpResponse('GET result')
	def post(self,request):
		return HttpResponse('POST result')


from django.core.paginator import Paginator
def paging(request):
    # 生成1到30的数字，将其作为分页的数据源
    data = [i for i in range(1, 31)]
    # 每页显示的数据量
    per_page = 10
    # 创建Paginator对象
    paginator = Paginator(data, per_page)
    # 获取当前请求的页数，默认为第一页
    page_number = request.GET.get('page', 1)
    # 获取当前页的数据
    page_obj = paginator.get_page(page_number)
    # 将当前页数据传递给模板进行渲染
    return render(request, 'paging.html', {'page_obj': page_obj})
