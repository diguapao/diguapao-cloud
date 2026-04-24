package org.diguapao.netty.im.demo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户端业务处理器
 *
 * @author lipiao
 * @version 2026.04.24
 * @since 2026/4/24 14:39
 */
@Slf4j
public class ClientBusinessHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("✅ [客户端] 收到服务端响应: " + msg);
        // 收到响应后关闭客户端，结束程序
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}