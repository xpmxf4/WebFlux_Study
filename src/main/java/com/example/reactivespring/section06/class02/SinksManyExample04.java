package com.example.reactivespring.section06.class02;

import static reactor.core.publisher.Sinks.EmitFailureHandler.FAIL_FAST;

import com.example.reactivespring.utils.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;

public class SinksManyExample04 {

    public static void main(String[] args) {
        // 구독 이후, Emit 된 데이터 중에서 최신 데이터 2개만 Replay 한다
        Many<Object> replaySink = Sinks.many().replay().limit(2);
        Flux<Object> fluxView = replaySink.asFlux();

        replaySink.emitNext(1, FAIL_FAST);
        replaySink.emitNext(2, FAIL_FAST);
        replaySink.emitNext(3, FAIL_FAST);

        fluxView.subscribe(data -> Logger.onNext("subscriber1", data));

        replaySink.emitNext(4, FAIL_FAST);

        fluxView.subscribe(data -> Logger.onNext("subscriber2", data));
    }

}
