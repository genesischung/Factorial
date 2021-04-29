/*
    I am using the following dependencies with JDK15:
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.7.1'
    testImplementation 'org.junit.jupiter:junit-jupiter-params:5.7.1'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.7.1'
 */
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

class FactorialTest {

    // A signed 64-bit long can only represent up to 20!
    // 21! will cause overflow
    public static long[][] testSet(){
        return new long[][]{{0, 1}, {1, 1},
                {2, 2}, {3, 6}, {4, 24}, {5, 120}, {6, 720}, {7,5040},
                {8, 40320}, {9, 362880}, {10, 3628800}, {11, 39916800},
                {12, 479001600}, {13, 6227020800L}, {14, 87178291200L},
                {15, 1307674368000L}, {16, 20922789888000L},
                {17, 355687428096000L}, {18, 6402373705728000L},
                {19, 121645100408832000L}, {20, 2432902008176640000L}
        };
    }

    // Test that factorial() will handle out of range input
    @Test
    void testFactorialInvalid() {
        assertThrows(ArithmeticException.class,
                () ->Factorial.factorial(-10));
        assertThrows(ArithmeticException.class,
                () ->Factorial.factorial(21));

    }

    // Test all acceptable inputs from 0 to 20 against the expected result
    @ParameterizedTest
    @MethodSource("testSet")
    void testFactorial(long[] set){
        assertEquals(set[1], Factorial.factorial(set[0]));
    }

}