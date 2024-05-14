public class Factory {
    private static final Object lock = new Object();
    private static boolean isProductAvailable = false;

    static class Producer extends Thread {
        public void run() {
            synchronized (lock) {
                produceProduct();
                isProductAvailable = true;
                lock.notify();
                System.out.println("Producer: Product is ready.");
            }
        }

        private void produceProduct() {
            System.out.println("Producer: Producing product...");
            try {
                Thread.sleep(5000); // production takes 5s
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    static class Consumer extends Thread {
        public void run() {
            synchronized (lock) {
                while (!isProductAvailable) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                System.out.println("Consumer: Product received.");
            }
        }
    }

    public static void main(String[] args) {
        Producer producer = new Producer();
        Consumer consumer = new Consumer();
        consumer.start();
        producer.start();
    }
}
