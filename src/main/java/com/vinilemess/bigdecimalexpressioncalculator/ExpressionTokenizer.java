package com.vinilemess.bigdecimalexpressioncalculator;

import com.vinilemess.bigdecimalexpressioncalculator.Token.Operator;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.vinilemess.bigdecimalexpressioncalculator.Token.Operator.ALLOWED_OPERATORS;

class ExpressionTokenizer {
    private static final String OPERAND_OPERATOR_REGEX = "(-?\\d+(\\.\\d+)?|\\+|-|\\*|/)";
    private static final Pattern OPERAND_OPERATOR_PATTERN = Pattern.compile(OPERAND_OPERATOR_REGEX);
    public static Deque<Token> tokenizeExpression(final String stringExpression) {
        final Matcher matcher = OPERAND_OPERATOR_PATTERN.matcher(stringExpression);

        Deque<Token> tokens = new ArrayDeque<>();
        while (matcher.find()) {
            final String token = matcher.group();
            tokens = classifyAndAppendToken(token, tokens);
        }
        return tokens;
    }

    private static Deque<Token> classifyAndAppendToken(final String token, final Deque<Token> tokens) {
        final Deque<Token> newTokens = new ArrayDeque<>(tokens);

        if (!token.isEmpty()) {
            if (isOperator(token)) {
                newTokens.addLast(Token.operator(Operator.fromString(token.toUpperCase())));
            } else {
                newTokens.addLast(Token.number(new BigDecimal(token)));
            }
        }
        return newTokens;
    }

    private static boolean isOperator(final String token) {
        return ALLOWED_OPERATORS.contains(token);
    }
}
