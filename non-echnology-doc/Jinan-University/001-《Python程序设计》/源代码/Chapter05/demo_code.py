# ===========   5.1.1字典的创建方式   ===========
# {'name': '小明', 'age': 21, 'address': '北京'}
# {}
# dict(name='小明', age=21, address='北京')


# ===========   5.1.2通过键访问字典   ===========
# color_dict = {'purple': '紫色', 'green': '绿色', 'black': '黑色'}
# print(color_dict['purple'])          # 获取键为purple对应的值
# print(color_dict['green'])           # 获取键为green对应的值
# print(color_dict['black'])           # 获取键为black对应的值

# print(color_dict['red'])

# if 'red' in color_dict:
#     print(color_dict['red'])
# else:
#     print('键不存在')


# ===========   5.2.1字典元素的添加和修改   ===========
# add_dict = {'stu1': '小明'}
# add_dict.update(stu2='小刚')         	# 使用update()方法添加元素
# add_dict['stu3'] = '小兰'            	# 通过指定键添加元素
# print(add_dict)

# modify_dict = {'stu1': '小明', 'stu2': '小刚', 'stu3': '小兰'}
# modify_dict.update(stu2='小强')   	# 使用update()方法修改元素
# modify_dict['stu3'] = '小婷'       	# 通过指定键修改元素
# print(modify_dict)


# ===========   5.2.2字典元素的删除   ===========
# per_info = {'001': '张三', '002': '李四', '003': '王五', '004': '赵六', }
# print(per_info.pop('001'))            # 使用pop()删除键为001的元素
# print(per_info)

# per_info = {'001': '张三', '002': '李四', '003': '王五', '004': '赵六'}
# print(per_info.popitem())             # 使用popitem()方法随机删除元素
# print(per_info)

# per_info = {'001': '张三', '002': '李四', '003': '王五', '004': '赵六', }
# per_info.clear()                        # 使用clear()方法清空字典中的元素
# print(per_info)


# ===========   5.2.3字典元素的查询   ===========
per_info = {'001': '张三', '002': '李四', '003': '王五'}
# print(per_info.items())
# per_info = {'001': '张三', '002': '李四', '003': '王五'}
# for i in per_info.items():
#     print(i)

# print(per_info.keys())
# for i in per_info.keys():
#     print(i)

# print(per_info.values())
# per_info = {'001': '张三', '002': '李四', '003': '王五'}
# for i in per_info.values():
#     print(i)


# ===========   5.3	集合的创建方式   ===========
# set_one = {'Python'}                  # 创建包含一个元素的集合
# set_two = {0.3, 1, 'Python'}        # 创建包含多个元素的集合
# set_three = {0.3, 1, 1, 'Python'}  # 创建包含多个元素的集合，有重复元素
# print(set_one)
# print(set_two)
# print(set_three)

# set_one = set([1, 1, 2, 3])         # 根据列表创建集合
# set_two = set((2, 3, 4))            # 根据元组创建集合
# set_three = set('Hello')            # 根据字符串创建集合
# set_four = set()                      # 创建空集合
# print(set_one)
# print(set_two)
# print(set_three)
# print(set_four)


# ===========   5.4.1集合元素的添加、删除和清空   ===========
# demo_set = set()
# demo_set.add('py')         # 使用add()方法向集合中添加一个元素'py'
# demo_set.update('thon')   # 使用update()方法向集合中添加多个元素
# demo_set.add('py')         # 使用add()方法向集合中再次添加'py'
# print(demo_set)

# remove_set = {'red', 'green', 'black'}
# remove_set.remove('red')             # 删除指定元素，该元素在集合中
# print(remove_set)
# remove_set.remove('blue')            # 删除指定元素，该元素不在集合中
# print(remove_set)

# discard_set = {'python', 'php', 'java'}
# discard_set.discard('java')         # 删除指定元素，该元素在集合中
# discard_set.discard('ios')          # 删除指定元素，该元素不在集合中
# print(discard_set)

# pop_set = {'green', 'blue', 'white'}
# pop_set.pop()       # 随机删除一个元素
# print(pop_set)

# clear_set = {'red', 'green', 'black'}
# clear_set.clear()    # 清空集合中的所有元素
# print(clear_set)


# ===========   5.4.2集合类型的操作符   ===========
set_a = {'a', 'c'}
set_b = {'b', 'c'}
# print(set_a | set_b)           # 使用|操作符对两个集合进行联合操作
# print(set_a & set_b)           # 使用&操作符对两个集合进行交集操作
# print(set_a - set_b)          # 使用-操作符获取只属于集合set_a的元素
# print(set_b - set_a)          # 使用-操作符获取只属于集合set_b的元素
# print(set_a ^ set_b)       # 使用^操作符获取只属于set_a和只属于set_b的元素


