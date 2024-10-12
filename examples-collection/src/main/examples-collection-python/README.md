Python 采用 FastAPI 构建一个简单的 RESTful 微服务。

### 1. 准备环境

PyCharm 2024.2.2 和 Python 3.x

### 2. 创建一个新的 Python 项目

1. **打开 PyCharm**：
   - 启动 PyCharm IDE。

2. **创建新项目**：
   - 在欢迎界面，选择 "Create New Project"。
   - 或者，如果你已经打开了一个项目，可以通过菜单 `File` -> `New Project` 来创建新项目。

3. **选择项目位置和模板**：
   - 选择项目的保存位置。
   - 选择 `Pure Python` 模板。
   - 点击 `Create`。

### 3. 配置虚拟环境（可选但推荐）

1. **创建虚拟环境**：
   - 在项目设置中，选择 `File` -> `Settings`（或 `Preferences` on macOS）。
   - 导航到 `Project: <your_project_name>` -> `Python Interpreter`。
   - 点击齿轮图标，选择 `Add...`。
   - 选择 `Virtualenv Environment`，然后点击 `OK`。
   - 选择 `New environment` 并指定虚拟环境的位置，然后点击 `OK`。

### 4. 安装 FastAPI 和 Uvicorn

1. **打开终端**：
   - 打开 PyCharm 内置的终端：`View` -> `Tool Windows` -> `Terminal`。

2. **安装 FastAPI 和 Uvicorn**：
   - 运行以下命令来安装 FastAPI 和 Uvicorn：

     ```sh
     pip install fastapi uvicorn
     ```

### 5. 创建主文件

1. **创建 `main.py` 文件**：
   - 在项目根目录下右键点击，选择 `New` -> `Python File`。
   - 输入文件名 `main.py`，然后按 Enter 键。

2. **编写代码**：
   - 在 `main.py` 文件中编写以下代码，创建一个简单的 RESTful 微服务：

     ```python
     import subprocess
     from typing import List
     
     from fastapi import FastAPI, HTTPException
     from pydantic import BaseModel
     
     
     # 定义用户模型
     class User(BaseModel):
         id: int
         name: str
         email: str
     
     
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
     
     
     # 在 if __name__ == "__main__": 块中调用 uvicorn.run 函数，并传入 app、host 和 port 参数。
     # reload=True 参数用于在开发过程中自动重新加载服务器，这样当你修改代码时，服务器会自动重启。
     if __name__ == "__main__":
         # 使用 subprocess 调用 uvicorn 命令
         subprocess.run(["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "28181", "--reload"])
     
     ```

### 6. 运行项目

1. **配置运行/调试配置**：
   - 点击顶部工具栏中的 `Add Configuration` 图标（看起来像一个加号）。
   - 选择 `Python`。
   - 填写配置信息，例如：
     - **Name**: 为配置起一个名字（例如 `FastAPI`）。
     - **Script path**: 选择 `main.py` 文件路径。
     - **Parameters**: 添加 `--reload` 参数以便在代码更改时自动重新加载服务器。
     - **Environment variables**: 添加 `PYTHONPATH` 变量，值为你的项目根目录（如果需要）。
   - 点击 `OK` 保存配置。

2. **运行配置**：
   - 从下拉菜单中选择你刚刚创建的配置（例如 `FastAPI`）。
   - 点击绿色的运行按钮（三角形图标）来运行程序。

3. **查看输出**：
   - 输出将显示在底部的 `Run` 窗口中。你应该看到类似如下的输出：

     ```sh
     INFO:     Uvicorn running on http://127.0.0.1:28181 (Press CTRL+C to quit)
     INFO:     Started reloader process [12345] using StatReload
     INFO:     Started server process [12346]
     INFO:     Waiting for application startup.
     INFO:     Application startup complete.
     ```

### 7. 测试 API

1. **使用浏览器或 Postman 测试 API**：
   - 你可以使用 Postman、cURL 或浏览器来测试你的 API。
   - 例如，获取所有用户的请求：

     ```sh
     curl http://127.0.0.1:28181/users
     ```

   - 你应该会看到如下响应：

     ```json
     [
       {
         "id": 1,
         "name": "John Doe",
         "email": "john@example.com"
       },
       {
         "id": 2,
         "name": "Jane Doe",
         "email": "jane@example.com"
       }
     ]
     ```

通过以上步骤，你应该能够在 PyCharm 2024.2.2 中成功创建并运行一个简单的 RESTful 微服务。你可以根据需要扩展这个示例，添加更多的功能和处理逻辑。如果你遇到任何问题，请随时告诉我！