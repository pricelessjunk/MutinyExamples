package com.logic;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple3;

import java.util.List;

public class MultiCases {
    /**
     * Same as unis, Multis can also be combined together. One thing to note here is that the length of the l
     */
    public Multi<Tuple3<String, String, String>> executeMutisAndCombineResult(){
        Multi<String> multi1 = Helper.createRandomMulti("this","is","the","first");
        Multi<String> multi2 = Helper.createRandomMulti("on","the","second");
        Multi<String> multi3 = Helper.createRandomMulti("off","to","the","third");

        return Multi.createBy().combining().streams(multi1, multi2, multi3).asTuple();//.collectItems().asList();
    }

    /**
     * Invike is very useful to spy on values in between.
     * @return
     */
    public Multi<String> executeMutisParallel(){
        Multi<String> multi1 = Helper.createRandomMulti("this","is","the","first");

        return multi1.invoke(a -> System.out.println(a));
    }
}
