package com.example.reactivespring;

import reactor.core.publisher.Mono;

public class HelloReactor {
    public static void main(String[] args) {
        Mono.just("Hello Reactor.")
                .subscribe(message -> System.out.println(message));
    }
}
