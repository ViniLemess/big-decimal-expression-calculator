package com.vinilemess.bigdecimalexpressioncalculator;

import java.util.Scanner;

public class LoopEvaluator {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(final String[] args) {
        while (true) {
            System.out.println("Type the expression you wish to calculate:");
            String expression = scanner.nextLine();
            System.out.println("Result: " + new Expression(expression).evaluate());
            System.out.println("Type 'S' to calculate another expression.");
            String shouldContinue = scanner.nextLine();
            if (shouldContinue.equals("S")) {
                continue;
            }
            break;
        }
    }
}
