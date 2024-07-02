package com.example.reactivespring;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.stream.Stream;

@Slf4j
public class ColdSequenceExample {

    @Test
    void ColdSeqEx01() {
        // given - 상황 만들기
        Flux<String> coldFlux = Flux.fromIterable(Arrays.asList("RED", "YELLOW", "PINK"));

        // when - 동작
        Flux<String> map = coldFlux.map(String::toUpperCase);

        // then - 검증
        map.subscribe(data -> log.info("{}", data));
    }

    @Test
    void ColdSequence는_테스트_메서드와_같은_스레드() {
        // given - 상황 만들기
        String testMethodThreadName = Thread.currentThread().getName();
        Flux<String> coldFlux = Flux.fromStream(Stream.of("RED", "YELLOW", "PINK"));

        // when - 동작
        Flux<String> map = coldFlux.map(color -> color.toLowerCase());

        // then - 검증
        map.subscribe(data -> {
            Assertions.assertEquals(testMethodThreadName, Thread.currentThread().getName());
            log.info("{}", data);
        });
    }
}
