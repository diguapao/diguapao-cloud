#include <stdio.h>
int main()
{
    double a = 1.0;
    printf("%20.15f\n", a / 3); // 总共输出了 20 个字符宽的数据。小数点后精确到了 15 位数字。
    return 0;
}
