import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FactoryWithExecutor {
    public static void main(String[] args) {
        // Create an ExecutorService with a pool of 2 threads
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Runnable producerTask = () -> {
            System.out.println("Producer: Producing product...");
            try {
                Thread.sleep(5000); // production takes 5s
                System.out.println("Producer: Product is ready.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Runnable consumerTask = () -> {
            try {
                // Consumer waits for a certain period before receiving the product
                Thread.sleep(5500);
                System.out.println("Consumer: Product received.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        executor.execute(producerTask);
        executor.execute(consumerTask);

        executor.shutdown();
    }
}
