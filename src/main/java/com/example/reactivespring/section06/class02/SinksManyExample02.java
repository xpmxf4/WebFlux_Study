package com.example.reactivespring.section06.class02;

import static reactor.core.publisher.Sinks.EmitFailureHandler.FAIL_FAST;

import com.example.reactivespring.utils.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;

/**
 * Sinks.Many 예제
 * - multicast() 를 사용해서 하나 이상의 Subscriber 에게 데이터를 Emit 하는 예제
 */
public class SinksManyExample02 {

    public static void main(String[] args) {
        Many<Object> multicastSink = Sinks.many().multicast().onBackpressureBuffer();
        Flux<Object> fluxView = multicastSink.asFlux();

        multicastSink.emitNext(1, FAIL_FAST);
        multicastSink.emitNext(2, FAIL_FAST);

        fluxView.subscribe(data -> Logger.onNext("Subscriber1 ", data));
        fluxView.subscribe(data -> Logger.onNext("Subscriber2 ", data));
        /**
         * 09:44:18.330 [main] INFO com.example.reactivespring.utils.Logger - # Subscriber1  onNext(): 1
         * 09:44:18.331 [main] INFO com.example.reactivespring.utils.Logger - # Subscriber1  onNext(): 2
         * 09:44:18.331 [main] INFO com.example.reactivespring.utils.Logger - # Subscriber1  onNext(): 3
         * 09:44:18.331 [main] INFO com.example.reactivespring.utils.Logger - # Subscriber2  onNext(): 3
         * 이렇게 찍히는데, multicast() 는 hot sequence 라서, 구독 시점부터 emit 된 정보를 받는다
         * subscriber1 는 처음부터 구독을 하기에 1,2,3 을 받지만, subsriber2 는 1,2 의 타임라인 뒤에 data 를 받는다.
         */

        multicastSink.emitNext(3, FAIL_FAST);
    }

}
