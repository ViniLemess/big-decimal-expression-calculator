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

    /**
     A recursive method that computes the result of the expression using two deques:
     one for numbers (operands) and another for operators (operators).
     **/
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
            final CalculationResult result = calculateOperation(operands, operators, operatorToken);
            operands = result.operands();
            operators = result.operators();
        }

        return removeTrailingZeros(computeResultFromTokens(newRemainingTokens, operands, operators));
    }

    private BigDecimal removeTrailingZeros(final BigDecimal value) {
        return value.stripTrailingZeros();
    }

    /**
     Evaluates the top of the stack based on precedence, ensuring that the
     stack of operators and operands is correctly manipulated before moving on.
     **/
    private CalculationResult calculateOperation(final Deque<NumberToken> operands,
                                                 final Deque<OperatorToken> operators,
                                                 final OperatorToken operatorToken) {
        Deque<OperatorToken> newOperators = new ArrayDeque<>(operators);
        Deque<NumberToken> newOperands = new ArrayDeque<>(operands);
        while (shouldProcessCurrentOperators(newOperators, operatorToken)) {
            final CalculationResult result = applyOperatorAndCalculateResult(newOperands, newOperators);
            newOperators = result.operators();
            newOperands = result.operands();
        }
        newOperators.push(operatorToken);
        return new CalculationResult(newOperands, newOperators);
    }

    /**
     Determines if the current operator should be processed based on its precedence.
     **/
    private static boolean shouldProcessCurrentOperators(final Deque<OperatorToken> operatorStack, final OperatorToken operatorToken) {
        if (!operatorStack.isEmpty()) {
            final Operator stackFirstOperator = operatorStack.peekFirst().getOperator();
            final Operator currentOperator = operatorToken.getOperator();
            return stackFirstOperator.getPrecedence() >= currentOperator.getPrecedence();
        }
        return false;
    }

   /**
    Evaluates the final result once all tokens have been processed.
    **/
    private BigDecimal evaluateFinalResult(final Deque<NumberToken> operands, final Deque<OperatorToken> operators) {
        if (operators.isEmpty()) {
            return operands.pop().getValue();
        }
        final CalculationResult result = applyOperatorAndCalculateResult(operands, operators);
        return evaluateFinalResult(result.operands(), result.operators());
    }

    /**
     Applies the operator at the top of the stack to the two operands at the top of the stack.
     **/
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
