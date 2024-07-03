package com.example.reactivespring.section06.class02;

import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

@Slf4j
public class ProgrammaticCreateExample01 {

    public static void main(String[] args) throws InterruptedException {

        int tasks = 6;

        Flux
            .create((FluxSink<String> sink) ->
                IntStream
                    .range(1, tasks)
                    .forEach(n -> sink.next(doTask(n))))
            .subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(500L);
    }

    private static String doTask(int taskNumber) {
        return "task " + taskNumber + " result";
    }
}
