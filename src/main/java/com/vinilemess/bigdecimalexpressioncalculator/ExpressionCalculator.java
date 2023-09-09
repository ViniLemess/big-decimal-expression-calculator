package com.vinilemess.bigdecimalexpressioncalculator;

import java.math.BigDecimal;

public class ExpressionCalculator {

    private ExpressionCalculator() {
    }

    public static ExpressionBuilder value(final BigDecimal value) {
        return ExpressionBuilder.initiateExpression(value);
    }

    public static class ExpressionBuilder {

        private String expression;

        private ExpressionBuilder(final String expression) {
            this.expression = expression;
        }

        public static ExpressionBuilder initiateExpression(final BigDecimal value) {
            return new ExpressionBuilder(value.toString());
        }

        public ExpressionBuilder plus(final BigDecimal value) {
            expression = String.format("%s + %s", expression, value.toString());
            return this;
        }

        public ExpressionBuilder minus(final BigDecimal value) {
            expression = String.format("%s - %s", expression, value.toString());
            return this;
        }

        public ExpressionBuilder times(final BigDecimal value) {
            expression = String.format("%s * %s", expression, value.toString());
            return this;
        }

        public ExpressionBuilder dividedBy(final BigDecimal value) {
            expression = String.format("%s / %s", expression, value.toString());
            return this;
        }

        public BigDecimal results() {
            return new Expression(this.expression).evaluate();
        }
    }
}
