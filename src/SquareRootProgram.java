import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SquareRootProgram {
    public static void main(String[] args) {
        // Create a thread pool with 5 threads
        ExecutorService executor = Executors.newFixedThreadPool(5);

        // Create an array of numbers
        double[] numbers = { 16, 25, 36, 49, 64, 81, 100 };

        // Submit tasks to the thread pool
        for (double number : numbers) {
            executor.submit(new SquareRootTask(number));
        }

        // Shutdown the executor
        executor.shutdown();
    }
}

class SquareRootTask implements Runnable {
    private double number;

    public SquareRootTask(double number) {
        this.number = number;
    }

    @Override
    public void run() {
        // Calculate the square root of the number
        double squareRoot = Math.sqrt(number);

        // Print the result
        System.out.println("Square root of " + number + " is " + squareRoot);
    }
}