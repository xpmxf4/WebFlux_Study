package com.example.reactivespring.practiceMyself.BackPressure;

import com.example.reactivespring.utils.TimeUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

/**
 * Subscriber 가 처리 가능한 만틈의 request 개수를 조절하는 Backpressure 예제
 */
class BackPressureExample {

    @Test
    void backPressure_예제() {
        Flux.range(1, 5)
            .doOnNext(data -> System.out.println("check before sending out = " + data))
            .doOnRequest(data -> System.out.println("request just came in = " + data))
            .subscribe(
                new BaseSubscriber<>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(1);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        TimeUtils.sleep(3000L);
                        System.out.println("value just came in = " + value);
                        request(1);
                    }
                }
            );
    }

    private static int count = 0;

    @Test
    void backPressure_예제2() {
        // given - 상황 만들기
        Flux<Integer> flux1to5 = Flux.range(1, 5)
            .doOnNext(data -> System.out.println("check before sending out = " + data))
            .doOnRequest(data -> {
                System.out.println();
                System.out.println("request just came in = " + data);
            });

        // when - 동작
        flux1to5.subscribe(
            new BaseSubscriber<Integer>() {
                @Override
                protected void hookOnSubscribe(Subscription subscription) {
                    request(2);
                }

                @Override
                protected void hookOnNext(Integer value) {
                    count++;
                    System.out.println("value just came in = " + value);
                    if (count == 2) {
                        TimeUtils.sleep(2000);
                        request(2);
                        count = 0;
                    }
                }
            }
        );

        // then - 검증
        Assertions.assertThat(count).isEqualTo(1);
    }
}
