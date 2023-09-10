package com.vinilemess.bigdecimalexpressioncalculator;

import java.math.BigDecimal;

public class ExpressionCalculatorApplication {
    public static void main(final String[] args) {
        if (args.length > 0) {
            if ("--help".equals(args[0])) {
                System.out.println("Display help text or run 'man jeval' for more information.");
                return;
            }
            try {
                final String expression = String.join(" ", args);
                final BigDecimal result = new Expression(expression).evaluate();
                System.out.println(result.toPlainString());
            } catch (Exception exception) {
                System.err.println(exception.getMessage());
            }
        } else {
            System.out.println("Usage: jeval \"expression\"");
        }
    }
}

