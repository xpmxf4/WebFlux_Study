package com.example.reactivespring;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

@Slf4j
public class Class02ExampleTest {

    @Test
    void testFlux() {
        // given - 1. Publisher 가 data 를 생성함
        Flux<Integer> sequence = Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16);

        // when - 2. 생성된 data 를 Operator 를 사용해 가공,
        // 3. 최종적으로 Subscriber 에게 건넨다!
        sequence
                .map(data -> data * 2)
                .subscribe(data -> log.info("{}", data));
    }
}
