package com.example.reactivespring.section06.class02;

import static reactor.core.publisher.Sinks.EmitFailureHandler.FAIL_FAST;

import com.example.reactivespring.utils.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;

/**
 * Sinks.Many 예제 - unicast() 를 사용해서 단 하나의 Subscriber 에게만 데이터를 Emit 하는 예제
 */
public class SinksManyExample01 {

    public static void main(String[] args) {
        Many<Object> unicastMany = Sinks.many().unicast().onBackpressureBuffer(); // unicast : 단 하나의 subscriber 에게만
        Flux<Object> fluxView = unicastMany.asFlux();

        unicastMany.emitNext(1, FAIL_FAST);
        unicastMany.emitNext(2, FAIL_FAST);

        fluxView.subscribe(data -> Logger.onNext("Subscriber 1", data));
        unicastMany.emitNext(3, FAIL_FAST);
//        fluxView.subscribe(data -> Logger.onNext("Subscriber 1", data));
    }
}
