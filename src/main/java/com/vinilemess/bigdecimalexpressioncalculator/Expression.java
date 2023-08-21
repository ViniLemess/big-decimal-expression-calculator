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

    public Expression(String stringExpression) {
        tokens = tokenizeExpression(stringExpression);
    }

    public BigDecimal evaluate() {
        Deque<NumberToken> operands = new ArrayDeque<>();
        Deque<OperatorToken> operators = new ArrayDeque<>();

        processTokens(operands, operators);

        finalizeCalculation(operands, operators);

        return operands.pop().getValue();
    }

    private void processTokens(Deque<NumberToken> operands, Deque<OperatorToken> operators) {
        while (hasTokens()) {
            Token token = tokens.removeFirst();

            if (token instanceof NumberToken numberToken) {
                handleNumberToken(operands, numberToken);
            } else if (token instanceof OperatorToken operatorToken) {
                handleOperatorToken(operands, operators, operatorToken);
            }
        }
    }

    private boolean hasTokens() {
        return !tokens.isEmpty();
    }

    private void handleNumberToken(Deque<NumberToken> operandStack, NumberToken token) {
        operandStack.push(token);
    }

    private void handleOperatorToken(Deque<NumberToken> operandStack, Deque<OperatorToken> operatorStack, OperatorToken token) {
        while (shouldProcessCurrentOperators(operatorStack, token)) {
            applyOperatorAndPushResult(operandStack, operatorStack);
        }
        operatorStack.push(token);
    }

    private static boolean shouldProcessCurrentOperators(Deque<OperatorToken> operatorStack, OperatorToken token) {
        return !operatorStack.isEmpty() && operatorStack.peek().getOperator().getPrecedencia() >= token.getOperator().getPrecedencia();
    }

    private void finalizeCalculation(Deque<NumberToken> operandStack, Deque<OperatorToken> operatorStack) {
        while (!operatorStack.isEmpty()) {
            applyOperatorAndPushResult(operandStack, operatorStack);
        }
    }

    private void applyOperatorAndPushResult(Deque<NumberToken> operandStack, Deque<OperatorToken> operatorStack) {
        OperatorToken operatorToken = operatorStack.pop();
        NumberToken rightOperand = operandStack.pop();
        NumberToken leftOperand = operandStack.pop();

        Operator operator = operatorToken.getOperator();

        BigDecimal result = operator.calculate(leftOperand.getValue(), rightOperand.getValue());
        operandStack.push(NumberToken.number(result));
    }
}
