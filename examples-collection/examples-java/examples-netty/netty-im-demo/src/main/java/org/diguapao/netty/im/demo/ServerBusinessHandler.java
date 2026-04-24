package org.diguapao.netty.im.demo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * 服务端业务处理器
 *
 * @author lipiao
 * @version 2026.04.24
 * @since 2026/4/24 14:39
 */
@Slf4j
public class ServerBusinessHandler extends SimpleChannelInboundHandler<Object> {
    /**
     * 处理客户端发来的消息
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("📩 [服务端] 收到消息: " + msg + " | 来自: " + ctx.channel().remoteAddress());

        // 模拟业务逻辑处理耗时
        // Thread.sleep(100); 

        // 构造响应消息
        String response = "收到你的消息：" + msg;

        // 回写给客户端 (Outbound)
        // 注意：这里只是写入缓冲区，flush 会在方法结束后由 Netty 自动触发或手动触发
        ctx.writeAndFlush(response);
    }

    /**
     * 处理用户自定义事件（用于心跳检测）
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                System.out.println("⚠[服务端] 检测到读空闲，准备断开连接: " + ctx.channel().remoteAddress());
                // 实际项目中，这里通常会先发送一个 PING 包，而不是直接断开
                ctx.close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("⚠服务端异常", cause);
        ctx.close();
    }

}