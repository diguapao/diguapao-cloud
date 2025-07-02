# 商品列表，每个元素包含商品名称、价格和销量
products = [
    {'name': '华为P60', 'price': 4887.00, 'sales': 210},
    {'name': '华为Mate 40E Pro', 'price': 4699.00, 'sales': 90},
    {'name': '华为nova 10 青春版', 'price': 1698.00 , 'sales': 102},
    {'name': '华为P50 pro', 'price': 3987.00, 'sales': 88},
    {'name': '华为畅享', 'price': 999.00, 'sales': 152}
]
# 使用匿名函数定义排序规则，按照价格降序和销量升序排序
sorted_products = sorted(products, key=lambda x: x['sales'], reverse=True)
# 输出排序结果
for product in sorted_products:
    print(f"商品：{product['name']}，价格：{product['price']}，"
          f"销量：{product['sales']}")
