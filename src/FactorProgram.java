import java.util.ArrayList;
import java.util.List;

public class FactorProgram {
    public static void main(String[] args) {
        int threads = 5;
        int number = 600000;

        if (args.length > 0) {
            threads = Integer.parseInt(args[0]);
        }
        if (args.length > 1) {
            number = Integer.parseInt(args[1]);
        }

        List<FactorThread> factorThreads = new ArrayList<>();
        for (int i = 0; i < threads; i++) {
            factorThreads.add(new FactorThread(i, threads, number));
        }

        for (FactorThread thread : factorThreads) {
            thread.start();
        }
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
                System.out.println(i + " is prime");
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
