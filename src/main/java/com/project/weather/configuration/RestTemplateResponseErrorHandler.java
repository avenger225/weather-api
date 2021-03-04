package com.project.weather.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return httpResponse.getStatusCode().series() == CLIENT_ERROR || httpResponse.getStatusCode().series() == SERVER_ERROR;
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        switch (httpResponse.getStatusCode().series()) {
            case SERVER_ERROR:
                throw new HttpServerErrorException("Server side error while connecting to Weather API Server: " + responseBodyToString(httpResponse.getBody()), HttpStatus.INTERNAL_SERVER_ERROR, httpResponse.getStatusText(), null, null, null);
            case CLIENT_ERROR:
                throw new HttpClientErrorException("Client side error while connecting to Weather API Server: " + responseBodyToString(httpResponse.getBody()), HttpStatus.BAD_REQUEST, httpResponse.getStatusText(), null, null, null);
        }
    }

    private String responseBodyToString(InputStream inputStream) {
        return new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines()
                .collect(Collectors.joining("\n"));
    }
}