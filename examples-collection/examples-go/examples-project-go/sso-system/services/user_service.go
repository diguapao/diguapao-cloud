package services

import (
	"database/sql"
	"errors"
	"fmt"
	"golang.org/x/crypto/bcrypt"
)

type User struct {
	ID       int    `json:"id"`
	Username string `json:"username"`
	Password string `json:"password"`
	Email    string `json:"email"`
	role     string `json:"role"`
}

type UserService struct {
	db *sql.DB
}

func NewUserService(db *sql.DB) *UserService {
	return &UserService{
		db: db,
	}
}

func (us *UserService) CreateUser(user *User) (int, error) {
	// 验证输入
	if err := validateUser(user); err != nil {
		return 0, fmt.Errorf("invalid user data: %w", err)
	}

	// 对密码进行哈希处理
	hashedPassword, err := bcrypt.GenerateFromPassword([]byte(user.Password), bcrypt.DefaultCost)
	if err != nil {
		return 0, fmt.Errorf("failed to hash password: %w", err)
	}

	query := `INSERT INTO users (username, email, password) VALUES ($1, $2, $3) RETURNING id`
	var userID int
	err = us.db.QueryRow(query, user.Username, user.Email, hashedPassword).Scan(&userID)
	if err != nil {
		return 0, fmt.Errorf("failed to create user: %w", err)
	}

	return userID, nil
}

func validateUser(user *User) error {
	if user.Username == "" {
		return fmt.Errorf("username cannot be empty")
	}
	if user.Email == "" {
		return fmt.Errorf("email cannot be empty")
	}
	return nil
}

// GetUserByID 根据 ID 获取用户
func (us *UserService) GetUserByID(id int) (*User, error) {
	query := `SELECT id, username, email FROM users WHERE id = $1`
	var user User
	row := us.db.QueryRow(query, id)
	err := row.Scan(&user.ID, &user.Username, &user.Email)
	if err == sql.ErrNoRows {
		return nil, errors.New("user not found")
	} else if err != nil {
		return nil, err
	}
	return &user, nil
}

// UpdateUser 更新用户信息
func (us *UserService) UpdateUser(user *User) error {
	query := `UPDATE users SET username = $1, email = $2 WHERE id = $3`
	result, err := us.db.Exec(query, user.Username, user.Email, user.ID)
	if err != nil {
		return fmt.Errorf("failed to update user: %w", err)
	}
	rowsAffected, err := result.RowsAffected()
	if rowsAffected == 0 {
		return errors.New("user not found or no changes made")
	}
	return nil
}

// DeleteUser 删除用户
func (us *UserService) DeleteUser(id int) error {
	query := `DELETE FROM users WHERE id = $1`
	result, err := us.db.Exec(query, id)
	if err != nil {
		return err
	}
	rowsAffected, err := result.RowsAffected()
	if rowsAffected == 0 {
		return errors.New("user not found")
	}
	return nil
}
