package com.example.reactivespring;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;

@Slf4j
public class MonoExample {

    @Test
    void MonoExample01() {
        // upstream
        Mono.just("Hello Reactor")
                // downstream
                .subscribe(data -> System.out.println(data));
    }

    @Test
    void MonoExample02() {
        Mono.empty().subscribe(data -> log.info("$ emitted data : {} " + data), error -> {
        }, () -> log.info("# emitted onComplete signal"));
    }

    @Test
    void MonoExample03() {
        // given - 상황 만들기
        URI worldTimeUri = UriComponentsBuilder.newInstance().scheme("http").host("worldtimeapi.org").port(80).path("/api/timezone/Asia/Seoul").build().encode().toUri();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Mono.just(
                restTemplate.exchange(worldTimeUri, HttpMethod.GET, new HttpEntity<>(headers), String.class)
        )
                .map(response -> {
                    DocumentContext jsonContext = JsonPath.parse(response.getBody());
                    String  dateTime = jsonContext.read("$.datetime");
                    return dateTime;
                })
                .subscribe(
                        data -> log.info("# emitted data : " + data),
                        error -> log.error(error.getMessage()),
                        () -> log.info("# emitted onComplete signal")
                );
    }
}
