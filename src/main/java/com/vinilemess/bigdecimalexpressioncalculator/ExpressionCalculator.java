package com.vinilemess.bigdecimalexpressioncalculator;

import java.math.BigDecimal;

public class ExpressionCalculator {

    public static ExpressionBuilder value(BigDecimal value) {
        return ExpressionBuilder.initiateExpression(value);
    }

    public static class ExpressionBuilder {

        private String expression;

        private ExpressionBuilder(String expression) {
            this.expression = expression;
        }

        public static ExpressionBuilder initiateExpression(BigDecimal value) {
            return new ExpressionBuilder(value.toString());
        }

        public ExpressionBuilder plus(BigDecimal value) {
            expression = String.format("%s + %s", expression, value.toString());
            return this;
        }

        public ExpressionBuilder minus(BigDecimal value) {
            expression = String.format("%s - %s", expression, value.toString());
            return this;
        }

        public ExpressionBuilder times(BigDecimal value) {
            expression = String.format("%s * %s", expression, value.toString());
            return this;
        }

        public ExpressionBuilder dividedBy(BigDecimal value) {
            expression = String.format("%s / %s", expression, value.toString());
            return this;
        }

        public BigDecimal results() {
            return new Expression(this.expression).evaluate();
        }
    }
}
