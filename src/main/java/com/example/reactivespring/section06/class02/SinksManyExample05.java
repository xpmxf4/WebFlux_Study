package com.example.reactivespring.section06.class02;

import static reactor.core.publisher.Sinks.EmitFailureHandler.FAIL_FAST;

import com.example.reactivespring.utils.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;

public class SinksManyExample05 {

    public static void main(String[] args) {
        // 구독 시점과 상광넚이, Emit 된 모든 데이터를 replay 한다.
        Many<Object> replaySink = Sinks.many().replay().all();
        Flux<Object> fluxView = replaySink.asFlux();

        replaySink.emitNext(1, FAIL_FAST);
        replaySink.emitNext(2, FAIL_FAST);
        replaySink.emitNext(3, FAIL_FAST);

        fluxView.subscribe(data -> Logger.onNext("subsriber1", data));
        fluxView.subscribe(data -> Logger.onNext("subsriber2", data));
    }
}
