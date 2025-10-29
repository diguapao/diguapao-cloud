# 根据价格区间筛选商品价格
price_li = [399, 4369, 539, 288, 109, 749, 235, 190, 99, 1000]
section_li = []
max_section = int(input("请输入最大价格:"))
min_section = int(input("请输入最小价格:"))
for i in price_li:                    # 遍历列表，从该列表中取出每个商品价格
    if min_section <= i <= max_section:  # 判断商品价格是否位于价格区间内
        section_li.append(i)
# 根据排序方式排列商品价格
print("1.价格降序排序")
print("2.价格升序排序")
choice_num = int(input("请选择排序方式:"))
if choice_num == 1:
    section_li.sort(reverse=True)  # 按照降序方式对列表进行排序
else:
    section_li.sort()                 # 按照升序方式对列表进行排序
print(section_li)
