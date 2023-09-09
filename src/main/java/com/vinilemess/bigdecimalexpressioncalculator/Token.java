package com.vinilemess.bigdecimalexpressioncalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

class Token {
    public static Token number(final BigDecimal value) {
        return new NumberToken(value);
    }

    public static Token operator(final Operator operator) {
        return new OperatorToken(operator);
    }

    public static class NumberToken extends Token {
        private final BigDecimal value;

        public static NumberToken number(final BigDecimal value) {
            return new NumberToken(value);
        }

        private NumberToken(final BigDecimal value) {
            this.value = value;
        }

        public BigDecimal getValue() {
            return value;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NumberToken that = (NumberToken) o;
            return Objects.equals(value, that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    public static class OperatorToken extends Token {
        private final Operator operator;

        private OperatorToken(final Operator operator) {
            this.operator = operator;
        }

        public Operator getOperator() {
            return operator;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            OperatorToken that = (OperatorToken) o;
            return operator == that.operator;
        }

        @Override
        public int hashCode() {
            return Objects.hash(operator);
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
                return a.divide(b, 16, RoundingMode.HALF_EVEN);
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

        public static Operator fromString(final String token) {
            return switch (token) {
                case "+" -> ADD;
                case "-" -> SUBTRACT;
                case "*" -> MULTIPLY;
                case "/" -> DIVIDE;
                default -> throw new IllegalArgumentException("Invalid operator: " + token);
            };
        }

        public abstract BigDecimal calculate(final BigDecimal a, final BigDecimal b);
    }
}
