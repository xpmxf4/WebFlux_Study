package com.example.reactivespring;

import com.example.reactivespring.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Slf4j
class BackPressureStrategy {

    @Test
    void error() {
        Flux
                .interval(Duration.ofMillis(1L))
                .onBackpressureError()
                .doOnNext(data -> log.info("doOnNext() : " + data))
                .publishOn(Schedulers.parallel())
                .subscribe(data -> {
                            TimeUtils.sleep(5L);
                            log.info("=============================================" + "subscribe() : {}", data);
                        },
                        error -> log.error("=============================================" + "subscribe() : error", error));
        TimeUtils.sleep(2000L);
    }

    @Test
    void drop() {
        Flux
                .interval(Duration.ofMillis(1L))
                .onBackpressureDrop(dropped -> System.out.println("=============================================" + "dropped = " + dropped))
                .publishOn(Schedulers.parallel())
                .subscribe(data -> {
                            TimeUtils.sleep(5L);
                            System.out.println("achieved data = " + data);
                        },
                        error -> log.error(error.getMessage(), error)
                );

        TimeUtils.sleep(2000L);
    }

    @Test
    void latest() {
        Flux
                .interval(Duration.ofMillis(1L))
                .onBackpressureLatest()
//                .doOnNext(data -> System.out.println("emitted data = " + data))
                .publishOn(Schedulers.parallel())
                .subscribe(
                        data -> {
                            TimeUtils.sleep(5L);
                            System.out.println("achieved data = " + data);
                        },
                        error -> log.error(error.getMessage(), error)
                );
        TimeUtils.sleep(2000L);
    }
}
