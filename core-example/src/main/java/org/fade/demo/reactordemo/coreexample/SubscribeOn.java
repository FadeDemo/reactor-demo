package org.fade.demo.reactordemo.coreexample;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/**
 * @author fade
 * @date 2022/03/31
 */
public class SubscribeOn {

    public static void main(String[] args) {
        // subscribeOn
        // it always affects the context of the source emission
        Scheduler s = Schedulers.newParallel("parallel-scheduler", 4);
        final Flux<String> flux = Flux
                .range(1, 2)
                .map(i -> {
                    System.out.println("[" + Thread.currentThread().getName() + "] first map: " + i);
                    return 10 + i;
                })
                .subscribeOn(s)
                .map(i -> {
                    System.out.println("[" + Thread.currentThread().getName() + "] second map: " + i);
                    return "value " + i;
                });

        new Thread(() -> {
            System.out.println("["+ Thread.currentThread().getName() +"] flux is going to invoke subscribe method");
            flux.subscribe(x -> System.out.println("["+ Thread.currentThread().getName() +"] " + x));
        }).start();
        try {
            Thread.sleep(1000);
            s.dispose();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
