package com.example.springwebclientexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;



@RestController
public class Controller {

    @Autowired
    private WebClient webClient;

    @GetMapping("/get")
    public ResponseObj getDetails(){
        return webClient.get()
                .uri("http://httpbin.org/get")
                .retrieve()
                .bodyToMono(ResponseObj.class)
                .block();
    }
    @PostMapping("/post")
    public ResponseObj postDetails(@RequestBody Employee employee){
        return webClient.post()
                .uri("http://httpbin.org/post")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(employee)
                .retrieve()
                .bodyToMono(ResponseObj.class)
                .block();

    }
    @PutMapping("/put")
    public ResponseObj putDetails(@RequestBody Employee employee) {
        return webClient.put()
                .uri("http://httpbin.org/put")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(employee)
                .retrieve()
                .bodyToMono(ResponseObj.class)
                .block();
    }
    @DeleteMapping("/delete")
    public ResponseObj deleteDetails(){
        return webClient.delete()
                .uri("http://httpbin.org/delete")
                .retrieve()
                .bodyToMono(ResponseObj.class)
                .block();
    }


}
