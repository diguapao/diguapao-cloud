package j.u.java002;

import java.util.Scanner;

public class PrimeValidator {

    public static void main(String[] args) {
        // 1. 使用 Scanner 接收键盘输入
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入一个整数: ");
        
        // 确保输入的是整数
        if (!scanner.hasNextInt()) {
            System.out.println("输入无效，请输入合法的整数！");
            return;
        }
        
        int num = scanner.nextInt();

        // 2. 做输入合法性判断，负数直接提示输入无效
        if (num < 0) {
            System.out.println("输入无效");
            return;
        }

        // 3. 判断是否为素数并按格式输出
        if (isPrime(num)) {
            System.out.println(num + " 是素数");
        } else {
            System.out.println(num + " 不是素数");
        }
        
        // 良好的习惯，用完关闭流
        scanner.close();
    }

    /**
     * 判断一个数是否为素数的核心方法
     */
    private static boolean isPrime(int n) {
        // 0 和 1 既不是素数也不是合数
        if (n <= 1) {
            return false;
        }
        // 2 是最小的素数，也是唯一的偶素数
        if (n == 2) {
            return true;
        }
        // 排除掉除 2 以外的所有偶数，提高后续循环效率
        if (n % 2 == 0) {
            return false;
        }
        
        // 核心优化：只需要遍历到根号 n 即可
        // 如果一个数能被整除，它的因数必然一个大于等于根号 n，另一个小于等于根号 n
        int boundary = (int) Math.sqrt(n);
        for (int i = 3; i <= boundary; i += 2) { // 步长为 2，跳过偶数
            if (n % i == 0) {
                return false; // 能被整除，说明不是素数
            }
        }
        
        return true;
    }
}