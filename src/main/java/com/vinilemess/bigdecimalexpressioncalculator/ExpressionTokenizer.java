package com.vinilemess.bigdecimalexpressioncalculator;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.vinilemess.bigdecimalexpressioncalculator.Token.Operator.ALLOWED_OPERATORS;
import static com.vinilemess.bigdecimalexpressioncalculator.Token.Operator.fromString;

class ExpressionTokenizer {
    private static final String OPERAND_OPERATOR_REGEX = "(-?\\d+|\\+|\\-|\\*|\\/)";
    private static final Pattern OPERAND_OPERATOR_PATTERN = Pattern.compile(OPERAND_OPERATOR_REGEX);
    public static Deque<Token> tokenizeExpression(String stringExpression) {
        Deque<Token> tokens = new ArrayDeque<>();
        Matcher matcher = OPERAND_OPERATOR_PATTERN.matcher(stringExpression);

        while (matcher.find()) {
            String token = matcher.group();
            classifyAndAddToken(token, tokens);
        }
        return tokens;
    }

    private static void classifyAndAddToken(String token, Deque<Token> tokens) {
        if (!token.isEmpty()) {
            if (ALLOWED_OPERATORS.contains(token)) {
                tokens.addLast(Token.operator(fromString(token.toUpperCase())));
            } else {
                tokens.addLast(Token.number(new BigDecimal(token)));
            }
        }
    }
}
