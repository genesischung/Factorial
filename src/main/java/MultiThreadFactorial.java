import java.math.BigInteger;

public class MultiThreadFactorial {
    private static int NUM_THREADS;

    /**
     * Default constructor: use 4 threads
     */
    public MultiThreadFactorial() {
        NUM_THREADS = 4;
    }

    /**
     * @param threadNum number of threads intended to use
     * Constructor that specifies the number of threads to use
     */
    public MultiThreadFactorial(int threadNum){
        NUM_THREADS = threadNum;
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
                result = result.multiply(fr[i].getResult());
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        return result;
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
