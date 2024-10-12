package org.diguapao.cloud.ec.algorithm;

import java.util.Arrays;

/**
 * 冒泡排序-Java 实现
 *
 * @author diguapao
 * @version 2024.10.12
 * @since 2024-10-12 21:08:54
 */
public class BubbleSortForJava {

    public static void main(String[] args) {
        BubbleSortForJava that = new BubbleSortForJava();
        int[] array = {2, 6, 1, 5, 9, 0, 12, 35};
        System.out.println("排序前：" + Arrays.toString(array));
        that.bubbleSort(array);
        System.out.println("排序后：" + Arrays.toString(array));
    }

    public void bubbleSort(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (nums[j] > nums[j + 1]) {
                    // 交换
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                }
            }
        }
    }

}

