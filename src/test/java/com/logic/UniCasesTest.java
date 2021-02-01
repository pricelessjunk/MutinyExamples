package com.logic;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class UniCasesTest {

    //@Inject
    UniCases uniCase;

    @BeforeEach
    public void init(){
        uniCase = new UniCases();
    }

    @Test
    void executeUnisAndCombineResultTest() throws ExecutionException, InterruptedException {
        String result = uniCase.executeUnisAndCombineResult().subscribeAsCompletionStage().get();
        assertEquals("hisupwhat", result);
    }



    @Test
    void executeUnisAndUsePreviousResultTest() throws ExecutionException, InterruptedException {
        String result = uniCase.executeUnisAndUsePreviousResult().subscribeAsCompletionStage().get();
        assertEquals("huh", result);
    }

    @Test
    void executeUnisNullCheckTest() throws ExecutionException, InterruptedException {
        String nonNull = uniCase.executeUnisNullCheck("the message").subscribeAsCompletionStage().get();
        assertEquals("the message", nonNull);

        String nullString = uniCase.executeUnisNullCheck(null).subscribeAsCompletionStage().get();
        assertEquals("value is null", nullString);
    }

    @Test
    void uniFailureCheckTest() throws ExecutionException, InterruptedException {
        String result = uniCase.uniFailureCheck().subscribeAsCompletionStage().get();
        assertEquals("Failed", result);
    }

    @Test
    void uniNoItemCheckTest() throws ExecutionException, InterruptedException {
        String result = uniCase.uniNoItemCheck().subscribeAsCompletionStage().get();
        assertEquals("fallback", result);
    }
}