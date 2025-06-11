# ===========   10.1.1认识错误和异常   ===========
# while True
#     print("语法格式错误")

# radius = 5
# area = 2 * 3.14 * radius              # 错误的公式
# print("圆的面积：", area)


# ===========   10.2.1try-except语句   ===========
# try:
#     for i in 2:
#         print(i)
# except TypeError:         # except后面指定一个异常类
#     print("出错了")

# try:
#     # print(count)
#     demo_list = ["Python", "Java", "C", "C++"]
#     print(demo_list[5])
# except (NameError, IndexError):   # except后面指定多个异常类
#     print("出错了")

# try:
#     print(count)
#     demo_list = ["Python", "Java", "C", "C++"]
#     print(demo_list[5])
# except NameError:       # except后面指定一个异常类
#     print("出错了")
# except IndexError:      # except后面指定另一个异常类
#     print("出错了")

# try:
#     print(count)
#     demo_list = ["Python", "Java", "C", "C++"]
#     print(demo_list[5])
# except Exception:          # except后面指定异常类为Exception
#     print("出错了")

# try:
#     print(count)
#     demo_list = ["Python", "Java", "C", "C++"]
#     print(demo_list[5])
# except:                      # except后面省略异常类
#     print("出错了")


# ===========   10.2.2捕获异常信息   ===========
# try:
#     # print(count)
#     demo_list = ["Python", "Java", "C", "C++"]
#     print(demo_list[5])
# except (NameError, IndexError) as error:   # 使用关键字as获取异常信息
#     print("出错了，原因是", error)


# ===========   10.2.3else子句   ===========
# num = input("请输入每页显示多少条数据:")
# try:
#     page_size = int(num)      # 将num的类型由字符串转换为整型
# except Exception:             # 转换类型时产生异常，使用默认的条数
#     page_size = 20
#     print(f"当前页显示{page_size}条数据")
# else:                           # 转换类型时没有产生异常，使用用户输入的条数
#     print(f"当前页显示{page_size}条数据")


# ===========   10.2.3else子句   ===========
# file = open('异常.txt', 'r')
# try:
#     file.write("人生苦短，我用Python")
# except Exception as error:
#     print("写入文件失败", error)
# finally:
#     file.close()
#     print('文件已关闭')


# ===========   10.3.1raise语句   ===========
# raise IndexError
# raise IndexError()
# raise IndexError('索引下标超出范围')         # 抛出异常及其具体信息
# try:
#     raise IndexError('索引下标超出范围')
# except:
#     raise


# ===========   10.3.2异常的传递   ===========
# def get_width():             # 获取用户输入的正方形边长
#     print("get_width开始执行")
#     num = int(input("请输入除数："))
#     width_len = 10 / num    # 此行代码在除数num为0时执行会产生异常
#     print("get_width执行结束")
#     return width_len
# def calc_area():             # 计算正方形的面积
#     print("calc_area开始执行")
#     width_len = get_width()
#     print("calc_area执行结束")
#     return width_len * width_len
# def show_area():            # 展示正方形的面积
#     try:
#         print("show_area开始执行")
#         area_val = calc_area()
#         print(f"正方形的面积是：{area_val}")
#         print("show_area执行结束")
#     except ZeroDivisionError as e:
#         print(f"捕捉到异常:{e}")
# if __name__ == '__main__':
#     show_area()


# ===========   10.3.3assert断言语句   ===========
# age = 17
# assert age >= 18,"年龄必须大于等于18岁"


# ===========   10.4	自定义异常   ===========
# class CustomError(Exception):
#     pass
# try:
#     pass
#     raise CustomError("出现错误")
# except CustomError as error:
#     print(error)

# class FileTypeError(Exception):
#     def __init__(self, err="仅支持jpg/png/bmp格式"):
#         super().__init__(err)
# file_name = input("请输入上传图片的名称（包含格式）：")
# try:
#     if file_name.split(".")[1] in ["jpg", "png", "bmp"]:
#         print("上传成功")
#     else:
#         raise FileTypeError
# except Exception as error:
#     print(error)


# ===========   10.5	with语句   ===========
# with open('with_sence.txt') as file:
#     for aline in file:
#         print(aline)
