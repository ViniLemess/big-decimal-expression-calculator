package com.vinilemess.bigdecimalexpressioncalculator;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ExpressionTest {

    @ParameterizedTest(name = "[{index}] {0} should be equal to {1}")
    @ArgumentsSource(ExpressionsProvider.class)
    void shouldCalculateAllProvidedExpressionSuccessfully(String stringExpression, BigDecimal expectedResult) {

        Expression expression = new Expression(stringExpression);
        assertEquals(expectedResult, expression.evaluate());
    }

    //TODO Implement parenthesis scenarios.
    private static class ExpressionsProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            return Stream.of(
                    arguments("2 + 2", BigDecimal.valueOf(4)),
                    arguments("-2 + 2", BigDecimal.ZERO),
                    arguments("2 + 2 - 2", BigDecimal.valueOf(2)),
                    arguments("2 + 2 - -2", BigDecimal.valueOf(6)),
                    arguments("2 + 2 * 2", BigDecimal.valueOf(6)),
                    arguments("2 * 2 + 2", BigDecimal.valueOf(6)),
                    arguments("2 - 2 + 2", BigDecimal.valueOf(2)),
                    arguments("2 * 2 - 2", BigDecimal.valueOf(2)),
                    arguments("2 / 2 * 2", BigDecimal.valueOf(2)),
                    arguments("10 / 2 - 3", BigDecimal.valueOf(2)),
                    arguments("-2 * -2", BigDecimal.valueOf(4)),
                    arguments("-2 * 2", BigDecimal.valueOf(-4)),
                    arguments("10 / -2", BigDecimal.valueOf(-5)),
                    arguments("-10 / -2", BigDecimal.valueOf(5)),
                    arguments("2 + 2 * 2 - 2", BigDecimal.valueOf(4)),
                    arguments("2 * 3 + 4 / 2", BigDecimal.valueOf(8)),
                    arguments("2 * 3 + 4 / 2 - 1", BigDecimal.valueOf(7))
//                    arguments("(2 + 2) * 2 - 2", BigDecimal.valueOf(6)),
//                    arguments("2 + 2 * (2 - 2)", BigDecimal.valueOf(2)),
//                    arguments("(2 + 2) * (2 - 2)", BigDecimal.ZERO),
//                    arguments("2 * (3 + 4) / 2", BigDecimal.valueOf(7))
            );
        }
    }
}