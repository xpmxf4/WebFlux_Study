package com.example.reactivespring.section06.class02;

import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.EmitFailureHandler;

@Slf4j
public class ProgrammaticSinksExample01 {

    public static void main(String[] args) {
        int tasks = 6;

        Sinks.Many<String> unicastSink = Sinks.many().unicast().onBackpressureBuffer();
        Flux<String> fluxView = unicastSink.asFlux();
        IntStream
            .range(1, tasks)
            .forEach(n -> {
                try {
                    new Thread(() -> {
                        unicastSink.emitNext(doTask(n), EmitFailureHandler.FAIL_FAST);
                    }).start();
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

        fluxView.subscribe(data -> log.info("# onNext : {}", data));
    }

    private static String doTask(int taskNumber) {
        return "task " + taskNumber + " result";
    }
}
