
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.cactoos.func.Async;

import java.util.concurrent.*;

public class MathematicsMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MathematicsOperations mathematicsOperations = new MathematicsOperations();
        int numero =10;
        //Thread
        ThreadExample(mathematicsOperations);


        FutureExample(mathematicsOperations);
        // CompetableFuture
        CompletableFuture(mathematicsOperations, numero);
        // guava
        guava(mathematicsOperations, numero);
        // Async
//        Async(mathematicsOperations, numero);
//        aspect();
        palindrome();

    }

//    private static void Async(MathematicsOperations mathematicsOperations, int numero) throws InterruptedException, ExecutionException {
//        CompletableFuture<Long> completableFuture = CompletableFuture.supplyAsync(()-> mathematicsOperations.factorial(numero));
//        long result = Async.await(completableFuture);
//        System.out.println("El resultado es : "+result);
//        Async<Integer,Long> async = new Async<Integer,Long>(input -> mathematicsOperations.factorial(input));
//        Future<Long> asyncFuture = async.apply(numero);
//        long result =asyncFuture.get();
//        System.out.println("The result is : "+result);
//    }

    private static void guava(MathematicsOperations mathematicsOperations, int numero) throws InterruptedException, ExecutionException {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        ListeningExecutorService service = MoreExecutors.listeningDecorator(threadPool);
        ListenableFuture<Long>guavaFutures = service.submit(()-> mathematicsOperations.factorial(numero));
        Long result = guavaFutures.get();
        System.out.println("El resultado es : "+result);
        threadPool.shutdown();
    }

    private static void CompletableFuture(MathematicsOperations mathematicsOperations, int numero) throws InterruptedException, ExecutionException {
        CompletableFuture<Long> completableFuture = CompletableFuture.supplyAsync(()-> mathematicsOperations.factorial(numero));
        while (!completableFuture.isDone()){
            System.out.println("CompletableFuture is not finished");
        }
        Long result = completableFuture.get();
        System.out.println("El resultado es : "+result);
    }

    private static void FutureExample(MathematicsOperations mathematicsOperations) throws InterruptedException, ExecutionException {
        ExecutorService threatPool = Executors.newCachedThreadPool();
        Future<Long>  futuretask = threatPool.submit(()-> mathematicsOperations.factorial(10));
        while (!futuretask.isDone()){
            System.out.println("Futuretask is not finished");
        }
        Long result = futuretask.get();
        System.out.println("El resultado ".concat(result+""));
        threatPool.shutdown();
    }

    private static void ThreadExample(MathematicsOperations mathematicsOperations) {
        Thread thread = new Thread(()->{
            System.out.println("El factorial del numero ".concat("25 es: ").concat(mathematicsOperations.factorial(25)+""));
        });
        thread.start();
    }

    //    @com.jcabi.aspects.Async
//    public static void aspect() throws ExecutionException, InterruptedException {
//        MathematicsOperations mathematicsOperations = new MathematicsOperations();
//        int numero =10;
//        Future<Long> factorial = CompletableFuture.completedFuture(mathematicsOperations.factorial(numero));
//        System.out.println("The result is : "+ factorial.get());
//    }
    @com.jcabi.aspects.Async
    public  static void palindrome() throws ExecutionException, InterruptedException {
        Palindromes palindromes = new Palindromes();
        Future<Integer> cantidadPalindromes = CompletableFuture.completedFuture(palindromes.cantidadPalabrasPalindromes());
        System.out.println("La cantidad de palindromes es : "+ cantidadPalindromes.get());
    }

}
