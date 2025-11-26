# WebSocket 篇

## The remote endpoint was in state [TEXT_FULL_WRITING] which is an invalid state for called method

### 日志采样

```text

2025-02-26 14:55:49.877 DEBUG 1716 --- [o-18803-exec-10] org.apache.tomcat.websocket.WsSession    173: Created WebSocket session [4]
2025-02-26 14:55:49.878  INFO 1716 --- [o-18803-exec-10] c.c.s.s.w.e.VehicleDomainWsEndpoint      91: Web Stock 建立连接, userId=, gardenId=, 会话数=4, 在线数连接数=1
2025-02-26 14:55:49.878 DEBUG 1716 --- [o-18803-exec-10] o.a.t.websocket.server.WsFrameServer     173: wsFrameServer.onDataAvailable
2025-02-26 14:55:49.977 DEBUG 1716 --- [io-18803-exec-6] o.a.t.websocket.server.WsFrameServer     173: wsFrameServer.onDataAvailable
2025-02-26 14:55:49.977 DEBUG 1716 --- [io-18803-exec-6] o.a.t.websocket.server.WsFrameServer     173: Read [31] bytes into input buffer ready for processing
2025-02-26 14:55:49.977 DEBUG 1716 --- [io-18803-exec-6] o.a.t.websocket.server.WsFrameServer     173: WebSocket frame received. fin [true], rsv [0], OpCode [1], payload length [25]
2025-02-26 14:55:50.005  INFO 1716 --- [io-18803-exec-6] c.c.s.s.w.e.VehicleDomainWsEndpoint      141: 切换园区成功, userId=2, 会话数=4, 在线数连接数=1
2025-02-26 14:55:50.008 DEBUG 1716 --- [io-18803-exec-5] o.a.t.websocket.server.WsFrameServer     173: wsFrameServer.onDataAvailable
2025-02-26 14:55:50.010 DEBUG 1716 --- [io-18803-exec-5] o.a.t.websocket.server.WsFrameServer     173: Read [8] bytes into input buffer ready for processing
2025-02-26 14:55:50.010 DEBUG 1716 --- [io-18803-exec-5] o.a.t.websocket.server.WsFrameServer     173: WebSocket frame received. fin [true], rsv [0], OpCode [8], payload length [2]
2025-02-26 14:55:50.010 DEBUG 1716 --- [io-18803-exec-5] org.apache.tomcat.websocket.WsSession    173: Close message with reason [CloseReason: code [1002], reason [null]] received for session [4] in state [OPEN]
2025-02-26 14:55:50.011 DEBUG 1716 --- [io-18803-exec-5] org.apache.tomcat.websocket.WsSession    175: Failed to send close message for session [4] to remote endpoint

java.io.IOException: java.io.IOException: 你的主机中的软件中止了一个已建立的连接。

```

### 日志分析

日志来看，WebSocket连接在建立后不久被关闭的主要原因与一个java.io.IOException有关，具体表现为“你的主机中的软件中止了一个已建立的连接”。以下是分析和可能的原因：

WebSocket连接建立：首先，一个新的WebSocket会话被创建（Created WebSocket session [4]），并记录了相应的用户ID、gardenId等信息。

数据交换：紧接着，服务器接收到了客户端发送的数据，并成功处理了一些操作（如“切换园区成功”）。

关闭帧接收：之后，服务器收到了一个关闭帧（OpCode [8], payload length [2]），表明客户端或服务器决定关闭连接。关闭代码为1002，这是一个表示协议错误的关闭码。

尝试发送关闭确认失败：服务器尝试向客户端发送关闭确认消息但失败了，这是因为试图写入到已经关闭或者正在关闭的连接上导致的异常。

异常堆栈跟踪：最后的日志显示，在尝试发送关闭消息时发生了IOException，这进一步证实了连接已被中断的事实。

可能的原因：

客户端主动关闭连接：可能是客户端出于某种原因（例如用户行为、客户端逻辑错误或网络问题）主动发起了关闭请求。

网络问题：中间可能存在网络不稳定的情况，导致连接意外中断。

服务端资源限制：如果服务器资源受限（如内存不足、文件描述符限制等），可能会强制关闭一些连接。

协议不匹配：尽管不太可能，但如果客户端和服务端之间的WebSocket子协议或其他配置不兼容，也可能导致这种类型的错误。

解决建议：

检查客户端逻辑：确保客户端没有无故发起关闭请求，并且正确处理所有可能的异常情况。

增强错误处理：在服务端增加对这类异常的捕获和处理机制，比如在遇到IOException时采取适当的恢复措施。

监控网络状态：使用工具监控网络状况，确保网络连接稳定，减少由于网络波动引起的连接中断。

调整资源限制：根据实际需求适当调整服务器上的资源限制，确保能够支持预期的并发连接数。

详细日志记录：增加更多详细的日志记录，尤其是在连接建立和关闭的地方，以便更好地理解何时何地发生了什么。这样可以帮助更准确地定位问题所在。

### 解决问题

出现这个问题是由于服务端推送的消息长度过大，由于 ws 连接是经过网关转发到后端服务的，若需要支持较大的报文长度，可再网关增加配置：

```yaml

# 配置WebSocket消息的最大帧长度（即允许的最大报文大小），不建议设置过大，4194304字节=4M，65536字节=64kb。
# 调整这些参数时要考虑到安全性，因为过大的报文尺寸可能会导致拒绝服务攻击（DoS）。此外，根据实际业务需求合理设置这些值非常重要，避免不必要的资源浪费。
spring.cloud.gateway.httpclient.websocket.max-frame-payload-length: 10065536

```
