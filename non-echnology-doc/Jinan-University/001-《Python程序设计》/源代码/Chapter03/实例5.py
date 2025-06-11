# 接收物品重量和地区编号
weight = float(input("请输入物品重量(kg)："))
print('编号01：华东地区 编号02：华南地区 编号03：华北地区')
place = input("请输入地区编号：")
# 判断物品重量是否超过首重
if weight <= 2:
    # 处理没超过首重的情况
    if place == '01':
        print('快递费用为13元')
    elif place == '02':
        print('快递费用为12元')
    elif place == '03':
        print('快递费用为14元')
else:
    # 处理超过首重的情况
    excess_weight = weight - 2   # 计算续重
    if place == '01':
        money = excess_weight * 3 + 13
        print('快递费用为%.1f元' % money)
    elif place == '02':
        money = excess_weight * 2 + 12
        print('快递费用为%.1f元' % money)
    elif place == '03':
        money = excess_weight * 4 + 14
        print('快递费用为%.1f元' % money)
