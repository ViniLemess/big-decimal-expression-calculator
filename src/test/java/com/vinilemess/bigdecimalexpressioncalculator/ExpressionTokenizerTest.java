package com.vinilemess.bigdecimalexpressioncalculator;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Deque;
import java.util.List;

import static com.vinilemess.bigdecimalexpressioncalculator.Token.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExpressionTokenizerTest {

    @Test
    void shouldReturnCorrectTokensWhenTokenizeExpression() {
        final String expression = "1 + 2 - -3 * 4 / -5";

        final Collection<Token> expectedTokens = List.of(
                NumberToken.number(BigDecimal.ONE),
                OperatorToken.operator(Operator.ADD),
                NumberToken.number(new BigDecimal("2")),
                OperatorToken.operator(Operator.SUBTRACT),
                NumberToken.number(new BigDecimal("-3")),
                OperatorToken.operator(Operator.MULTIPLY),
                NumberToken.number(new BigDecimal("4")),
                OperatorToken.operator(Operator.DIVIDE),
                NumberToken.number(new BigDecimal("-5")));

        final Deque<Token> tokens = ExpressionTokenizer.tokenizeExpression(expression);

        assertTrue(expectedTokens.containsAll(tokens));
    }
}