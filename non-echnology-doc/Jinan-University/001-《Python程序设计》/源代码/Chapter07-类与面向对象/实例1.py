class Search:
    info = [{'天问一号': {'时间': '2020年', '简介': '天问一号是我国自行研制的'
             '探测器，负责执行中国第一次自主火星探测任务。'}},
            {'长征十一号海射运载火箭': {'时间': '2022年','简介': '长征十一号'
             '是我国自主研制的一型四级全固体运载火箭，该火箭主要用于快速机动发'
             '射应急卫星，满足自然灾害、突发事件等应急情况下微小卫星发射需求。'}},
            {'长征五号B运载火箭': {'时间': '2022年','简介': '长征五号B运载火'
             '箭是专门为中国载人航天工程空间站建设而研制的一型新型运载火箭，以'
             '长征五号火箭为基础改进而成，是中国近地轨道运载能力最大的新'
             '一代运载火箭。'}}]
    def search_info(self):
        name_li = []          # 存储航天器和火箭的名称
        print('✈' * 11)
        # 输出所有名称
        for info_dict in self.info:
            for name in info_dict:
                name_li.append(name)
                print(name)
        print('✈' * 11)
        search_name = input('请输入查询名称：')
        if search_name not in name_li:         # 判断查询的名称是否存在
            print('查询的名称不存在')
        else:
            for i in Search.info:
                for s_name, s_info, in i.items():
                    if s_name == search_name:       # 判断查询的名称是否存在
                        for title, info in s_info.items():
                            print(title + ':' + info)
search = Search()
search.search_info()
