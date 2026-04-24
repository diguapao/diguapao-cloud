package org.diguapao.netty.im.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * IM 服务端端
 *
 * @author lipiao
 * @version 2026.04.24
 * @since 2026/4/24 14:39
 */
@Slf4j
public class ImServer {

    private final int port;

    public ImServer(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {
        // 1. 创建 BossGroup 和 WorkerGroup (Reactor 模型)
        // BossGroup 只负责接受连接，通常只需要 1 个线程
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // WorkerGroup 负责 IO 读写和业务，默认 CPU核数*2
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            // 2. 服务器启动引导类
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    // 使用 NIO 模式
                    .channel(NioServerSocketChannel.class)
                    // 设置全连接队列大小
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 开启 TCP 保活
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    // 禁用 Nagle 算法，提高实时性
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ImClient.fillChannelPipeline(ch);

                            // === 核心组件协作流程开始 ===

                            // 心跳检测：如果 5秒内没有读到数据，触发 UserEventTriggered
                            // writeIdleTime=0 表示不监控写空闲
                            pipeline.addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));

                            // 自定义业务处理器
                            pipeline.addLast(new ServerBusinessHandler());

                            // === 核心组件协作流程结束 ===
                        }
                    });

            // 3. 绑定端口并同步等待
            ChannelFuture future = bootstrap.bind(port).sync();
            log.info("🚀 Netty IM Server 启动成功，监听端口: " + port);

            // 4. 等待服务端口关闭
            future.channel().closeFuture().sync();

        } finally {
            // 5. 优雅停机
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ImServer(5050).start();
    }

}