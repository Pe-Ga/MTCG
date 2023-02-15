package at.technikum.lessons;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Threading {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService executorService
                = Executors.newFixedThreadPool(50);

        List<CompletableFuture<String>> futureList =
                new ArrayList<>();

        /*for (int i = 0; i < 50; i++) {
            Thread.sleep(300);
            futureList.add(CompletableFuture.supplyAsync(() -> {
                System.out.println("Hallo");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                return Thread.currentThread().getName() + " was here!";
            }, executorService));
        } */
        final CompletableFuture[] completableFutures = new CompletableFuture[10];
        /*CompletableFuture.allOf(
                futureList.toArray(completableFutures)
        ).join(); */




        executorService.shutdown();


        CompletableFuture<Void> task = CompletableFuture.supplyAsync(
                        () -> {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            if (1 > 40) {
                                throw new IllegalStateException("");
                            }
                            return List.of("Hallo", "wie", "geht", "es");
                        }
                ).exceptionally((ex) -> {
                    return List.of("alternative");
                })
                .thenApplyAsync((stringList) -> {
                    return stringList.size();
                }).thenAccept((size) -> System.out.println(size))
                .exceptionally((ex) -> {
                    System.err.println(ex.getStackTrace());
                    return Void.TYPE.cast(new Object());
                });
        task.join();


        final ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        forkJoinPool.submit(new CustomRecursiveAction("Hallo, test"));
        forkJoinPool.awaitQuiescence(3000, TimeUnit.MINUTES);

    }


}
