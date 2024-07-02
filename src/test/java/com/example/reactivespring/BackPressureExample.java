package com.example.reactivespring;

import com.example.reactivespring.utils.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

/**
 * Subscriber가 처리 가능한 만큼의 request 개수를 조절하는 Backpressure 예제
 */
@Slf4j
class BackPressureExample {

    /**
     * request(1) -> doOnRequest(1) -> doOnNext(1) -> hookOnNext(1) request(1) -> doOnRequest(1) -> doOnNext(2) -> hookOnNext(2) request(1) -> doOnRequest(1) -> doOnNext(3) -> hookOnNext(3) request(1)
     * -> doOnRequest(1) -> doOnNext(4) -> hookOnNext(4) request(1) -> doOnRequest(1) -> doOnNext(5) -> hookOnNext(5)
     */
    @Test
    void testBackPressure() {
        Flux.range(1, 5)
            .doOnNext(data -> System.out.println("data (onNext)= " + data))         // side-effect, data emit 전에
            .doOnRequest(data -> System.out.println("data (onRequest)= " + data)) // side-effect, request 들어올 때
            .subscribe(new BaseSubscriber<Integer>() {
                // hookOnSubscribe : 구독이 시작될 때 호출
                @Override
                protected void hookOnSubscribe(Subscription subscription) {
                    request(1);
                }

                // hookOnNext : Subscriber 가 data 를 수신할 때 호출
                @Override
                protected void hookOnNext(Integer value) {
                    TimeUtils.sleep(3000L);
                    System.out.println("value (hookOnNext)= " + value);
                    request(1);
                }
            });
    }


    /**
     * Flux.range(1, 5) | v data (onNext)= 1  -----> value = 1 data (onNext)= 2  -----> value = 2 (count == 2, request 2 more, reset count) | data (onRequest)= 2 | data (onNext)= 3  -----> value = 3
     * data (onNext)= 4  -----> value = 4 (count == 2, request 2 more, reset count) | data (onRequest)= 2 | data (onNext)= 5  -----> value = 5
     */
    private static int count = 0;

    @Test
    void testBackPressureValue2() {
        // given - 상황 만들기
        Flux.range(1, 5)
            .doOnNext(data -> System.out.println("data (onNext)= " + data))
            .doOnRequest(data -> System.out.println("data (onRequest)= " + data))
            .subscribe(
                new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(2);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        count++;
                        System.out.println("value = " + value);
                        if (count == 2) {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            request(2);
                            count = 0;
                        }
                    }
                }
            );
    }
}