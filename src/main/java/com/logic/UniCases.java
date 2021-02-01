package com.logic;

import io.smallrye.mutiny.Uni;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

/**
 * More information
 *
 * https://javadoc.io/doc/io.smallrye.reactive/mutiny/latest/io/smallrye/mutiny/Uni.html
 */

public class UniCases {
    /**
     * This technique is useful when running multiple unis that are independent of one another. The output of these unis
     * can either be collected or discarded. In this example the output is collected and joined into a single uni.
     *
     * One point to note is that although the three unis are combined at the end, the order of execution is still serial and in the order
     * the unis are provided.
     *
     * Usecase - Best when running inserts on table where order of insert is important.
     *
     */
    public Uni<String> executeUnisAndCombineResult(){
        Uni<String> uni1 = Helper.createRandomUni("hi", 3);
        Uni<String> uni2 = Helper.createRandomUni("sup", 1);
        Uni<String> uni3 = Helper.createRandomUni("what", 2);

        // Expected output - hisupwhat
        return Uni.combine().all().unis(uni1, uni2, uni3).asTuple().map(tuple -> tuple.getItem1() + tuple.getItem2() + tuple.getItem3());
    }

    /**
     * The unis each dependent on the output of the previous. The last output is only the output of the last uni.
     *
     * Usecase - Can be used for insert with returning id and then the id can be used to perform another insert.
     *
     */
    public Uni<String> executeUnisAndUsePreviousResult(){
        Uni<String> uni1 =Helper.createRandomUni("okay", 0);
        Uni<String> uni2 =Helper.createRandomUni("blah",0);
        Uni<String> uni3 =Helper.createRandomUni("huh",0);

        // Expected only the last output. huh in this case
       return uni1.flatMap(prevOutput -> "okay".equals(prevOutput) ? uni2 : null).flatMap(prevOutput -> "blah".equals(prevOutput) ? uni3 : null);
    }

    /**
     * Null checks
     *
     * @param message
     * @return
     */
    public Uni<String> executeUnisNullCheck(String message){
        Uni<String> uni1 = Helper.createRandomUni(message, 0);

        return uni1.onItem().ifNotNull().transform(m -> m)
                .onItem().ifNull().continueWith("value is null");
    }

    /**
     * When a fail event is sent, it can be handled in the following way.
     * This happens when an exception occurs.
     *
     * @return
     */
    public Uni<String> uniFailureCheck(){
        Uni<String> uni1 = Helper.createFailingUni();

        return uni1.onItem().transform(i -> i).onFailure().recoverWithItem("Failed");
    }

    /**
     * When a no item event is sent, this can be handled as follows.
     * Such an event is sent sometimes when a database select query is executed, and no rows are found matching the where clause.
     *
     * More options
     * uni.ifNoItem().after(Duration.ofMillis(1000)).fail() // Propagate a TimeOutException
     * uni.ifNoItem().after(Duration.ofMillis(1000)).recoverWithItem("fallback") // Inject a fallback item on timeout
     * uni.ifNoItem().after(Duration.ofMillis(1000)).on(myExecutor)... // Configure the executor calling on timeout actions
     * uni.ifNoItem().after(Duration.ofMillis(1000)).retry().atMost(5) // Retry five times
     */
    public Uni<String> uniNoItemCheck(){
        Uni<String> uni1 = Helper.createNoItemUni();
        return uni1.ifNoItem().after(Duration.ofMillis(1000)).recoverWithItem("fallback");
    }
}
