package j.u.java002;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// 复用题目 3 的 Student 类（保持原样）
class Student {
    private String id;
    private String name;
    private int age;
    private double chineseScore;
    private double mathScore;

    public Student() {}

    public Student(String id, String name, int age, double chineseScore, double mathScore) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.chineseScore = chineseScore;
        this.mathScore = mathScore;
    }

    public double getSumScore() {
        return this.chineseScore + this.mathScore;
    }

    public double getAverageScore() {
        return this.getSumScore() / 2.0;
    }

    // Getter/Setter
    public String getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public double getChineseScore() { return chineseScore; }
    public double getMathScore() { return mathScore; }
}

// ==========================================
// 6. 主观题测试类
// ==========================================
public class CollectionTest {
    public static void main(String[] args) {
        // 创建 ArrayList 存储 5 个学生对象
        ArrayList<Student> list = new ArrayList<>();
        list.add(new Student("1001", "张三", 18, 85.0, 90.0)); // 总分 175
        list.add(new Student("1002", "李四", 19, 75.5, 82.0)); // 总分 157.5
        list.add(new Student("1003", "王五", 18, 95.0, 98.0)); // 总分 193
        list.add(new Student("1004", "赵六", 20, 60.0, 70.0)); // 总分 130
        list.add(new Student("1005", "钱七", 19, 88.0, 88.0)); // 总分 176

        // ------------------------------------------
        // 要求 1：遍历集合打印所有学生信息
        // ------------------------------------------
        System.out.println("================ 1. 原始学生列表 ================");
        printList(list);

        // ------------------------------------------
        // 要求 2：按总分从高到低（降序）排序集合
        // ------------------------------------------
        // 使用 Collections.sort 配合自定义比较器，降序排序：后者减前者
        Collections.sort(list, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                // 考虑到总分是 double 类型，直接使用 Double.compare 保证精度安全
                return Double.compare(o2.getSumScore(), o1.getSumScore());
            }
        });
        
        /* 💡 现代 Java (Java 8+) 也可以直接一行 Lambda 表达式搞定：
        list.sort((o1, o2) -> Double.compare(o2.getSumScore(), o1.getSumScore())); */

        System.out.println("\n================ 2. 按总分从高到低排序后 ================");
        printList(list);

        // ------------------------------------------
        // 要求 3：根据姓名删除指定学生，遍历输出删除后结果
        // ------------------------------------------
        String targetName = "李四"; // 假设要删除的名字是 "李四"
        
        // 避坑点：使用 Java 8 的 removeIf 过滤，底层自动处理迭代器，安全高效
        list.removeIf(student -> student.getName().equals(targetName));

        System.out.println("\n================ 3. 删除姓名为 [" + targetName + "] 的学生后 ================");
        printList(list);
    }

    /**
     * 辅助方法：遍历打印集合
     */
    private static void printList(ArrayList<Student> list) {
        for (Student s : list) {
            System.out.printf("学号: %s | 姓名: %s | 年龄: %d | 总分: %.1f | 平均分: %.1f\n",
                    s.getId(), s.getName(), s.getAge(), s.getSumScore(), s.getAverageScore());
        }
    }
}