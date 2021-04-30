import java.math.BigInteger;

public class MultiThreadFactorial {

    private static int NUM_THREADS;

    /**
     * Default constructor: use 8 threads
     */
    public MultiThreadFactorial() {
        //System.out.println("default constructor");
        NUM_THREADS = 8;
    }

    /**
     * @param threadNum number of threads intended to use
     * Constructor that specifies the number of threads to use
     */
    public MultiThreadFactorial(int threadNum){
        //System.out.println("parametrized constructor");
        this.NUM_THREADS = threadNum;
    }


    public BigInteger singleThreadFactorial(long n){
        if(n < 0)
            throw new ArithmeticException("invalid number");
        if(n == 0 || n == 1)
            return BigInteger.ONE;
        BigInteger result = BigInteger.ONE;
        for(long i = 2; i <= n; i++){
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }
    /**
     * @param n a non-negative integer
     * @return n! (n factorial)
     * if n > NUM_THREADS, use a single thread to compute n!
     * otherwise use multiple threads to compute n!
     */
    public BigInteger factorial(long n){
        if(n < 0)
            throw new ArithmeticException("invalid number");
        if(n == 0 || n == 1)
            return BigInteger.ONE;
        if(n < NUM_THREADS){
            FactorialRunnable fr = new FactorialRunnable(1, n);
            fr.run();
            return fr.getResult();
        }
        Thread[] threads = new Thread[NUM_THREADS];
        FactorialRunnable[] fr = new FactorialRunnable[NUM_THREADS];
        for(int i = 0; i < NUM_THREADS; i++){
            long start, end;
            // start from 1 for the first thread
            start = i == 0 ? 1 : (1 + (n / NUM_THREADS) * i);
            // end with n for the last thread
            end = i == (NUM_THREADS - 1) ? n : (n / NUM_THREADS) * (i + 1);
            try {
                fr[i] = new FactorialRunnable(start, end);
                threads[i] = new Thread(fr[i]);
                threads[i].start();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        for(int i = 0; i < NUM_THREADS; i++){
            try{
                threads[i].join();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        BigInteger result = BigInteger.ONE;
        for(int i = 0; i < NUM_THREADS; i++){
            try{
                //System.out.println("Thread " + i);
                result = result.multiply(fr[i].getResult());
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        return result;
    }

    /**
     * @param n input for n!
     * @return the execution time for factorial() in nanoseconds
     * Run multiple times and get the average execution time
     */
    public static long benchFactorial(long n, int reps, int threads){
        MultiThreadFactorial mf = new MultiThreadFactorial(threads);
        long sum = 0;
        for(int i = 0; i < reps; i++) {
            long start = System.nanoTime();
            mf.factorial(n);
            long end = System.nanoTime();
            sum += (end - start);
        }
        return sum / reps;
    }

    /**
     * @param n input for n!
     * @return the execution time for factorial() in nanoseconds
     * Run multiple times and get the average execution time
     */
    public static long benchSingleThreadFactorial(long n, int reps){
        MultiThreadFactorial mf = new MultiThreadFactorial();
        long sum = 0;
        for(int i = 0; i < reps; i++) {
            long start = System.nanoTime();
            mf.singleThreadFactorial(n);
            long end = System.nanoTime();
            sum += (end - start);
        }
        return sum / reps;
    }

    /**
     * Implementation of the Runnable interface to compute the product of
     * all the integers from start to end.
     */
    private class FactorialRunnable implements Runnable{
        public BigInteger result = BigInteger.ONE;
        private final long start;
        private final long end;

        public FactorialRunnable(long start, long end){
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            for(long i = start; i <= end; i++){
                result = result.multiply(BigInteger.valueOf(i));
            }
        }

        public BigInteger getResult(){
            return result;
        }
    }
}
