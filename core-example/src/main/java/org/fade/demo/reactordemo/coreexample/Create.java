package org.fade.demo.reactordemo.coreexample;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

/**
 * 创建
 *
 * @author fade
 * @date 2022/03/11
 */
public class Create {

    @SuppressWarnings("AlibabaUndefineMagicConstant")
    public static void main(String[] args) {
        // 创建Mono
        Mono<Object> empty = Mono.empty();
        Mono<String> monoData = Mono.just("Hello Mono");
        // 创建Flux
        Flux<String> fluxJust = Flux.just("Hello Flux", "Hello Fade");
        Flux<String> fluxFromIterable = Flux.fromIterable(List.of("Hello Flux", "Hello Fade"));
        Flux<String> fluxFromStream = Flux.fromStream(Stream.of("Hello Flux", "Hello Fade"));
        Flux<Integer> range = Flux.range(5, 2);
        // and more ...

        // 编程方式创建
        // 同步
        Flux<String> synchronousFlux = Flux.generate(
                () -> 0,
                (state, sink) -> {
                    sink.next("3 x " + state + " = " + 3*state);
                    if (state == 10) {
                        sink.complete();
                    }
                    return state + 1;
                });
        synchronousFlux = Flux.generate(
                AtomicLong::new,
                (state, sink) -> {
                    long i = state.getAndIncrement();
                    sink.next("3 x " + i + " = " + 3*i);
                    if (i == 10) {
                        sink.complete();
                    }
                    return state;
                });
        synchronousFlux = Flux.generate(
                AtomicLong::new,
                (state, sink) -> {
                    long i = state.getAndIncrement();
                    sink.next("3 x " + i + " = " + 3*i);
                    if (i == 10) {
                        sink.complete();
                    }
                    return state;
                }, (state) -> System.out.println("state: " + state));
        // 异步 多线程
        // todo
//        Flux.create()
//        Mono.create()
        // 异步 单线程
        // todo
//        Flux.push()

        // handle
        // 类似于map和filter的组合
        Flux<String> alphabet = Flux.just(-1, 30, 13, 9, 20)
                .handle((i, sink) -> {
                    String letter = alphabet(i);
                    if (letter != null) {
                        sink.next(letter);
                    }
                });

        alphabet.subscribe(System.out::println);
    }

    public static String alphabet(int letterNumber) {
        if (letterNumber < 1 || letterNumber > 26) {
            return null;
        }
        int letterIndexAscii = 'A' + letterNumber - 1;
        return "" + (char) letterIndexAscii;
    }

}
