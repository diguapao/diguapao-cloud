# 准备一段文本
text = '我们拥有多年的品牌战略规划及标志设计、商标注册经验；' \
        '专业提供公司标志设计与商标注册一条龙服务。' \
         '我们拥有最优秀且具有远见卓识的设计师，使我们的策略分析严谨，' \
         '设计充满创意。我们有信心为您缔造最优秀的品牌形象设计服务，' \
         '将您的企业包装得更富价值。'
# 查找不良词语
sensitive_word = '最优秀'                      # 设立不良词语
replace_word = '较优秀'                        # 替换后词语
result = text.find(sensitive_word)
# 根据查找结果分情况处理文本
if result != -1:       # 找到不良词语
    text = text.replace(sensitive_word, replace_word) # 替换不良词语
    print('过滤后的文本：\n' + text)
else:                    # 没有找到不良词语
    print("无不良词语！")
