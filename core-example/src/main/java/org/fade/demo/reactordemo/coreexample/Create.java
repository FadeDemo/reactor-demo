package org.fade.demo.reactordemo.coreexample;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Stream;

/**
 * 创建
 *
 * @author fade
 * @date 2022/03/11
 */
public class Create {

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
        
    }

}
