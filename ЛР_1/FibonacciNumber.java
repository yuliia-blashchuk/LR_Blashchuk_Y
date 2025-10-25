package org.example;
import java.math.BigInteger;

/**
        * Клас для представлення числа Фібоначчі.
 * Зберігає його індекс, значення та надає методи для роботи з ним.
        */

public class FibonacciNumber {
    private int index;
    private BigInteger value;

    /**
     * Конструктор класу FibonacciNumber.
     */
    public FibonacciNumber(int index, BigInteger value) {
        this.index = index;
        this.value = value;
    }

    /**
     * Повертає індекс числа Фібоначчі.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Повертає значення числа Фібоначчі.
     */
    public BigInteger getValue() {
        return value;
    }

    /**
     * Перевіряє, чи є число повним квадратом.
     */
    public boolean Is_square() {
        BigInteger sqrt = BISqrt(value);
        return sqrt.multiply(sqrt).equals(value);
    }

    private static BigInteger BISqrt(BigInteger x) {
        BigInteger right = x, left = BigInteger.ZERO, midle;
        while (left.compareTo(right) <= 0) {
            midle = left.add(right).divide(BigInteger.TWO);
            int comp = midle.multiply(midle).compareTo(x);
            if (comp == 0) return midle;
            if (comp < 0) left = midle.add(BigInteger.ONE);
            else right = midle.subtract(BigInteger.ONE);
        }
        return right;
    }
}




