# Python 的特点

- 简单易学
- 免费开源
- 可移植性
- 丰富的库
- 良好的中文支持
- 面向对象
- 解释型语言

# Python 的应用领域

- Web开发
- 网络爬虫
- 自动化运维
- 游戏开发
- 人工智能
- 数据分析

# 关键字

![img.png](./img.assets/img.png)

# 位运算

![image-20250323225351785](img.assets/image-20250323225351785.png)

# Python程序开发案例教程 (Pdg2Pic, 黑马程序员编著)

## 有趣浅习

让我们用一种有趣且简洁的方式来学习 Python 的核心知识！

---

### **1. 数字类型与字符串**

- **数字类型**：Python 有整数（`int`）、浮点数（`float`）和复数（`complex`）。
    - 整数是“老实人”，永远精确；浮点数是“近似派”，有时会有点误差。
    - 复数是“数学家的最爱”，带着虚部一起玩（比如 `3 + 4j`）。
- **字符串**：字符串是“文本艺术家”，用单引号 `'hello'` 或双引号 `"world"` 包裹。
    - 它们可以拼接（`+`），重复（`*`），甚至切片（`"hello"[1:4]` -> `"ell"`）。

---

### **2. 流程控制**

- **条件判断**：`if` 是“选择大师”，它会根据条件决定走哪条路：
  ```python
  if 天气 == "晴天":
      print("去公园")
  elif 天气 == "雨天":
      print("看电影")
  else:
      print("睡觉")
  ```
- **循环**：`for` 和 `while` 是“勤劳工人”。
    - `for` 适合遍历列表、字符串等：“我挨个检查每个元素！”
    - `while` 更自由：“只要条件为真，我就一直干！”

---

### **3. 列表与元组**

- **列表**：列表是“可变盒子”，能装任意东西，还能随时增删改查：
  ```python
  fruits = ["苹果", "香蕉", "橙子"]
  fruits.append("葡萄")  # 加入新水果
  ```
- **元组**：元组是“固定盒子”，一旦装好就不能改了：
  ```python
  colors = ("红", "绿", "蓝")
  ```

---

### **4. 字典和集合**

- **字典**：字典是“快查手册”，用键值对存储数据：
  ```python
  person = {"名字": "小明", "年龄": 18}
  print(person["名字"])  # 输出“小明”
  ```
- **集合**：集合是“无序俱乐部”，成员不能重复：
  ```python
  unique_numbers = {1, 2, 3, 3}  # 结果是 {1, 2, 3}
  ```

---

### **5. 函数**

- 函数是“魔法咒语”，定义一次就能反复调用：
  ```python
  def say_hello(name):
      return f"你好，{name}！"
  print(say_hello("世界"))  # 输出“你好，世界！”
  ```
- 参数可以灵活设置，默认值、可变参数都行！

---

### **6. 类与面向对象**

- **类**：类是“蓝图”，用来创建对象（实例）：
  ```python
  class Dog:
      def __init__(self, name):
          self.name = name
      def bark(self):
          return f"{self.name} 汪汪叫！"
  my_dog = Dog("旺财")
  print(my_dog.bark())  # 输出“旺财 汪汪叫！”
  ```
- 面向对象就是让代码像现实世界一样，万物皆对象！

---

### **7. 模块**

- 模块是“工具箱”，把代码分门别类放进去：
  ```python
  import math  # 引入数学模块
  print(math.sqrt(16))  # 输出 4.0
  ```
- 自己写的代码也可以打包成模块，方便复用。

---

### **8. 文件与文件路径操作**

- 文件操作就像“写信和读信”：
  ```python
  with open("日记.txt", "w") as file:  # 写入模式
      file.write("今天天气很好！")
  with open("日记.txt", "r") as file:  # 读取模式
      print(file.read())
  ```
- 路径操作可以用 `os` 或 `pathlib` 模块，轻松搞定跨平台问题。

---

### **9. 错误和异常**

- 错误是“不速之客”，但我们可以用 `try-except` 来应对：
  ```python
  try:
      x = 1 / 0
  except ZeroDivisionError:
      print("除零错误！")
  finally:
      print("无论如何都会执行这句话。")
  ```

---

### **10. 正则表达式**

- 正则表达式是“文本侦探”，擅长找模式：
  ```python
  import re
  text = "我的电话是 123-4567"
  match = re.search(r"\d{3}-\d{4}", text)
  print(match.group())  # 输出“123-4567”
  ```

---

### **11. 进程和线程**

- **进程**：独立运行的程序，像是“分开的工厂”。
- **线程**：进程里的“工人”，共享资源，但需要小心“争抢”问题：
  ```python
  from threading import Thread
  
  def task():
      print("我在工作！")
  
  thread = Thread(target=task)
  thread.start()
  thread.join()  # 等待线程完成
  ```

---

### **12. 网络编程**

- 网络编程是“邮递员的工作”，发送和接收数据包：
  ```python
  import socket
  
  sock = socket.socket()
  sock.connect(("www.google.com", 80))
  sock.send(b"GET / HTTP/1.1\r\nHost: www.google.com\r\n\r\n")
  response = sock.recv(4096)
  print(response.decode())
  ```

---

### **13. 数据库编程**

- 数据库编程是“图书馆管理”，用 SQL 查询和操作数据：
  ```python
  import sqlite3
  
  conn = sqlite3.connect("example.db")
  cursor = conn.cursor()
  cursor.execute("CREATE TABLE IF NOT EXISTS users (name TEXT, age INTEGER)")
  cursor.execute("INSERT INTO users VALUES ('小明', 18)")
  conn.commit()
  conn.close()
  ```

---

## 深入研究

## 课本习题

### 第一章

好的，让我们一起来解答这份关于Python的习题。

#### 一、填空题

1. Python 是一种面向 **对象** 的语言。
2. Python 编写的程序可以在任何平台中执行，这体现了 Python 的 **跨平台** 特点。

#### 二、判断题

1. Python 具有丰富的第三方库。 (✓)
2. Python 2 中的异常与 Python 3 中的异常使用方式相同。 (✗)
3. PyCharm 是一个完全免费的 IDE 工具。 (✗)

#### 三、选择题

1. 下列选项中，不属于 Python 特点的是（D）。
    - A. 简单易学
    - B. 免费开源
    - C. 面向对象
    - D. 编译型语言

2. 下列关于 Python 2 与 Python 3 的说法中，错误的是（B）。
    - A. Python 3 默认使用 UTF-8 编码
    - B. Python 2 与 Python 3 中的 print 语句的格式没有变化
    - C. Python 2 默认使用 ASCII 编码
    - D. Python 2 与 Python 3 中运算符 “//” 的使用方式一致

3. 下列关于 Python 命名规范的说法中，错误的是（D）。
    - A. 模块名、包名应简短且全为小写
    - B. 类名首字母一般使用大写
    - C. 常量通常使用全大写字母命名
    - D. 函数名中不可使用下画线

4. 下列选项中，（B）是不符合规范的变量名。
    - A. _text
    - B. 2cd
    - C. ITCAST
    - D. hei_ma

5. 下列关于 input()与 print()函数的说法中，错误的是（D）。
    - A. input()函数可以接收由键盘输入的数据
    - B. input()函数会返回一个字符串类型数据
    - C. print()函数可以输出任何类型的数据
    - D. print()函数输出的数据不支持换行操作

#### 四、简答题

1. **简述 Python 的特点。**
    - Python 是一种解释型、面向对象、动态数据类型的高级程序设计语言。
    - 它具有简单易学、语法简洁的特点，适合快速开发和原型设计。
    - Python 支持多种编程范式，包括面向对象、过程化和函数式编程。
    - Python 拥有丰富的第三方库，可以方便地进行各种应用开发，如Web开发、数据分析、人工智能等。
    - Python 具有良好的跨平台性，可以在多种操作系统上运行。

2. **简述 Python 2 与 Python 3 的区别。**
    - **打印语句**：Python 2 使用 `print` 语句，而 Python 3 使用 `print()` 函数。
    - **整数除法**：Python 2 中 `/` 运算符在整数除法时返回整数结果，而 Python 3 总是返回浮点数结果。
    - **Unicode 支持**：Python 3 默认使用 Unicode 字符串，而 Python 2 默认使用 ASCII 字符串。
    - **异常处理**：Python 3 异常处理语法更统一，例如 `except Exception as e`。
    - **其他改进**：Python 3 在语法、库等方面进行了许多改进，以提高代码的可读性和效率。

---
**_注：部分内容由AI生成！_**