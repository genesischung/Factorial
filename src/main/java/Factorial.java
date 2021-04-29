public class Factorial {
    /**
     * @param n a non-negative integer
     * @return n! (n factorial)
     * Note: long can only hold 64 bit so the acceptable range for n is
     * between 0 and 20. BigInteger can be used instead of long to support
     * bigger input.
     *
     * Big-O: The complexity of factorial() is linear: as n increases by 1,
     * the method performs 1 additional multiplication. We assume that multiply
     * two long integers is O(1), so the overall factorial() is O(n).
     */
    public static long factorial(long n){
        if(n < 0){
            throw new ArithmeticException(
                    "factorial of negative number is undefined");
        }
        if(n > 20){
            throw new ArithmeticException(
                    "factorial of this number is out of range");
        }
        if(n == 0 || n == 1)
            return 1;
        return n * factorial(n - 1);
    }
}
