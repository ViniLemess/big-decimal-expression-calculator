package com.vinilemess.bigdecimalexpressioncalculator;

import com.vinilemess.bigdecimalexpressioncalculator.Token.NumberToken;
import com.vinilemess.bigdecimalexpressioncalculator.Token.Operator;
import com.vinilemess.bigdecimalexpressioncalculator.Token.OperatorToken;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Deque;

import static com.vinilemess.bigdecimalexpressioncalculator.ExpressionTokenizer.tokenizeExpression;

class Expression {
    private final Deque<Token> tokens;

    public Expression(final String stringExpression) {
        tokens = tokenizeExpression(stringExpression);
    }

    public BigDecimal evaluate() {
        return computeResultFromTokens(tokens, new ArrayDeque<>(), new ArrayDeque<>());
    }

    private BigDecimal computeResultFromTokens(final Deque<Token> remainingTokens,
                                               Deque<NumberToken> operands,
                                               Deque<OperatorToken> operators) {
        if (remainingTokens.isEmpty()) {
            return evaluateFinalResult(operands, operators);
        }

        final Token token = remainingTokens.removeFirst();
        final Deque<Token> newRemainingTokens = new ArrayDeque<>(remainingTokens);

        if (token instanceof NumberToken numberToken) {
            operands.push(numberToken);
        } else if (token instanceof OperatorToken operatorToken) {
            CalculationResult result = calculateOperation(operands, operators, operatorToken);
            operands = result.operands();
            operators = result.operators();
        }

        return computeResultFromTokens(newRemainingTokens, operands, operators);
    }

    private CalculationResult calculateOperation(final Deque<NumberToken> operands,
                                                 final Deque<OperatorToken> operators,
                                                 final OperatorToken token) {
        Deque<OperatorToken> newOperators = new ArrayDeque<>(operators);
        Deque<NumberToken> newOperands = new ArrayDeque<>(operands);
        while (shouldProcessCurrentOperators(newOperators, token)) {
            final CalculationResult result = applyOperatorAndCalculateResult(newOperands, newOperators);
            newOperators = result.operators();
            newOperands = result.operands();
        }
        newOperators.push(token);
        return new CalculationResult(newOperands, newOperators);
    }

    private static boolean shouldProcessCurrentOperators(final Deque<OperatorToken> operatorStack, final OperatorToken token) {
        return !operatorStack.isEmpty() && operatorStack.peek().getOperator().getPrecedencia() >= token.getOperator().getPrecedencia();
    }

    private BigDecimal evaluateFinalResult(final Deque<NumberToken> operands, final Deque<OperatorToken> operators) {
        if (operators.isEmpty()) {
            return operands.pop().getValue();
        }
        final CalculationResult result = applyOperatorAndCalculateResult(operands, operators);
        return evaluateFinalResult(result.operands(), result.operators());
    }


    private CalculationResult applyOperatorAndCalculateResult(final Deque<NumberToken> operands, final Deque<OperatorToken> operators) {
        final OperatorToken operatorToken = operators.pop();
        final NumberToken rightOperand = operands.pop();
        final NumberToken leftOperand = operands.pop();

        final Operator operator = operatorToken.getOperator();
        final BigDecimal result = operator.calculate(leftOperand.getValue(), rightOperand.getValue());

        final Deque<NumberToken> newOperands = new ArrayDeque<>(operands);
        newOperands.push(NumberToken.number(result));
        final Deque<OperatorToken> newOperators = new ArrayDeque<>(operators);

        return new CalculationResult(newOperands, newOperators);
    }

    private record CalculationResult(Deque<NumberToken> operands, Deque<OperatorToken> operators) {
    }
}
