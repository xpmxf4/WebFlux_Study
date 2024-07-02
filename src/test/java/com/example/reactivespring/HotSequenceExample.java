package com.example.reactivespring;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.HashMap;
import java.util.stream.Stream;

@Slf4j
public class HotSequenceExample {

    @Test
    void hotSeqExample01() throws InterruptedException {
        // given - 상황 만들기
        Flux<String> hotSequence = Flux.fromStream(Stream.of("singer 1", "singer 2", "singer 3", "singer 4", "singer 5"))
                .delayElements(Duration.ofSeconds(1))
                .share();

        // when - 동작
        hotSequence.subscribe(singer -> log.info("=============================================" + "Subscriber1 is watching {}", singer));

        Thread.sleep(2500);

        // then - 검증
        hotSequence.subscribe(singer -> log.info("=============================================" + "Subscriber2 is watching {}", singer));
        Thread.sleep(3000);
    }

    @Test
    void HotSequence는_테스트_메서드와_별도의_스레드에서_구독() throws InterruptedException {
        // given - 상황 만들기
        HashMap<String, String> threadNames = new HashMap<>();

        Flux<String> hotSequence = Flux.fromStream(Stream.of("singer 1", "singer 2", "singer 3", "singer 4", "singer 5"))
                .delayElements(Duration.ofSeconds(1))
                .share();

        String testMethodThread = Thread.currentThread().getName();
        threadNames.put("current Thread", testMethodThread);

        log.info("test method's thread name : {}", testMethodThread);

        // when - 동작
        hotSequence.subscribe((singer) -> {
            log.info("Thread.currentThread().getName() = " + Thread.currentThread().getName());
            Assertions.assertNotEquals(Thread.currentThread().getName(), testMethodThread);
        });
        Thread.sleep(2500);

        hotSequence.subscribe((singer) -> {
            log.info("Thread.currentThread().getName() = " + Thread.currentThread().getName());
            Assertions.assertNotEquals(Thread.currentThread().getName(), testMethodThread);
        });
        Thread.sleep(3000);

        // then - 검증
    }
}
