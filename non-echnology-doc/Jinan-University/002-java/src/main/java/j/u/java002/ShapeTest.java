package j.u.java002;

// 1. 定义抽象类 Shape
abstract class Shape {
    // 抽象方法：求面积（抽象方法没有方法体，子类必须重写）
    public abstract double getArea();
}

// ==========================================
// 2. 定义接口 Draw
// ==========================================
interface Draw {
    // 接口中的方法默认是 public abstract 的，可以省略关键字
    void draw();
}

// ==========================================
// 3. 定义 Circ 圆类，继承 Shape 并实现 Draw 接口
// ==========================================
class Circ extends Shape implements Draw {
    private double radius; // 半径

    public Circ() {}

    public Circ(double radius) {
        this.radius = radius;
    }

    // 实现抽象类中的求面积方法
    @Override
    public double getArea() {
        return Math.PI * radius * radius; // π * r²
    }

    // 实现接口中的绘图方法
    @Override
    public void draw() {
        System.out.println("正在绘制一个圆形，半径为: " + radius);
    }

    // Getter/Setter
    public double getRadius() { return radius; }
    public void setRadius(double radius) { this.radius = radius; }
}

// ==========================================
// 3. 定义 Rectang 矩形类，继承 Shape 并实现 Draw 接口
// ==========================================
class Rectang extends Shape implements Draw {
    private double width;  // 宽
    private double height; // 高

    public Rectang() {}

    public Rectang(double width, double height) {
        this.width = width;
        this.height = height;
    }

    // 实现抽象类中的求面积方法
    @Override
    public double getArea() {
        return width * height; // 宽 * 高
    }

    // 实现接口中的绘图方法
    @Override
    public void draw() {
        System.out.println("正在绘制一个矩形，宽度为: " + width + ", 高度为: " + height);
    }

    // Getter/Setter
    public double getWidth() { return width; }
    public void setWidth(double width) { this.width = width; }
    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }
}

// ---------------------------------------------------
// 4. 测试类
// ---------------------------------------------------
public class ShapeTest {
    public static void main(String[] args) {
        // 创建圆和矩形对象
        Circ circle = new Circ(5.0);
        Rectang rectangle = new Rectang(4.0, 6.0);

        System.out.println("================= 测试圆形 =================");
        // 调用求面积和绘图方法
        System.out.printf("圆形的面积为: %.2f\n", circle.getArea());
        circle.draw();

        System.out.println("\n================= 测试矩形 =================");
        // 调用求面积和绘图方法
        System.out.printf("矩形的面积为: %.2f\n", rectangle.getArea());
        rectangle.draw();
    }
}