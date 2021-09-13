# official-website
基于springboot websocket聊天系统

------



## 功能列表
- 管理员登陆登出
- 管理员登陆之后和游客之间的在线会话
- 管理员查询留言记录
- 管理员查询在线聊天记录
- 游客留言
- 其他功能正在陆续增加中......

## 接口说明

### 管理员密码生成方式

> - 本系统只有一个管理员，故写到配置文件里
>
> - 执行java类com.ow.utils.EncryptUtil#main
>
> - 填写明文密码123456：System.out.println(cipherText("123456"));
>
> - 生成的密文填写到application.properties文件login.manage.password=密文


### 管理员登陆接口

> ```apl
> POST http://localhost:8080//manage/login
> # 请求头信息
> Content-Type: application/json
> # 请求包体
> {
>   "name": "admin",
>   "password": "123456"
> }
> 
> # 返回信息
> {
>   "status": "ok",
>   "message": null,
>   "data": {
>     "token": "0fe47802d1624bdca179612793411efd"
>   }
> }
> ```

### 管理员登出接口

> ```apl
> POST http://localhost:8080//manage/logout
> # 请求头信息
> Content-Type: application/json
> token: 0fe47802d1624bdca179612793411efd
> 
> # 返回信息
> {
>   "status": "ok",
>   "message": null,
>   "data": null
> }
> ```

### 管理员查询留言记录

> ```apl
> POST http://localhost:8080/manage/leave/message/query
> # 请求头信息
> Content-Type: application/json
> token: 1fe94dc1c0df47db8c8c2dc7fa73a673
> 
> # 请求包体
> {
>   "sessionId": "",
>   "phone": "",
>   "content": "",
>   "state": 1,
>   "createTimeStart": "2021-09-12 04:59:06",
>   "createTimeEnd": "2021-09-12 04:59:22",
>   "updateTimeStart": "2021-09-12 04:59:06",
>   "updateTimeEnd": "2021-09-12 04:59:22"
> }
> 
> # 返回信息
> [
>   {
>     "id": 3,
>     "sessionId": "tgfdhry",
>     "phone": "56yuhgf",
>     "content": "677utt",
>     "state": 1,
>     "createTime": "2021-09-12 04:59:20",
>     "updateTime": "2021-09-12 04:59:20"
>   },
>   {
>     "id": 2,
>     "sessionId": "54645",
>     "phone": "5367",
>     "content": "9hgfhfgh",
>     "state": 1,
>     "createTime": "2021-09-12 04:59:09",
>     "updateTime": "2021-09-12 04:59:09"
>   }
> ]
> ```

### 管理员查询在线聊天记录

> ```apl
> POST http://localhost:8080/manage/chat/message/query
> 
> # 请求头信息
> Content-Type: application/json
> token: 1fe94dc1c0df47db8c8c2dc7fa73a673
> 
> # 请求包体
> {
>   "sessionId": "43543543",
>   "content": "",
>   "createTimeStart": "2021-09-11 08:15:27",
>   "createTimeEnd": "2021-09-11 09:17:01",
>   "updateTimeStart": "2021-09-11 08:15:27",
>   "updateTimeEnd": "2021-09-11 09:17:01"
> }
> 
> # 返回信息
> [
>   {
>     "id": 2,
>     "sessionId": "43543543",
>     "content": "weqedgfretr",
>     "createTime": "2021-09-11 08:15:27",
>     "updateTime": "2021-09-11 08:16:27"
>   },{
>     "id": 1,
>     "sessionId": "4544343",
>     "content": "uty45433",
>     "createTime": "2021-09-11 08:15:27",
>     "updateTime": "2021-09-11 08:16:27"
>   }
> ]
> ```



### 游客留言

> ```apl
> POST http://localhost:8080/user/leave/message
> # 请求头信息
> Content-Type: application/json
> 
> # 请求包体
> {
>   "sessionId": "123321",
>   "phone": "12345678909",
>   "content": "content test!",
>   "state": 1
> }
> 
> # 返回信息
> true
> ```



### 管理员登陆之后和游客的在线会话

> ```apl
> ws://127.0.0.1:8080/chat/message/push/0fe47802d1624bdca179612793411efd/5
> 
> #地址解析说明
> # ws协议。如果有ssl证书，可以改为wss协议
> # 127.0.0.1:8080 表示IP+端口
> # chat/message/push固定不变
> # 0fe47802d1624bdca179612793411efd 代表管理员登陆的token
> # 5 代表游客端客户端ID值
> ```

### 游客和管理员的在线会话

> ```apl
> ws://localhost:8080/chat/message/push/2021/1
> 
> #地址解析说明
> # ws协议。如果有ssl证书，可以改为wss协议
> # 127.0.0.1:8080 表示IP+端口
> # chat/message/push固定不变
> # 2021 代表游客登陆(任意数字类型均可)
> # 1 代表管理端客户端ID值
> ```
>
> 
