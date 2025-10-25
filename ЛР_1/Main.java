package org.example;
import java.util.Scanner;
import java.math.BigInteger;
/**
 * Головний клас програми.
 * Генерує послідовність чисел Фібоначчі, виводить їх
 * і перевіряє, які з них є повними квадратами.
 */

public class Main {

    /**
     * Запускає програму.
     * @param args аргументи командного рядка
     */
    public static void main(String[] args) {

        int N; // к-сть чисел

        if (args.length > 0) {
            N = Integer.parseInt(args[0]);
        } else {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter the number of Fibonacci numbers N: ");
            N = sc.nextInt();
        }

        FibonacciNumber[] numbers = new FibonacciNumber[N];

        BigInteger a = BigInteger.ONE;
        BigInteger b = BigInteger.ONE;
        for (int i = 0; i < N; i++) {
            BigInteger  value;
            if (i == 0 || i == 1) {
                value = BigInteger.ONE;
            } else {
                value = a.add(b);
                a = b;
                b = value;
            }
            numbers[i] = new FibonacciNumber(i + 1, value);
        }

        System.out.println("\nThe first " + N + " Fibonacci numbers:");
        for (FibonacciNumber num : numbers) {
            System.out.print(num.getValue() + " ");
        }
        System.out.println("\n");

        System.out.println("Fibonacci numbers that are squares:");
        boolean found = false;
        for (FibonacciNumber num : numbers) {
            if (num.Is_square()) {
                System.out.println("F(" + num.getIndex() + ") = " + num.getValue() + " - squares");
                found = true;
            }
        }

        if (!found) {
            System.out.println("There are no squares among the first " + N + " Fibonacci numbers.");
        }
    }
}


