package com.vinilemess.bigdecimalexpressioncalculator;

import java.util.Scanner;

public class LoopEvaluator {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        for (int index = 0; index <= 100; index++) {
            System.out.println("Informe a expressao que deseja calcular: ");
            String expression = scanner.nextLine();
            System.out.println("Resultado: " + new Expression(expression).evaluate());
        }
    }
}
