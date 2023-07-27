package com.example.springwebclientexample;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.JettyClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import java.net.URI;

@Configuration
public class WebClientConfig {
    /**
     * To get our enhance method executed during the invocation of our WebClient,
     * we're going to create our own HttpClient and use it in place of the default JettyClientHttpConnector.
     */

    @Bean
    public WebClient getWebClient() {
        return WebClient.builder()
                .clientConnector(new JettyClientHttpConnector(getHttpClient()))
                .build();
    }

    private HttpClient getHttpClient(){
        return new HttpClient() {
            @Override
            public Request newRequest(URI uri) {
                Request request = super.newRequest(uri);
                return new ExternalClientLogger().enhance(request);
            }
        };
    }

}






