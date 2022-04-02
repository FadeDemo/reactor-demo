package org.fade.demo.reactordemo.coreexample;

import reactor.core.publisher.Flux;

/**
 * 错误处理
 *
 * @author fade
 * @date 2022/03/31
 */
public class ErrorHandling {

    public static void main(String[] args) {
        // reactor doesn't stop main thread from running when an exception occurred
        // todo why

        // static fallback value
        Flux.just(1, 0, 2)
                .map(i -> "100 / " + i + " = " + (100 / i))
                .onErrorReturn("Divided by zero :(")
                .subscribe(System.out::println);

        // apply a Predicate to decide whether to offer a static fallback value
        Flux.just(0, 1, 2).map(x -> 100 / x)
                .onErrorReturn(x -> x instanceof ArithmeticException, 5)
                .subscribe(System.out::println);

        // fallback method
        // onErrorResume
        // since Flux.just(100 / x) has thrown the exception and
        // onErrorResume operator needs a Flux to operate, the
        // onErrorResume operator can't take effect
        // output error message
        Flux.just(0, 1, 2).flatMap(x -> Flux.just(100 / x)
                .onErrorResume(y -> Flux.just(-1)))
                .subscribe(System.out::println);
        // -1 100 50
        // defer operator is lazy and todo: why
        Flux.just(0, 1, 2).flatMap(x -> Flux
                .defer(() -> Flux.just(100 / x))
                .onErrorResume(y -> Flux.just(-1)))
                .subscribe(System.out::println);
        // -1
        Flux.just(0, 1, 1).flatMap(x -> Flux.just(100 / x))
                .onErrorResume(x -> Flux.just(-1))
                .subscribe(System.out::println);
        // -1
        Flux.just(0, 1, 2).map(x -> 100 / x)
                .onErrorResume(y -> Flux.just(-1))
                .subscribe(System.out::println);

        // catch and rethrow
        Flux.just(0, 1, 2).flatMap(x -> Flux.just(100 / x))
                .onErrorResume(x -> Flux.error(new Exception(x)))
                .subscribe(System.out::println);
        Flux.just(0, 1, 2).flatMap(x -> Flux.just(100 / x))
                .onErrorMap(Exception::new)
                .subscribe(System.out::println);

        // long and react on the side
        Flux.just(0, 1, 2).flatMap(x -> Flux.just(100 / x))
                .doOnError(System.err::println)
                .onErrorReturn(-1)
                .subscribe(System.out::println);

        // using resources and the finally block
        Flux.just(1, 2, 3).flatMap(x -> Flux.just(100 / x))
                .doOnSubscribe(x -> System.out.println("start"))
                .doFinally(x -> {
                    switch (x) {
                        case CANCEL:
                            System.out.println("cancel");
                            break;
                        case ON_COMPLETE:
                            System.out.println("complete");
                            break;
                        default:
                            System.out.println("unknown");
                            break;
                    }
                }).take(1).subscribe(System.out::println);

    }

}
