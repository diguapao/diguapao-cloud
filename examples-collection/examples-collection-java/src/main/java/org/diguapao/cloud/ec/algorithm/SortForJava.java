package org.diguapao.cloud.ec.algorithm;

import java.util.Arrays;

/**
 * 冒泡排序-Java 实现
 *
 * @author diguapao
 * @version 2024.10.12
 * @since 2024-10-12 21:08:54
 */
public class SortForJava {

    public static void main(String[] args) {
        SortForJava that = new SortForJava();
        int[] array = {2, 6, 1, 5, 9, 0, 12, 35};
        System.out.println("排序前：" + Arrays.toString(array));
        that.selectionSort(array);
        System.out.println("排序后：" + Arrays.toString(array));
    }

    /**
     * 选择排序
     *
     * @param nums
     */
    public void selectionSort(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[j] < nums[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = nums[i];
            nums[i] = nums[minIndex];
            nums[minIndex] = temp;
        }
    }

    /**
     * 冒泡排序
     *
     * @param nums
     */
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

    /**
     * 冒泡排序思路
     * 假设有如下数组：
     * 8 6 4 3 2 1 7 5
     * 0 1 2 3 4 5 6 7
     * <p>
     * 可以看出0位置是8，7位置是5
     * 在进行冒泡排序时：
     * 让0位置的8与1位置的3比较，若0位置的8大于1位置的3，则让0位置的8与1位置的3交换，0位置变成了3，1位置变成了8
     * 让1位置的8与2位置的4比较，若1位置的8大于2位置的4，则让1位置的8与2位置的4交换，1位置变成了4，2位置变成了8
     * ……
     * 让6位置的8与7位置的5比较，若6位置的8大于7位置的5，则让6位置的8与7位置的5交换，6位置变成了5，7位置变成了8
     * 这么这一轮下来，最大的数就换到了最后的位置，
     * 6 4 3 2 1 7 5 8
     * 0 1 2 3 4 5 6 7
     * 进入下一轮：
     * 让0位置的6与1位置的4比较，若0位置的6大于1位置的4，则让0位置的6与1位置的4交换，0位置变成了4，1位置变成了6
     * 让1位置的5与2位置的3比较，若1位置的6大于2位置的3，则让1位置的6与2位置的3交换，1位置变成了3，2位置变成了6
     * ……
     * 让5位置的6与6位置的5比较，若5位置的6大于6位置的5，则让5位置的6与6位置的5交换，5位置变成了5，6位置变成了6
     * 这样依次交换7-1，即6轮，排序完成
     *
     * @param nums
     */
    public void bubbleSortV2(int[] nums) {
        for (int i = nums.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                int num = nums[j];
                if (num > nums[j + 1]) {
                    int numJ1 = nums[j + 1];
                    nums[j] = numJ1;
                    nums[j + 1] = num;
                }
            }
        }
    }

    /**
     * 插入排序
     *
     * @param nums
     */
    public void insertionSort(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            for (int j = i - 1; j >= 0 && nums[j] > nums[j + 1]; j--) {
                int numJ = nums[j];
                nums[j] = nums[j + 1];
                nums[j + 1] = numJ;
            }
        }
    }

}

