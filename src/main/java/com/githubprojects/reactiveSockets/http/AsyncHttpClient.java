package com.githubprojects.reactiveSockets.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.githubprojects.reactiveSockets.auth.OwnJwt;
import io.netty.handler.codec.http.HttpHeaderNames;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


public class AsyncHttpClient {

    public void start(int port) throws NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        HttpClient httpClient =
                HttpClient.create()
                        .port(port)
                        .host("127.0.0.1");

        httpClient = provideJwtHeader(httpClient);

        httpClient.post()
                .uri("/")
                .send(ByteBufFlux.fromString(Mono.just("hi!")))
                .response()
                .block();

    }

    public HttpClient provideJwtHeader(HttpClient httpClient) throws NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        Map<String, Object> claims = new HashMap<>();
        Map<Object, Object> payloadClaims = new HashMap<>();
        payloadClaims.put("test", "test");
        payloadClaims.put("another-test", "test");
        String secret = "signature-for-my-own-jwt-token";

        OwnJwt ownJwt = OwnJwt.builder().header(claims).payload(payloadClaims).build();
        String jwtToken = ownJwt.buildJwt(secret);

        return httpClient.headers(h -> h.set(HttpHeaderNames.AUTHORIZATION, jwtToken));

    }
}
