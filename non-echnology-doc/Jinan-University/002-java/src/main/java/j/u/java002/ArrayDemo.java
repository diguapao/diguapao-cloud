package j.u.java002;

import java.util.Random;

public class ArrayDemo {

    public static void main(String[] args) {
        // 创建长度为 10 的 int 数组
        int[] arr = new int[10];
        Random random = new Random();

        // 随机生成 1~100 整数填充数组
        // random.nextInt(100) 生成 0~99，加 1 后变成 1~100
        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(100) + 1;
        }

        // ==========================================
        // 要求 1：遍历打印原数组所有元素
        // ==========================================
        System.out.print("原数组元素为：");
        printArray(arr);

        // ==========================================
        // 要求 2：求出最大值、最小值、总和、平均值
        // ==========================================
        int max = arr[0];
        int min = arr[0];
        int sum = 0;

        for (int i = 0; i < arr.length; i++) {
            sum += arr[i]; // 累加求和
            
            if (arr[i] > max) {
                max = arr[i]; // 更新最大值
            }
            if (arr[i] < min) {
                min = arr[i]; // 更新最小值
            }
        }
        // 平均值为了保证精度，强转为 double 
        double avg = (double) sum / arr.length;

        System.out.println("最大值：" + max);
        System.out.println("最小值：" + min);
        System.out.println("总和：" + sum);
        System.out.println("平均值：" + avg);

        // ==========================================
        // 要求 3：使用冒泡排序对数组进行升序排序
        // ==========================================
        // 外层循环控制比较的轮数（10个元素需要比较9轮）
        for (int i = 0; i < arr.length - 1; i++) {
            // 设置一个优化标志位，如果某一轮没有发生交换，说明已经有序，可提前结束
            boolean swapped = false;
            
            // 内层循环控制每轮相邻元素的比较和交换
            // -i 是因为每轮结束后，最大的元素都会“沉底”到最后，不需要再比了
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) { // 如果前面的比后面的大，则交换位置
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true; // 发生了交换
                }
            }
            
            // 优化：如果这一轮没有发生任何交换，直接跳出循环
            if (!swapped) {
                break;
            }
        }

        // 输出排序后的数组
        System.out.print("升序排序后的数组为：");
        printArray(arr);
    }

    /**
     * 辅助方法：遍历打印数组
     */
    private static void printArray(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + (i == arr.length - 1 ? "" : ", "));
        }
        System.out.println(); // 换行
    }
}