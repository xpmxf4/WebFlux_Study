package com.example.reactivespring.section06.class02;

import static reactor.core.publisher.Sinks.EmitFailureHandler.FAIL_FAST;

import com.example.reactivespring.utils.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;

public class SinksManyExample03 {

    public static void main(String[] args) {
        Many<Object> replaySink = Sinks.many().replay().limit(2);
        Flux<Object> fluwView = replaySink.asFlux();

        replaySink.emitNext(1, FAIL_FAST);
        replaySink.emitNext(2, FAIL_FAST);
        replaySink.emitNext(3, FAIL_FAST);

        fluwView.subscribe(data -> Logger.onNext("subsriber1", data));
        fluwView.subscribe(data -> Logger.onNext("subsriber2", data));
    }

}
