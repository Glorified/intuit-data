package com.intuit.developer.helloworld;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean(name = "adapterWSTemplate")
    public RestTemplate adapterWSTemplate(RestTemplateBuilder builder) {
    /*	int timeout = 5000;
        RequestConfig config = RequestConfig.custom()
          .setConnectTimeout(timeout)
          .setConnectionRequestTimeout(timeout)
          .setSocketTimeout(timeout)
          .build();
        CloseableHttpClient client = HttpClientBuilder
          .create()
          .setDefaultRequestConfig(config)
          .build();
        return new RestTemplate(new ClientHttpRequestFactory());*/
    	
        return builder.rootUri("http://localhost:8081/").build();
    }
}