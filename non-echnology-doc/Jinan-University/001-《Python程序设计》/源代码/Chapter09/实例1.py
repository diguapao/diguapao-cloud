import json
file = open("身份证码值对照表.txt", 'r',encoding='utf-8')
content = file.read()
content_dict = json.loads(content)  # 转换为字典类型
address = input('请输入身份证前6位：')
for key, val in content_dict.items():
    if key == address:
        print(val)
file.close()
