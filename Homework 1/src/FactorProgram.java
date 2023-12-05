import java.util.ArrayList;
import java.util.List;

public class FactorProgram {
    public static void main(String[] args) {


    //i will be the number of threads
        for(int i = 1;i <=10;i++){
        System.out.println("Running with " + i + " threads");
        //k will be the number of times we run the program per thread count
        for (int k = 0; k < 5; k++) {
            //time for memes
            meme(i, 60000);
        }
        System.out.println("--------------------------------------------------");
    }
}

    public static void meme(int threadNum, int number) {
        //Start timing now.
        long startTime = System.currentTimeMillis();
        System.out.println("Starting time: " + startTime);

        List<FactorThread> factorThreads = new ArrayList<>();
        for (int i = 0; i < threadNum; i++) {
            factorThreads.add(new FactorThread(i, threadNum, number));
        }

        for (FactorThread thread : factorThreads) {
            thread.start();
        }
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

class FactorThread extends Thread {
    private final int id;
    private final int totalThreads;
    private final int number;

    public FactorThread(int id, int totalThreads, int number) {
        this.id = id;
        this.totalThreads = totalThreads;
        this.number = number;
    }

    @Override
    public void run() {
        for (int i = id; i <= number; i += totalThreads) {
            if (isPrime(i)) {
                //DO NOTHING! I DONT WANT OUTPUT :)
            }
        }
    }

    private boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
