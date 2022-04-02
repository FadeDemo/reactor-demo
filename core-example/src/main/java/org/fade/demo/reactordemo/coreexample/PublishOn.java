package org.fade.demo.reactordemo.coreexample;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/**
 * 线程和调度器
 *
 * @author fade
 * @date 2022/03/26
 */
public class PublishOn {

    public static void main(String[] args) {
        // publishOn
        // affects where the subsequent operators execute
        // (until another publishOn is chained in)
        Scheduler s = Schedulers.newParallel("parallel-scheduler", 4);
        final Flux<String> flux = Flux
                .range(1, 2)
                .map(i -> {
                    System.out.println("["+ Thread.currentThread().getName() +"] first map: " + i);
                    return 10 + i;
                })
                .publishOn(s)
                .map(i -> "["+ Thread.currentThread().getName() +"] value:" + i);

        Thread thread = new Thread(() -> {
            System.out.println("["+ Thread.currentThread().getName() +"] flux is going to invoke subscribe method");
            flux.subscribe(System.out::println);
        });
        thread.start();
        try {
            Thread.sleep(1000);
            s.dispose();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
