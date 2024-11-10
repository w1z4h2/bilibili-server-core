package com.bilibili.filter;

import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StopWatch;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;

@Component
public class LogFilter implements GlobalFilter, Ordered {
    private static final String DARK_BLUE = "\u001B[34;1m";
    private static final String BRIGHT_GREEN = "\u001B[32;1m";
    private static final String RESET = "\u001B[0m";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        logRequestDetails(request);

        return DataBufferUtils.join(request.getBody())
                .flatMap(dataBuffer -> {
                    byte[] content = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(content);
                    String requestBody = new String(content, StandardCharsets.UTF_8);
                    System.out.println("Body: " + requestBody);
                    System.out.println(DARK_BLUE + "\n=========================================Request End===========================================" + RESET + "\n");

                    ServerHttpRequestDecorator decoratedRequest = new ServerHttpRequestDecorator(request) {
                        @Override
                        public Flux<DataBuffer> getBody() {
                            DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
                            return Flux.just(bufferFactory.wrap(content));
                        }
                    };

                    ServerHttpResponse response = exchange.getResponse();
                    ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(response) {
                        @Override
                        public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                            return DataBufferUtils.join(body)
                                    .flatMap(dataBuffer -> {
                                        byte[] responseContent = new byte[dataBuffer.readableByteCount()];
                                        dataBuffer.read(responseContent);
                                        String responseBody = new String(responseContent, StandardCharsets.UTF_8);

                                        printResponseDetails(response, responseBody);

                                        DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
                                        DataBuffer responseDataBuffer = bufferFactory.wrap(responseContent);

                                        return super.writeWith(Flux.just(responseDataBuffer));
                                    });
                        }
                    };

                    return chain.filter(exchange.mutate().request(decoratedRequest).response(decoratedResponse).build())
                            .doFinally(signalType -> {
                                stopWatch.stop();
                                System.out.println("Response Time: " + stopWatch.getTotalTimeMillis() + " ms");
                            });
                });
    }

    private void logRequestDetails(ServerHttpRequest request) {
        System.out.println(DARK_BLUE + "\n=========================================Request Start=========================================" + RESET + "\n");
        System.out.println("URI: " + request.getURI());
        System.out.println("Path: " + request.getPath());
        System.out.println("Method: " + request.getMethod());
        System.out.println("Method Value: " + request.getMethodValue());
        System.out.println("Remote Address: " + request.getRemoteAddress());
        System.out.println("Local Address: " + request.getLocalAddress());
        System.out.println("ID: " + request.getId());

        SslInfo sslInfo = request.getSslInfo();
        System.out.println("SSL Info: ");
        if (sslInfo != null) {
            System.out.println("    Session ID: " + sslInfo.getSessionId());
            X509Certificate[] peerCertificates = sslInfo.getPeerCertificates();
            System.out.println("    Peer Certificates: ");
            for (X509Certificate certificate : peerCertificates) {
                System.out.println("        Subject: " + certificate.getSubjectDN());
                System.out.println("        Issuer: " + certificate.getIssuerDN());
                System.out.println("        Serial Number: " + certificate.getSerialNumber());
                System.out.println("        Valid From: " + certificate.getNotBefore());
                System.out.println("        Valid Until: " + certificate.getNotAfter());
            }
        }

        MultiValueMap<String, HttpCookie> cookies = request.getCookies();
        System.out.println("Cookies: ");
        if (!cookies.isEmpty()) {
            request.getCookies().forEach((key, value) -> {
                System.out.println("    " + key + " = " + value);
            });
        }

        MultiValueMap<String, String> queryParams = request.getQueryParams();
        System.out.println("Query Params: ");
        if (!queryParams.isEmpty()) {
            request.getQueryParams().forEach((key, value) -> {
                System.out.println("    " + key + " = " + value);
            });
        }

        HttpHeaders headers = request.getHeaders();
        System.out.println("Headers: ");
        if (!headers.isEmpty()) {
            request.getHeaders().forEach((key, value) -> {
                System.out.println("    " + key + " : " + value);
            });
        }
    }

    private void printResponseDetails(ServerHttpResponse response, String responseBody) {
        System.out.println(BRIGHT_GREEN + "\n=======================================Response Start==========================================" + RESET + "\n");
        HttpStatus statusCode = response.getStatusCode();
        System.out.println("Status Code: " + (statusCode != null ? statusCode : "UNKNOWN"));
        Integer rawStatusCode = response.getRawStatusCode();
        System.out.println("Raw Status Code: " + (rawStatusCode != null ? rawStatusCode : "UNKNOWN"));

        HttpHeaders headers = response.getHeaders();
        System.out.println("Headers: ");
        if (!headers.isEmpty()) {
            headers.forEach((key, value) -> {
                System.out.println("    " + key + " : " + value);
            });
        }

        MultiValueMap<String, ResponseCookie> cookies = response.getCookies();
        System.out.println("Cookies: ");
        if (!cookies.isEmpty()) {
            cookies.forEach((key, value) -> {
                System.out.println("    " + key + " : " + value);
            });
        }

        System.out.println("Body: " + responseBody);
        System.out.println(BRIGHT_GREEN + "\n=======================================Response End============================================" + RESET + "\n");
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
