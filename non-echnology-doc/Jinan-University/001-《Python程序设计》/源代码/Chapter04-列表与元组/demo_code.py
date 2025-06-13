print('===========   4.1.1列表的创建方式   ===========')
list_one = []  # 空列表
list_two = ['p', 'y', 't', 'h', 'o', 'n']  # 列表中元素类型均为字符串类型
list_three = [1, 'a', '&', 2.3]  # 列表中元素类型不同

# list_one = list(1)  # 整型数据不是可迭代对象，因此列表创建失败
list_two = list('python')  # 字符串是可迭代对象
list_three = list([1, 'python'])  # 列表是可迭代对象

print(list_one)
print(list_two)
print(list_three)

print('===========   4.1.2访问列表元素   ===========')
list_demo01 = ["Java", "C#", "Python", "PHP"]
print(list_demo01[2])  # 访问列表中索引为2的元素
print(list_demo01[-1])  # 访问列表中索引为-1的元素

li_one = ["自", "古", "风", "云", "多", "变", "幻",
          "不", "以", "成", "败", "论", "英", "雄"]
print(li_one[2:10])  # 获取列表中索引为2至索引为10之前的元素
print(li_one[:10])  # 获取列表中索引为0至索引为10之前的元素
print(li_one[2:])  # 获取列表中索引为2至末尾的元素
print(li_one[:])  # 获取列表中的所有元素
print(li_one[2:10:2])  # 获取列表中索引为2至索引为10之前，且步长为2的元素

print('===========   4.2.1列表的遍历   ===========')
list_one = ['小明', '小红', '小花', '小丽']
print("今日促销通知！！！")
for i in list_one:  # 通过for语句遍历列表
    print(f"您好，{i}！今日商店大促销，赶快来抢购吧！")

print('===========   4.2.2列表的排序   ===========')
li_one = [6, 2, 5, 3]
li_two = [7, 3, 5, 4]
li_three = ['Python', 'Java', 'PHP']
li_one.sort()  # 升序排列列表中的元素
li_two.sort(reverse=True)  # 降序排列列表中的元素
li_three.sort(key=len)  # 升序排列，排序的规则是列表中各字符串的长度
print(li_one)
print(li_two)
print(li_three)

li_one = [4, 3, 2, 1]
li_two = sorted(li_one)  # 升序排列列表中的元素
print(li_one)
print(li_two)

li_one = ['a', 'b', 'c', 'd']
li_one.reverse()
print(li_one)

print('===========   4.3.1添加列表元素   ===========')
list_one = [1, 2, 3, 4]
list_one.append(5)  # 在列表末尾添加元素5
print(list_one)

list_str = ['a', 'b', 'c']
list_num = [1, 2, 3]
list_str.extend(list_num)  # 在列表末尾添加另一列表的所有元素
print(list_num)
print(list_str)

names = ['baby', 'Lucy', 'Alise']
names.insert(2, 'Peter')  # 在索引为2的位置插入元素'Peter'
print(names)

print('===========   4.3.2删除列表元素   ===========')
names = ['小明', '小红', '小华', '小花', '小李', '小张']
del names[0]  # 删除索引为1的元素
print(names)
del names[1:3]  # 删除索引为1至索引为3之前的元素
print(names)

chars = ['h', 'e', 'l', 'l', 'e']
chars.remove('e')  # 删除第一个匹配的元素'e'
print(chars)

numbers = [1, 2, 3, 4, 5]
numbers.pop()  # 删除列表中的最后一个元素
print(numbers)
numbers.pop(1)  # 删除列表中索引为1的元素
print(numbers)

print('===========   4.3.3修改列表元素   ===========')
names = ['小明', '小红', '小华']
names[0] = '小花'  # 将索引为0的元素由'小明'修改为'小花'
print(names)

print('===========   4.4.1嵌套列表的创建与访问   ===========')
name_li = [['小周', '小吴'], ['小郑'], ['小王', '小赵']]
print(name_li[0][0])  # 访问嵌套列表中第一个列表第一个元素

name_li = [['小周', '小吴'], ['小郑'], ['小王', '小赵']]
name_li[1].append('小李')  # 向嵌套的第二个列表中添加元素'小李'
print(name_li)

print('===========   4.5.1元组的创建方式   ===========')
tu_one = ()  # 空元组
tu_two = (1,)  # 元组中只有一个元素
tu_three = ('t', 'u', 'p', 'l', 'e')  # 元组中元素类型均为字符串类型
tu_four = (0.3, 1, 'python', True)  # 元组中元素类型不同
print(tu_one)
print(tu_two)
print(tu_three)
print(tu_four)

tuple_null = tuple()
print(tuple_null)
tuple_str = tuple('abc')
print(tuple_str)
tuple_list = tuple([1, 2, 3])
print(tuple_list)

print('===========   4.5.2访问元组元素   ===========')
tuple_demo = ('hello', 100, 'Python')
print(tuple_demo[0])  # 访问索引为0的元素
print(tuple_demo[1])  # 访问索引为1的元素
print(tuple_demo[2])  # 访问索引为2的元素

exam_tuple = ('h', 'e', 'l', 'l', 'o')
print(exam_tuple[1:4])  # 访问索引为1至索引为4之前的元素

exam_tuple = ('hello', 100, 'Python')
# exam_tuple[0] = 'hi'  # 将索引为0的元素修改为'hi'，这里会报错，因为元组不可变
print(exam_tuple)

tuple_char = ('a', 'b', ['1', '2'])
tuple_char[2][0] = 'c'
tuple_char[2][1] = 'd'
print(tuple_char)
