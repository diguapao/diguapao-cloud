def calculate_discount(price, discount_rate=0.9, *args, **kwargs):
    """
    用于计算商品折扣
    :param price:商品原价
    :param discount_rate:折扣率
    :param args:额外的折扣率
    :param kwargs:额外的折扣类型和值，折扣类型有百分比折扣(percentage)
    和固定金额折扣(amount)
    :return:计算完折扣的价格
    """
    # 根据折扣率计算折扣后的价格
    discounted_price = price * discount_rate
    # 根据额外折扣率计算折扣后的价格
    if args:
        for additional_discount in args:
            discounted_price = discounted_price * additional_discount
    # 根据额外折扣类型和值计算折扣后的价格
    if kwargs:
        for discount_type, value in kwargs.items():
            if discount_type == 'percentage':  # 折扣类型是百分比折扣
                discounted_price = discounted_price * value
            elif discount_type == 'amount':  # 折扣类型是固定金额折扣
                discounted_price -= value
    return discounted_price
# 商品原价
product_price = float(input("请输入商品价格: "))
# 折扣率
discount_rate = float(input("请输入折扣率 (0~1之间的小数): "))
# 额外折扣率
additional_discounts = []
input_discounts = input("请输入额外折扣率(多个折扣率以空格分隔): ").split()
for discount in input_discounts:
    additional_discounts.append(float(discount))
print(additional_discounts)
# 额外的折扣类型和值
discount_types = input("请输入折扣类型和值(如类型：值): ").split(',')
kwargs = {}
for discount_type_value in discount_types:
    discount_type, value = discount_type_value.split(':')
    kwargs[discount_type.strip()] = float(value.strip())
print(kwargs)
discounted_price = calculate_discount(product_price, discount_rate,
*additional_discounts, **kwargs)
print("折扣后的价格为:", discounted_price)
