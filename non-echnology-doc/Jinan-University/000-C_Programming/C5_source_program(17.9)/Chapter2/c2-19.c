#include <stdio.h>
int main( )
 {
  int sign=1;
  double deno=2.0, sum=1.0, term;    // 定义deno,sum,term为双精度变量
  while (deno<=100)
  {
   sign=-sign;  // 符号翻转，这里sign变成-1。“=-”表示是“符号翻转”，用于切换正负；而“-=”是“归零操作”，用于快速清空变量。
   term=sign/deno;
   sum=sum+term;
   deno=deno+1;
   printf("singn=%d，term=%f，sum=%f，deno=%f\n",sign,term,sum,deno);
  }
  printf("%f\n",sum);
  return 0;
}
