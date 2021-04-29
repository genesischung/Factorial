/*
    I am using the following dependencies with JDK15:
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.7.1'
    testImplementation 'org.junit.jupiter:junit-jupiter-params:5.7.1'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.7.1'
 */
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class MultiThreadFactorialTest {

    int threadsAvailable = Runtime.getRuntime().availableProcessors();
    MultiThreadFactorial mf = new MultiThreadFactorial(threadsAvailable);

    // A test set from the previous exercise
    // including all the expected result for input from 0 to 20
    public static long[][] testSet(){
        return new long[][]{ {0, 1}, {1, 1},
                {2, 2}, {3, 6}, {4, 24}, {5, 120}, {6, 720}, {7,5040},
                {8, 40320}, {9, 362880}, {10, 3628800}, {11, 39916800},
                {12, 479001600}, {13, 6227020800L}, {14, 87178291200L},
                {15, 1307674368000L}, {16, 20922789888000L},
                {17, 355687428096000L}, {18, 6402373705728000L},
                {19, 121645100408832000L}, {20, 2432902008176640000L}
        };
    }

    // Test that factorial() will throw exception for negative inputs
    @Test
    void testFactorialInvalid() {
        assertThrows(ArithmeticException.class,
                () ->mf.factorial(-10));
    }

    // Test inputs from 0 to 20 against the expected result
    @ParameterizedTest
    @MethodSource("testSet")
    void testFactorial(long[] set){
        assertEquals(BigInteger.valueOf(set[1]), mf.factorial(set[0]));
        //System.out.println(mf.factorial(set[0]));
    }

    // Test a couple of larger inputs against the expected result
    @ParameterizedTest
    @CsvSource({"30, 265252859812191058636308480000000", "50, " +
            "30414093201713378043612608166064768844377641568960512000000000000",
            "70, 11978571669969891796072783721689098736458938142546425" +
                    "857555362864628009582789845319680000000000000000",
            "90, 14857159644817614973095227336208257378855699612846887" +
                    "66942216863704985393094065876545992131370884059645" +
                    "617234469978112000000000000000000000",
            "99, 9332621544394415268169923885626670049071596826438162" +
                    "14685929638952175999932299156089414639761565182862" +
                    "536979208272237582511852109168640000000000000000000000"
})
    void testBigFactorial(String first, String second){
        long n = Long.parseLong(first);
        BigInteger expected = new BigInteger(second);
        assertEquals(expected, mf.factorial(n));
    }

    // A test for Question 2 and Question 3 with single thread factorial
    @Test
    void benchSingleFactorial(){
        System.out.println(mf.benchSingleThreadFactorial(1000, 20) +  " ns");
        System.out.println(mf.benchSingleThreadFactorial(10000, 20)  +  " ns");
        System.out.println(mf.benchSingleThreadFactorial(100000, 20)  +  " ns");
        //System.out.println(mfSingle.benchSingleThreadFactorial(1000000, 1)  +  " ns");

    }

    // Another test for Question 2 and Question 3 with multi thread factorial
    @Test
    void benchMultiFactorial(){
        MultiThreadFactorial mfMulti = new MultiThreadFactorial(12);
        System.out.println(mfMulti.benchFactorial(1000, 20) +  " ns");
        System.out.println(mfMulti.benchFactorial(10000, 20)  +  " ns");
        System.out.println(mfMulti.benchFactorial(100000, 20)  +  " ns");
        System.out.println(mfMulti.benchFactorial(1000000, 10)  +  " ns");
    }

}