package org.diguapao.cloud.ec.str;

/**
 * 数字格式化
 */
public class NumberFormatter {

    public static void main(String[] args) {
        // 测试用例
        // 输出: "12"
        System.out.println(foo(12, 2));
        // 输出: "1,234"
        System.out.println(foo(1234, 3));
        // 输出: "12,34,56"
        System.out.println(foo(123456, 2));
        // 输出: "1,2345"
        System.out.println(foo(12345, 4));
    }

    // 把整数 v 转换成 String，但从个位起每隔 x(x>0) 个数字就加一个逗号(,)分隔。例如：
    // foo(12, 2) => "12"
    // foo(1234, 3) => "1,234"
    // foo(123456, 2) => "12,34,56"
    // foo(12345, 4) => "1,2345"
    public static String foo(int v, int x) {
        if (x <= 0) {
            throw new RuntimeException("X必须大于0");
        }

        // 将整数转换为字符串
        String str = Integer.toString(v);
        int len = str.length();

        // 如果长度小于等于 x，则直接返回原字符串
        if (len <= x) {
            return str;
        }

        // 方法一
        // 使用 StringBuilder 来构建结果字符串
//        StringBuilder result = new StringBuilder(str);
//        // 从右到左每隔 x 个字符插入一个逗号
//        for (int i = len - x; i > 0; i -= x) {
//            result.insert(i, ',');
//        }

        //方法二
        char[] arr = str.toCharArray();
        StringBuilder result = new StringBuilder();
        for (int i = arr.length - 1; i > 0; i++) {
            if ((i) % x == 0 && i < arr.length - 1 && i != 0) {
                result.append(",");
            }
            result.append(arr[i]);
        }

        return result.toString();
    }

}