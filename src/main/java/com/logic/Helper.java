package com.logic;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Helper {
    /**
     * The time will be printed only after a subscribe is called as this part rus from within the uni.
     * A subscribe may automatically called by the resteasy api. Here, subscribe is called from test.
     *
     *
     * @param message
     * @param sleepTime The amount of time the execution is paused in sec. Simulates time needed for processing
     * @return
     */
    public static Uni<String> createRandomUni(String message, int sleepTime){
        return Uni.createFrom().item(message).onItem().transform( i -> {
            waitFor(sleepTime);
            System.out.println(i + " " + LocalDateTime.now());
            return i;
        });
    }

    public static Uni<String> createFailingUni(){
        return Uni.createFrom().emitter(e -> e.fail(new Exception()));
    }

    public static Uni<String> createNoItemUni(){
        return Uni.createFrom().nothing();
    }

    /*
    * Multi
     */

    public static Multi<String> createRandomMulti(String... words) {
        return Multi.createFrom().items(words);
    }

    /**
     * sleep
     * @param sleepTime
     */
    private static void waitFor(int sleepTime){
        try {
            TimeUnit.SECONDS.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
