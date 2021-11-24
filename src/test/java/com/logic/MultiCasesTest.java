package com.logic;

import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.tuples.Tuple3;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MultiCasesTest {
    MultiCases multiCases;

    @BeforeEach
    public void init(){
        multiCases = new MultiCases();
    }

    @Test
    void executeUnisAndCombineResultTest() throws ExecutionException, InterruptedException {
        List<Tuple3<String, String, String>> a = multiCases.executeMutisAndCombineResult().collectItems().asList().subscribeAsCompletionStage().get();
        assertEquals("this", a.get(0).getItem1());
        assertEquals("on", a.get(0).getItem2());
        assertEquals("off", a.get(0).getItem3());

        assertEquals("is", a.get(1).getItem1());
        assertEquals("the", a.get(1).getItem2());
        assertEquals("to", a.get(1).getItem3());

        assertEquals("the", a.get(2).getItem1());
        assertEquals("second", a.get(2).getItem2());
        assertEquals("the", a.get(2).getItem3());
    }

    @Test
    void executeMutisParallelTest() throws ExecutionException, InterruptedException {
        List<String> result = multiCases.executeMutisParallel().collectItems().asList().subscribeAsCompletionStage().get();
        assertArrayEquals(Arrays.asList("this","is","the","first").toArray(), result.toArray());
    }

    @Test
    void executeWithRetriesTest() throws ExecutionException, InterruptedException {
        List<String> result =  multiCases.executeWithRetries().collectItems().asList().subscribeAsCompletionStage().get();
        assertArrayEquals(Arrays.asList("this").toArray(), result.toArray());
    }
}