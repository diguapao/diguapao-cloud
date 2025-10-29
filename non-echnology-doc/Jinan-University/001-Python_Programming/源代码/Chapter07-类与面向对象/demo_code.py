print('===========   7.2.2类的定义   ===========')


class Car:
    wheels = 4  # 属性

    def drive(self):  # 方法drive()
        print('开车方式')

    def stop(self):  # 方法stop()
        print('停车方式')


print('===========   7.2.3对象的创建与使用   ===========')
my_car = Car()
print(my_car.wheels)  # 访问对象my_car的属性wheels
my_car.drive()  # 调用对象my_car的方法drive()
my_car.stop()  # 调用对象my_car的方法stop()

print('===========   7.2.4访问限制   ===========')


class PersonInfo:
    __weight = 55  # 定义私有属性

    def __info(self):  # 定义私有方法
        print(f'我的体重是55kg')


person = PersonInfo()


# person.__weight  # 在类外部访问私有属性，运行会报错
# person.__info()  # 在类外部调用私有方法，运行会报错


class PersonInfo:
    __weight = 55  # 定义私有属性

    def __info(self):  # 定义私有方法
        print(f'我的体重是55kg')

    def get_weight(self):
        print(f'我的体重是{self.__weight}kg')  # 在类内部访问私有属性


person = PersonInfo()
person.get_weight()


class PersonInfo:
    __weight = 55  # 定义私有属性

    def __info(self):  # 定义私有方法
        print(f'我的体重是{self.__weight}kg')

    def get_weight(self):
        print(f'我的体重是{self.__weight}kg')  # 在类内部访问私有属性
        self.__info()  # 在类内部调用私有方法


person = PersonInfo()
person.get_weight()

print('===========   7.3	构造方法   ===========')


class Person(object):
    def __init__(self, name, sex):  # 有参构造方法
        self.name = name  # 添加属性name，并将该属性的值赋为参数name
        self.sex = sex  # 添加属性sex，并将该属性的值赋为参数sex

    def introduce(self):
        print(f'姓名：{self.name}')
        print(f'性别：{self.sex}')


person_one = Person('小李', '女')
person_one.introduce()
person_two = Person('小张', '男')
person_two.introduce()

print('===========   7.4.1类方法   ===========')


class Test:
    @classmethod  # 定义类方法
    def use_classmet(cls):
        print("我是类方法")


test = Test()
test.use_classmet()  # 通过对象名调用类方法
Test.use_classmet()  # 通过类名调用类方法


class Apple(object):
    count = 0  # 定义类属性

    @classmethod
    def add_two(cls):
        cls.count = 2  # 在类方法中修改类属性的值


apple = Apple()
print(Apple.count)
Apple.add_two()
print(Apple.count)

print('===========   7.4.2静态方法   ===========')


class Example:
    num = 10  # 类属性

    @staticmethod  # 定义静态方法
    def static_method():
        print(f"类属性的值为：{Example.num}")
        print("---静态方法")


example = Example()  # 创建对象
example.static_method()  # 通过对象名调用静态方法
Example.static_method()  # 通过类名调用静态方法

print('===========   7.6.1单继承   ===========')


class Amphibian:
    name = "两栖动物"

    def features(self):
        print("幼年用鳃呼吸")
        print("成年用肺兼皮肤呼吸")


class Frog(Amphibian):  # Frog类继承自Amphibian类
    def attr(self):
        print("我会呱呱叫")


frog = Frog()
print(frog.name)  # 访问父类的属性
frog.features()  # 调用父类的方法
frog.attr()  # 调用自身的方法

print(isinstance(frog, Frog))
print(issubclass(Frog, Amphibian))

print('===========   7.6.2多继承   ===========')


class English:
    def receive_eng_know(self):
        print('具备英语知识。')


class Math:
    def receive_math_know(self):
        print('具备数学知识。')


class Student(English, Math):
    def study(self):
        print('学生的任务是学习。')


stu = Student()
stu.receive_eng_know()
stu.receive_math_know()
stu.study()

print('===========   7.6.3方法的重写   ===========')


class Felines:
    def speciality(self):
        print("猫科动物善于奔跑")


class Cat(Felines):
    name = "猫"

    def speciality(self):
        print(f'{self.name}爱吃鱼')
        print(f'{self.name}会爬树')


cat = Cat()
cat.speciality()

print('===========   7.6.4super()函数   ===========')


class Cat(Felines):
    name = "猫"

    def speciality(self):
        print(f'{self.name}爱吃鱼')
        print(f'{self.name}会爬树')
        print('-' * 20)
        super().speciality()  # 使用super()函数调用父类被重写的方法


cat = Cat()
cat.speciality()

print('===========   7.8	多态   ===========')


class Animal(object):  # 定义父类Animal
    def move(self):
        pass


class Rabbit(Animal):  # 定义子类Rabbit
    def move(self):
        print("兔子蹦蹦跳跳")


class Snail(Animal):  # 定义子类Snail
    def move(self):
        print("蜗牛缓慢爬行")


def test(obj):  # 在函数test()中调用了对象的move()方法
    obj.move()


rabbit = Rabbit()
test(rabbit)  # 传入Rabbit类的对象
snail = Snail()
test(snail)  # 传入Snail类的对象
