package com.githubprojects.reactiveSockets.async;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.netty.Connection;
import reactor.netty.tcp.TcpClient;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class AsyncClient {

    public void start(int port) {
        Connection connection =
                TcpClient.create()
                        .host("127.0.0.1")
                        .port(port)
                        .handle((inbound, outbound) -> {
                            Flux<ByteBuf> threads = Flux.range(1, 5)
                                    .flatMap(thread -> {
                                        return Flux.interval(Duration.ofSeconds(1))
                                                .subscribeOn(Schedulers.newSingle("thread-" + thread))
                                                .map(m -> {
                                                    String message = "Thread-" + thread + ": " + m+"\n";
                                                    return Unpooled.copiedBuffer(message, StandardCharsets.UTF_8);
                                                });
                                    });
                            return outbound.send(threads);
                        })
                        .connectNow();

        connection.onDispose().block();
    }
}
