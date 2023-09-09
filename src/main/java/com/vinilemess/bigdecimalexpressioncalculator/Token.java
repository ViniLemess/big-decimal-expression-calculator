package com.vinilemess.bigdecimalexpressioncalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

class Token {
    public static Token number(BigDecimal value) {
        return new NumberToken(value);
    }

    public static Token operator(Operator operator) {
        return new OperatorToken(operator);
    }

    public static class NumberToken extends Token {
        private final BigDecimal value;

        public static NumberToken number(BigDecimal value) {
            return new NumberToken(value);
        }

        private NumberToken(BigDecimal value) {
            this.value = value;
        }

        public BigDecimal getValue() {
            return value;
        }
    }

    public static class OperatorToken extends Token {
        private final Operator operator;

        private OperatorToken(Operator operator) {
            this.operator = operator;
        }

        public Operator getOperator() {
            return operator;
        }
    }

    public enum Operator {
        ADD(1) {
            @Override
            public BigDecimal calculate(BigDecimal a, BigDecimal b) {
                return a.add(b);
            }
        },
        SUBTRACT(1) {
            @Override
            public BigDecimal calculate(BigDecimal a, BigDecimal b) {
                return a.subtract(b);
            }
        },
        MULTIPLY(2) {
            @Override
            public BigDecimal calculate(BigDecimal a, BigDecimal b) {
                return a.multiply(b);
            }
        },
        DIVIDE(2) {
            @Override
            public BigDecimal calculate(BigDecimal a, BigDecimal b) {
                return a.divide(b, RoundingMode.HALF_EVEN);
            }
        };

        private final int precedence;

        public static final String ALLOWED_OPERATORS = "+-*/";

        Operator(int precedence) {
            this.precedence = precedence;
        }

        public int getPrecedence() {
            return precedence;
        }

        public static Operator fromString(String token) {
            return switch (token) {
                case "+" -> ADD;
                case "-" -> SUBTRACT;
                case "*" -> MULTIPLY;
                case "/" -> DIVIDE;
                default -> throw new IllegalArgumentException("Invalid operator: " + token);
            };
        }

        public abstract BigDecimal calculate(BigDecimal a, BigDecimal b);
    }
}
