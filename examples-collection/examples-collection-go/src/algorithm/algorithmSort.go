package algorithm

import "fmt"

func BubbleSort(nums []int) {
	n := len(nums)
	for i := 0; i < n-1; i++ { //控制遍历次数
		for j := 0; j < n-1-i; j++ { //比较相邻元素并进行交换
			if nums[j] > nums[j+1] {
				// 交换
				nums[j], nums[j+1] = nums[j+1], nums[j]
			}
		}
	}
}

// PrintSlice 打印整数切片
func PrintSlice(slice []int) {
	fmt.Println(slice)
}
