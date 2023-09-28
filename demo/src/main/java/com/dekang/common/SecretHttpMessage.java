package com.dekang.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
//重新post请求输入流
public class SecretHttpMessage implements HttpInputMessage {

    private ByteArrayInputStream byteArrayInputStream;

    private HttpHeaders headers;

    public SecretHttpMessage(ByteArrayInputStream byteArrayInputStream, HttpHeaders headers) {
        this.byteArrayInputStream = byteArrayInputStream;
        this.headers = headers;
    }

    @Override
    public InputStream getBody() throws IOException {
        return byteArrayInputStream;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }
}
