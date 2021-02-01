
| Shortcut | Equivalent |
| :------------- | :----------: |
| uni.map(x -> y) | uni.onItem().transform(x -> y)|
| uni.flatMap(x -> uni2) | uni.onItem().transformToUni(x -> uni2) |
| uni.chain(x -> uni2) | uni.onItem().transformToUni(x -> uni2) |
| uni.then(() -> uni2) | uni.onItem().transformToUni(ignored -> uni2) |
| uni.invoke(x -> System.out.println(x)) | uni.onItem().invoke(x -> System.out.println(x))|
| uni.call(x -> uni2) | uni.onItem().call(x -> uni2)|
| uni.eventually(() -> System.out.println("eventually")) | uni.onItemOrFailure().invoke((ignoredItem, ignoredException) -> System.out.println("eventually"))
| uni.eventually(() -> uni2) | uni.onItemOrFailure().call((ignoredItem, ignoredException) -> uni2) |