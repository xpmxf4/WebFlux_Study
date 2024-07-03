package com.example.reactivespring.section06.class02;

import com.example.reactivespring.utils.Logger;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitFailureHandler;

/**
 * Sinks.One 예제
 * - 두 건의 데이터를 emit 하는 예제
 */
public class SinkOneExample02 {

    public static void main(String[] args) {
        // emit 된 데이터 중에서 단 하나의 데이터만 Subscriber 에게 전달한다. 나머지 data 는 drop 됨
        Sinks.One<String > sinkOne = Sinks.one();
        Mono<String> mono = sinkOne.asMono();

        sinkOne.emitValue("Hello Reactor", EmitFailureHandler.FAIL_FAST);
        sinkOne.emitValue("World", EmitFailureHandler.FAIL_FAST);

        mono.subscribe(data -> Logger.onNext("Subscriber 1", data));
        mono.subscribe(data -> Logger.onNext("Subscriber 2", data));
    }
}
