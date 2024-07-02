package com.example.reactivespring;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class HelloReactor {
    public static void main(String[] args) {
        log.info("Hello World");
        Mono.just("Hello Reactor.")
                .subscribe(message -> System.out.println(message));
    }
}
