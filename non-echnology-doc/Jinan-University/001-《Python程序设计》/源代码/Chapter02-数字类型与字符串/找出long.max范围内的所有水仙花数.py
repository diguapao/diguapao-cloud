# long类型在Java中是64位有符号整数，其最大值为9,223,372,036,854,775,807
def find_narcissistic_numbers(max_value):
    # 遍历可能的位数，从3开始直到找到的最大水仙花数的位数为止
    for digits in range(3, 39 + 1):  # 假设我们知道的最大水仙花数是39位
        lower_bound = 10 ** (digits - 1)
        upper_bound = min(10 ** digits - 1, max_value)

        if lower_bound > max_value:
            break

        # 预先计算0-9的digits次幂
        power_table = {str(i): i**digits for i in range(10)}

        for num in range(lower_bound, upper_bound + 1):
            sum_of_powers = sum(power_table[digit] for digit in str(num))
            if sum_of_powers == num:
                print(num)

# 设置最大值为long.max对应的值，在Python中可以用sys.maxsize来近似表示
import sys
find_narcissistic_numbers(sys.maxsize)