package main

import (
	"examples-collection-go/src/algorithm"
	"fmt"
	"github.com/gin-gonic/gin"
	"net/http"
	"strconv"
)

// 定义一个结构体来表示用户
type User struct {
	ID    int    `json:"id"`
	Name  string `json:"name"`
	Email string `json:"email"`
}

var users = []User{
	{ID: 1, Name: "John Doe", Email: "john@example.com"},
	{ID: 2, Name: "Jane Doe", Email: "jane@example.com"},
}

func main() {
	router := gin.Default()

	// 路由配置
	v1 := router.Group("/api/v1")
	{
		v1.GET("/users", getUsers)
		v1.GET("/users/:id", getUserByID)
		v1.POST("/users", createUser)
		v1.PUT("/users/:id", updateUser)
		v1.DELETE("/users/:id", deleteUser)
		v1.POST("/algorithm/bubbleSort", bubbleSort)
	}

	// 启动服务器
	router.Run(":18181")
}

// 获取所有用户
func getUsers(c *gin.Context) {
	c.JSON(http.StatusOK, users)
}

// 获取单个用户
func getUserByID(c *gin.Context) {
	idStr := c.Param("id")
	id, err := strconv.Atoi(idStr)
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "Invalid user ID"})
		return
	}

	for _, user := range users {
		if user.ID == id {
			c.JSON(http.StatusOK, user)
			return
		}
	}

	c.JSON(http.StatusNotFound, gin.H{"error": "User not found"})
}

// 创建新用户
func createUser(c *gin.Context) {
	var newUser User
	if err := c.ShouldBindJSON(&newUser); err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	newUser.ID = len(users) + 1
	users = append(users, newUser)
	c.JSON(http.StatusCreated, newUser)
}

// 更新用户
func updateUser(c *gin.Context) {
	idStr := c.Param("id")
	id, err := strconv.Atoi(idStr)
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "Invalid user ID"})
		return
	}

	for i, user := range users {
		if user.ID == id {
			var updatedUser User
			if err := c.ShouldBindJSON(&updatedUser); err != nil {
				c.JSON(http.StatusBadRequest, gin.H{"error": err.Error()})
				return
			}

			updatedUser.ID = id // 确保更新后的用户 ID 不变
			users[i] = updatedUser
			c.JSON(http.StatusOK, updatedUser)
			return
		}
	}

	c.JSON(http.StatusNotFound, gin.H{"error": "User not found"})
}

// 删除用户
func deleteUser(c *gin.Context) {
	idStr := c.Param("id")
	id, err := strconv.Atoi(idStr)
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "Invalid user ID"})
		return
	}

	for i, user := range users {
		if user.ID == id {
			users = append(users[:i], users[i+1:]...)
			c.JSON(http.StatusNoContent, gin.H{})
			return
		}
	}

	c.JSON(http.StatusNotFound, gin.H{"error": "User not found"})
}

// 冒泡排序
func bubbleSort(c *gin.Context) {
	//调用排序程序
	array := [...]int{2, 6, 1, 5, 9, 0, 12, 35} // 编译器会自动推断数组的长度
	slice := array[:]                           // 将数组转换为切片
	fmt.Println("排序前:", slice)
	algorithm.BubbleSort(slice) // 调用 algorithm 包中的 BubbleSort 方法
	fmt.Println("排序后:", slice)
	algorithm.PrintSlice(slice)

	c.JSON(http.StatusOK, slice)
	return
}
