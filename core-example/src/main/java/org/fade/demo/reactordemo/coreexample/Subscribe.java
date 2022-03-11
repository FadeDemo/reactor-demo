package org.fade.demo.reactordemo.coreexample;

import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

/**
 * 订阅
 *
 * @author fade
 * @date 2022/03/11
 */
public class Subscribe {

    @SuppressWarnings({"deprecation", "AlibabaRemoveCommentedCode"})
    public static void main(String[] args) {
        // subscribe()
        Flux<Integer> ints = Flux.range(1, 3);
        ints.subscribe();

        // subscribe(Consumer<T>)
        ints.subscribe(System.out::println);

        // subscribe(Consumer<? super T> consumer,
        //          Consumer<? super Throwable> errorConsumer)
        int errorNumber = 2;
        ints.map(x -> {
            if (x.equals(errorNumber)) {
                throw new RuntimeException("test");
            }
            return x;
        }).subscribe(System.out::println, System.err::println);

        // subscribe(Consumer<? super T> consumer,
        //          Consumer<? super Throwable> errorConsumer,
        //          Runnable completeConsumer)
        ints.subscribe(System.out::println,
                System.err::println,
                () -> System.out.println("Done"));

        // subscribe(Consumer<? super T> consumer,
        //          Consumer<? super Throwable> errorConsumer,
        //          Runnable completeConsumer,
        //          Consumer<? super Subscription> subscriptionConsumer)
        ints.subscribe(System.out::println,
                System.err::println,
                () -> System.out.println("Done"), x -> x.request(2));
        ints.subscribe(System.out::println,
                System.err::println,
                () -> System.out.println("Done"), x -> x.request(10));
        ints.subscribe(System.out::println,
                System.err::println,
                () -> System.out.println("Done"), Subscription::cancel);

        // subscribe(CoreSubscriber<? super T> actual)
        ints.subscribe(new SampleSubscriber<>());
    }

}
