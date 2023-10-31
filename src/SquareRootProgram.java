import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This program demonstrates how to use a thread pool to calculate the square
 * root of a set of numbers.
 *
 */
public class SquareRootProgram {

    public static void main(String[] args) throws FileNotFoundException {
        File squaresFile = new File("first100000squares.txt");
        Scanner scanner = new Scanner(squaresFile);
        ArrayList<Double> squares = new ArrayList<Double>();
        while (scanner.hasNext()) {
            String[] string = scanner.next().split(",");
            for (String s : string) {
                squares.add(Double.parseDouble(s));
            }
        }
        //i will be the number of threads
        for (int i = 1; i <= 10; i++) {
            System.out.println("Running with " + i + " threads");
            //k will be the number of times we run the program per thread count
            for (int k = 0; k < 5; k++) {
                //time for memes
                meme(i, squares);
            }
            System.out.println("--------------------------------------------------");
        }
    }

    /**
     * The entry point of the program.
     * @param threadNum The first argument is the number of threads to use.
     */
    public static void meme(int threadNum, ArrayList<Double> squares) {
        //Start timing now.
        long startTime = System.currentTimeMillis();
        System.out.println("Starting time: " + startTime);

        // Create a thread pool with the given number of threads
        ExecutorService executor = Executors.newFixedThreadPool(threadNum);

        // Submit a task for each number
        for (double number : squares) {
            executor.submit(new SquareRootTask(number));
        }

        // Shutdown the executor
        executor.shutdown();

        // Wait until all tasks are finished
        while (Thread.activeCount()>2){
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Stop timing now.
        long endTime = System.currentTimeMillis();
        System.out.println("Thread Ending time: " + endTime);
        System.out.println("total time: " + (endTime - startTime));
    }
}

/**
 * This class represents a task that calculates the square root of a number.
 */
class SquareRootTask implements Runnable {
    private final double number;

    /**
     * Creates a new task for calculating the square root of the given number.
     * @param number The number to calculate the square root of.
     */
    public SquareRootTask(double number) {
        this.number = number;
    }

    /**
     * Calculates the square root of the number.
     */
    @Override
    public void run() {
        // Calculate the square root of the number
        double squareRoot = Math.sqrt(number);

        // Print the result
        //System.out.println("Square root of " + number + " is " + squareRoot);
    }
}