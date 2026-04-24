package org.diguapao.netty.im.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * IM 客户端
 *
 * @author lipiao
 * @version 2026.04.24
 * @since 2026/4/24 14:39
 */
@Slf4j
public class ImClient {

    private final String host;
    private final int port;

    public ImClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = fillChannelPipeline(ch);
                            pipeline.addLast(new ClientBusinessHandler());
                        }
                    });

            // 连接服务端
            ChannelFuture future = bootstrap.connect(host, port).sync();
            log.info("🔗 [客户端] 已连接到服务器");

            // 模拟发送消息
            // 这里的 writeAndFlush 会触发 Outbound 流程
            future.channel().writeAndFlush("Hello Netty! 这是一个测试消息。");

            // 保持客户端存活，等待服务端响应
            future.channel().closeFuture().sync();

        } finally {
            group.shutdownGracefully();
        }
    }

    public static ChannelPipeline fillChannelPipeline(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();

        // 添加 LengthFieldPrepender
        // 作用：在发送 "Hello" 之前，自动在前面加上 4 个字节的长度值
        // 参数：长度字段占用的字节数
        pipeline.addLast(new io.netty.handler.codec.LengthFieldPrepender(4));

        // 解决粘包/拆包：限制最大帧长度为 1MB，长度字段偏移0，长度占4字节。服务端、客户端都需要同样的编解码器来对应
        pipeline.addLast(new LengthFieldBasedFrameDecoder(1048576, 0, 4, 0, 0));

        // 编解码器：将 ByteBuf 转为 String (简化演示)
        pipeline.addLast(new StringDecoder());
        pipeline.addLast(new StringEncoder());

        return pipeline;
    }

    public static void main(String[] args) throws InterruptedException {
        int imServerPort = 5050;
        String imServerHost = "127.0.0.1";
        new ImClient(imServerHost, imServerPort).connect();
    }
}