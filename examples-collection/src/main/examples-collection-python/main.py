import subprocess
from typing import List

from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from src.algorithm.algorithmSort import bubble_sort  # 导入 bubble_sort 函数


# 定义用户模型
class User(BaseModel):
    id: int
    name: str
    email: str


# 定义响应模型
class SortedArrayResponse(BaseModel):
    detail: List[int]


# 模拟数据库
users_db = [
    User(id=1, name="John Doe", email="john@example.com"),
    User(id=2, name="Jane Doe", email="jane@example.com"),
]

app = FastAPI()


@app.get("/users", response_model=List[User])
def get_users():
    return users_db


@app.get("/users/{user_id}", response_model=User)
def get_user(user_id: int):
    for user in users_db:
        if user.id == user_id:
            return user
    raise HTTPException(status_code=404, detail="User not found")


@app.post("/users", response_model=User)
def create_user(user: User):
    users_db.append(user)
    return user


@app.put("/users/{user_id}", response_model=User)
def update_user(user_id: int, updated_user: User):
    for i, user in enumerate(users_db):
        if user.id == user_id:
            users_db[i] = updated_user
            return updated_user
    raise HTTPException(status_code=404, detail="User not found")


@app.delete("/users/{user_id}")
def delete_user(user_id: int):
    for i, user in enumerate(users_db):
        if user.id == user_id:
            del users_db[i]
            return {"detail": "User deleted"}
    raise HTTPException(status_code=404, detail="User not found")


@app.post("/algorithm/bubbleSort", response_model=SortedArrayResponse)
def algorithm_bubbleSort():
    # 创建一个整型数组（列表）
    int_array = [2, 6, 1, 5, 9, 0, 12, 35]

    # 对数组进行冒泡排序
    bubble_sort(int_array)

    return {"detail": int_array}


# 在 if __name__ == "__main__": 块中调用 uvicorn.run 函数，并传入 app、host 和 port 参数。
# reload=True 参数用于在开发过程中自动重新加载服务器，这样当你修改代码时，服务器会自动重启。
if __name__ == "__main__":
    # 使用 subprocess 调用 uvicorn 命令
    subprocess.run(["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "28181", "--reload"])
