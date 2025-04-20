package com.githubprojects.reactiveSockets.tcp.async;

import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

import java.nio.charset.StandardCharsets;

public class AsyncServer {

    public void start(int port) {
        DisposableServer tcpServer = TcpServer.create()
                .host("127.0.0.1")
                .port(port)
                .handle((inbound, outbound) -> inbound.receive()
                        .flatMap(m -> Mono.just(m.toString(StandardCharsets.UTF_8)))
                        .doOnNext(message -> System.out.println("Message: " + message))
                        .then())
                .bindNow();

        tcpServer.onDispose()
                .block();
    }
}