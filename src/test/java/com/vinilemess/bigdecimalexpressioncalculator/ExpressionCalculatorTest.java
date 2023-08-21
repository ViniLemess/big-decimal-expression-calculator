package com.vinilemess.bigdecimalexpressioncalculator;

import com.vinilemess.bigdecimalexpressioncalculator.ExpressionCalculator.ExpressionBuilder;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ExpressionCalculatorTest {

    @ParameterizedTest(name = "[{index}] Expression should result in {1}")
    @ArgumentsSource(ExpressionsProvider.class)
    void shouldCalculateAllProvidedExpressionSuccessfully(ExpressionBuilder expression, BigDecimal expectedResult) {

        assertEquals(expectedResult, expression.results());
    }

    private static class ExpressionsProvider implements ArgumentsProvider {
        public static final BigDecimal BIG_DECIMAL_NEGATIVE_TWO = BigDecimal.valueOf(-2);
        public static final BigDecimal BIG_DECIMAL_TWO = BigDecimal.valueOf(2);
        public static final BigDecimal BIG_DECIMAL_THREE = BigDecimal.valueOf(3);
        public static final BigDecimal BIG_DECIMAL_FOUR = BigDecimal.valueOf(4);
        public static final BigDecimal BIG_DECIMAL_SIX = BigDecimal.valueOf(6);

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    arguments(twoPlusTwo(), BIG_DECIMAL_FOUR),
                    arguments(negativeTwoPlusTwo(), BigDecimal.ZERO),
                    arguments(twoPlusTwoMinusTwo(), BIG_DECIMAL_TWO),
                    arguments(twoPlusTwoMinusNegativeTwo(), BIG_DECIMAL_SIX),
                    arguments(twoTimesTwoPlusTwo(), BIG_DECIMAL_SIX),
                    arguments(twoMinusTwoPlusTwo(), BIG_DECIMAL_TWO),
                    arguments(twoTimesTwoMinusTwo(), BIG_DECIMAL_TWO),
                    arguments(twoDividedByTwoTimesTwo(), BIG_DECIMAL_TWO),
                    arguments(tenDividedByTwoMinusThree(), BIG_DECIMAL_TWO),
                    arguments(negativeTwoTimesNegativeTwo(), BIG_DECIMAL_FOUR),
                    arguments(negativeTwoTimesTwo(), BIG_DECIMAL_FOUR.negate()),
                    arguments(tenDividedByNegativeTwo(), BigDecimal.valueOf(-5)),
                    arguments(negativeTenDividedByNegativeTwo(), BigDecimal.valueOf(5)),
                    arguments(twoPlusTwoTimesTwoMinusTwo(), BIG_DECIMAL_FOUR),
                    arguments(twoTimesThreePlusFourDividedByTwo(), BigDecimal.valueOf(8)),
                    arguments(twoTimesThreePlusFourDividedByTwoMinusOne(), BigDecimal.valueOf(7))
            );
        }

        private static ExpressionBuilder twoPlusTwo() {
            return ExpressionCalculator.value(BIG_DECIMAL_TWO)
                    .plus(BIG_DECIMAL_TWO);
        }

        private static ExpressionBuilder negativeTwoPlusTwo() {
            return ExpressionCalculator.value(BIG_DECIMAL_NEGATIVE_TWO)
                    .plus(BIG_DECIMAL_TWO);
        }

        private static ExpressionBuilder twoPlusTwoMinusTwo() {
            return ExpressionCalculator.value(BIG_DECIMAL_TWO)
                    .plus(BIG_DECIMAL_TWO)
                    .minus(BIG_DECIMAL_TWO);
        }

        private static ExpressionBuilder twoPlusTwoMinusNegativeTwo() {
            return ExpressionCalculator.value(BIG_DECIMAL_TWO)
                    .plus(BIG_DECIMAL_TWO)
                    .minus(BIG_DECIMAL_NEGATIVE_TWO);
        }

        private static ExpressionBuilder twoTimesTwoPlusTwo() {
            return ExpressionCalculator.value(BIG_DECIMAL_TWO)
                    .times(BIG_DECIMAL_TWO)
                    .plus(BIG_DECIMAL_TWO);
        }

        private static ExpressionBuilder twoMinusTwoPlusTwo() {
            return ExpressionCalculator.value(BIG_DECIMAL_TWO)
                    .minus(BIG_DECIMAL_TWO)
                    .plus(BIG_DECIMAL_TWO);
        }

        private static ExpressionBuilder twoTimesTwoMinusTwo() {
            return ExpressionCalculator.value(BIG_DECIMAL_TWO)
                    .times(BIG_DECIMAL_TWO)
                    .minus(BIG_DECIMAL_TWO);
        }

        private static ExpressionBuilder twoDividedByTwoTimesTwo() {
            return ExpressionCalculator.value(BIG_DECIMAL_TWO)
                    .dividedBy(BIG_DECIMAL_TWO)
                    .times(BIG_DECIMAL_TWO);
        }

        private static ExpressionBuilder tenDividedByTwoMinusThree() {
            return ExpressionCalculator.value(BigDecimal.TEN)
                    .dividedBy(BIG_DECIMAL_TWO)
                    .minus(BIG_DECIMAL_THREE);
        }

        private static ExpressionBuilder negativeTwoTimesNegativeTwo() {
            return ExpressionCalculator.value(BIG_DECIMAL_NEGATIVE_TWO)
                    .times(BIG_DECIMAL_NEGATIVE_TWO);
        }

        private static ExpressionBuilder negativeTwoTimesTwo() {
            return ExpressionCalculator.value(BIG_DECIMAL_NEGATIVE_TWO)
                    .times(BIG_DECIMAL_TWO);
        }

        private static ExpressionBuilder tenDividedByNegativeTwo() {
            return ExpressionCalculator.value(BigDecimal.TEN)
                    .dividedBy(BIG_DECIMAL_NEGATIVE_TWO);
        }

        private static ExpressionBuilder negativeTenDividedByNegativeTwo() {
            return ExpressionCalculator.value(BigDecimal.valueOf(-10))
                    .dividedBy(BIG_DECIMAL_NEGATIVE_TWO);
        }

        private static ExpressionBuilder twoPlusTwoTimesTwoMinusTwo() {
            return ExpressionCalculator.value(BIG_DECIMAL_TWO)
                    .plus(BIG_DECIMAL_TWO)
                    .times(BIG_DECIMAL_TWO)
                    .minus(BIG_DECIMAL_TWO);
        }

        private static ExpressionBuilder twoTimesThreePlusFourDividedByTwo() {
            return ExpressionCalculator.value(BIG_DECIMAL_TWO)
                    .times(BIG_DECIMAL_THREE)
                    .plus(BIG_DECIMAL_FOUR)
                    .dividedBy(BIG_DECIMAL_TWO);
        }

        private static ExpressionBuilder twoTimesThreePlusFourDividedByTwoMinusOne() {
            return ExpressionCalculator.value(BIG_DECIMAL_TWO)
                    .times(BIG_DECIMAL_THREE)
                    .plus(BIG_DECIMAL_FOUR)
                    .dividedBy(BIG_DECIMAL_TWO)
                    .minus(BigDecimal.ONE);
        }
    }
}