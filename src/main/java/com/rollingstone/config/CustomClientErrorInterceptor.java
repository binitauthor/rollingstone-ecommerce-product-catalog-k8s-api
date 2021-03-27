package com.rollingstone.config;

import com.rollingstone.exceptions.HTTP404Exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class CustomClientErrorInterceptor implements ResponseErrorHandler {

    final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean hasError (ClientHttpResponse clientHttpResponse) throws IOException {
        return clientHttpResponse.getStatusCode().is4xxClientError();
    }

    @Override
    public void handleError (ClientHttpResponse clientHttpResponse) throws IOException {
        log.error("CustomClientErrorHandler | HTTP Status Code: " + clientHttpResponse.getStatusCode().value());
        throw new HTTP404Exception(("Resource Not Found"));
    }
}
