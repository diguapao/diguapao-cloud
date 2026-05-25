package j.u.java002;

// 1. 定义父类 Animal
class Animal {
    // 父类的鸣叫方法
    public void cry() {
        System.out.println("动物在鸣叫...");
    }
}

// ==========================================
// 要求 1：子类 Dog 继承 Animal，并重写 cry()
// ==========================================
class Dog extends Animal {
    // @Override 注解显式声明重写，提高代码可读性和安全性
    @Override
    public void cry() {
        System.out.println("狗叫：汪汪汪！");
    }
}

// ==========================================
// 要求 1：子类 Cat 继承 Animal，并重写 cry()
// ==========================================
class Cat extends Animal {
    @Override
    public void cry() {
        System.out.println("猫叫：喵喵喵~");
    }
}

// ---------------------------------------------------
// 要求 2：测试多态特性
// ---------------------------------------------------
public class PolyTest {
    public static void main(String[] args) {

        // 利用多态：创建父类 Animal 数组，存放子类 Dog 和 Cat 对象
        // 这一步体现了“父类引用指向子类对象”的多态核心
        Animal[] animals = new Animal[4];
        animals[0] = new Dog(); // 向上转型
        animals[1] = new Cat(); // 向上转型
        animals[2] = new Dog();
        animals[3] = new Cat();

        System.out.println("================ 循环遍历调用 cry() ================");
        // 循环遍历调用 cry() 方法
        for (Animal animal : animals) {
            // 运行时绑定（动态绑定）：
            // 编译时 animal 是 Animal 类型，但运行时它会根据具体的子类实例去执行对应重写后的方法
            animal.cry();
        }
    }
}