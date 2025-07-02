# 创建字典，用于保存字母h、u及其对应的单词Thusday、Tuesday
tues_or_thurs = {'h': 'Thursday', 'u': 'Tuesday'}
# 创建字典，用于保存字母a、u及其对应的单词Saturday、Sunday
weekend = {'a': 'Saturday', 'u': 'Sunday'}
# 创建字典，用于保存字母m、w、f及其对应的单词Monday、Wednesday、Friday，
# 字母t、s及其对应的前面两个字典
week = {'t': tues_or_thurs, 's': weekend,
         'm': 'Monday', 'w': 'Wednesday',
         'f': 'Friday'}
first_char = input('请输入第一位字母：').lower().strip()
# 判断字母是否为m、w、f、t、s
if first_char in ['t', 's', 'm', 'w', 'f']:
    # 判断第一个字母对应的值是否为字典tues_or_thurs或weekend
    if week[first_char] == tues_or_thurs or week[first_char] == weekend:
        second_char = input('请输入第二位字母：').lower().strip()
        # 判断第二个字母是否为u、h、a
        if second_char in ['u', 'h', 'a']:
            # 根据键first_char访问其对应的字典，
            # 之后根据键second_char访问字典中其对应的值
            print(week[first_char][second_char])
        else:
            print('请输入正确字母')
    else:
        print(week[first_char])
else:
    print('请输入正确的字母')
