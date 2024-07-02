package com.example.reactivespring;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public class FluxExample {

    @Test
    void FluxExample01() {
        Flux.just(6, 9, 13)
                .map(num -> num % 2)
                .subscribe(remainder -> log.info("# remainder: {}", remainder));
    }

    @Test
    void FluxExample02() {
        Flux.fromArray(new Integer[]{3, 6, 7, 9})
                .filter(num -> num > 6)
                .map(num -> num * 2)
                .subscribe(multiply -> log.info("# multiply: {}", multiply));
    }

    @Test
    void FluxExample03() {
        Flux<Object> flux = Mono.justOrEmpty(null)
                .concatWith(Mono.justOrEmpty("Jobs"));

        flux.subscribe(data -> log.info("# result: {}", data));
    }

    @Test
    void FluxExample04() {
        Flux.concat(
                        Flux.just("Venus"),
                        Flux.just("Earth"),
                        Flux.just("Mars")
                )
//                .collectList()
                .subscribe(planetList -> log.info("# planetList: {}", planetList));
    }
}
