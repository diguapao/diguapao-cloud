words_book = set()
words_only_set = set()


print('=' * 20)
print('欢迎使用生词本')
print('1.查看生词本')
print('2.背单词')
print('3.添加新单词')
print('4.删除单词')
print('5.清空生词本')
print('6.退出生词本')
print('=' * 20)


while True:
    word_dict = {}
    fun_num = input('请输入功能编号：')
    if fun_num == '1':  # 查看生词本
        if len(words_book) == 0:
            print('生词本内容为空')
        else:
            print(words_book)
    elif fun_num == '2':  # 背单词
        if len(words_book) == 0:
            print('生词本内容为空')
        else:
            for random_words in words_book:
                w = random_words.split(':')
                in_words = input("请输入" + w[0] + '翻译'+'：\n')
                if in_words == w[1].strip():
                    print('太棒了')
                else:
                    print('再想想')
    elif fun_num == '3':  # 添加新单词
        new_words = input('请输入新单词：')
        # 检测单词是否重复
        if new_words in words_only_set:
            # 添加的单词重复
            print('此单词已存在')
        else:
            # 执行单词添加
            new_chinese = input('请输入单词翻译：')
            word_dict.update({new_words: new_chinese})
            # 转换成字符串存入set集合中
            dict_str = str(word_dict).replace('{', '').replace('}',
 '').replace("'", '')
            words_book.add(dict_str)
            print('单词添加成功')
            dict_str = dict_str.replace(',', '')
            print(dict_str)
            words_only_set.add(new_words)
    elif fun_num == '4':  # 删除单词
        if len(words_book) == 0:
            print('生词本为空')
        else:
            temp_list = list(words_book)
            print(temp_list)
            del_wd = input("请输入要删除的单词")
            # 如果要删除的单词不在单词集合中
            if del_wd not in words_only_set:
                print('删除的单词不存在')
            else:
                words_only_set.discard(del_wd)
                for temp in temp_list:
                    if del_wd in temp:
                        words_book.remove(temp)
                        print('删除成功')
    elif fun_num == '5':  # 清空
        if len(words_book) == 0:
            print('生词本为空')
        else:
            words_only_set.clear()
            words_book.clear()
            print('生词本清空成功')
    elif fun_num == '6':  # 退出
        print('退出成功')
        break
