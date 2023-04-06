package stark.dataworks.basic.mathematics;

import stark.dataworks.basic.collections.LinkedList;
import stark.dataworks.basic.collections.Stack;

import java.util.Scanner;

/**
 * The {@link ExpressionCalculator} class provides static methods to evaluate arithmetic expressions with numeric values
 * and operators [only "( ) + - * / "];
 *
 * TODO: Refactor this class using Tokenizer and Parser.
 */
public class ExpressionCalculator
{
    private static String[] tokenize(String expression)
    {
        // Remove all leading and ending spaces.
        expression = expression.trim();

        // Add a space between a number and a non-number element.
        for (int i = 0; i < expression.length(); i++)
        {
            if ((expression.charAt(i) < '0') || (expression.charAt(i) > '9'))
            {
                expression = expression.substring(0, i) + " " + expression.charAt(i) + " " + expression.substring(i + 1);
                i += 2;
            }
        }

        Scanner tokenReader = new Scanner(expression);
        LinkedList<String> list = new LinkedList<>();
        while (tokenReader.hasNext())
            list.addLast(tokenReader.next());

        String[] tokens = new String[list.count()];
        list.copyTo(tokens);
        return tokens;
    }

    private static boolean isNumber(String token)
    {
        for (int i = 0; i < token.length(); i++)
        {
            if (!Character.isDigit(token.charAt(i)))
                return false;
        }
        return true;
    }

    private static boolean isOperator(String token)
    {
        char c = token.charAt(0);
        return (c == '+') || (c == '-') || (c == '*') || (c == '/');
    }

    private static double calculate(double leftOperand, char operator, double rightOperand)
    {
        switch (operator)
        {
            case '+':
                return leftOperand + rightOperand;
            case '-':
                return leftOperand - rightOperand;
            case '*':
                return leftOperand * rightOperand;
            case '/':
                return leftOperand / rightOperand;
        }
        return 0;
    }

    private static double calculate(String[] tokens)
    {
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (String token : tokens)
        {
            if (isNumber(token))
                numbers.push(Double.parseDouble(token));
            else if ((token.charAt(0) == '+') || (token.charAt(0) == '-'))
            {
                calculate(numbers, operators, false);
                operators.push(token.charAt(0));
            }
            else if ((token.charAt(0) == '*') || (token.charAt(0) == '/'))
            {
                while ((!operators.isEmpty()) && (operators.peek() != '(') && (operators.peek() != '+') && (operators.peek() != '-'))
                {
                    double b = numbers.pop();
                    double a = numbers.pop();
                    numbers.push(calculate(a, operators.pop(), b));
                }

                if ((!operators.isEmpty()) && (operators.peek() == '('))
                    operators.pop();

                operators.push(token.charAt(0));
            }
            else if (token.charAt(0) == '(')
                operators.push(token.charAt(0));
            else if (token.charAt(0) == ')')
                calculate(numbers, operators, true);
        }

        while (!operators.isEmpty())
        {
            double b = numbers.pop();
            double a = numbers.pop();
            numbers.push(calculate(a, operators.pop(), b));
        }

        return numbers.peek();
    }

    private static void calculate(Stack<Double> numbers, Stack<Character> operators, boolean meetRightParenthesis)
    {
        while ((!operators.isEmpty()) && (operators.peek() != '('))
        {
            double rightOperand = numbers.pop();
            double leftOperand = numbers.pop();
            numbers.push(calculate(leftOperand, operators.pop(), rightOperand));
        }

        if ((!operators.isEmpty()) && (operators.peek() == '(') && meetRightParenthesis)
            operators.pop();
    }

    public static boolean isLegalExpression(String expression)
    {
        return isLegalExpression(tokenize(expression));
    }

    private static boolean isLegalExpression(String[] tokens)
    {
        // The first and last character of a legal expression is not an operator.
        char firstChar = tokens[0].charAt(0);
        char lastChar = tokens[tokens.length - 1].charAt(0);
        if ((firstChar == '+') || (firstChar == '-') || (firstChar == '*') || (firstChar == '/') || (lastChar == '+') || (lastChar == '-') || (lastChar == '*') || (lastChar == '/'))
            return false;

        // Each '(' should have a matched ')', '(' cannot be the last parenthesis, ')' cannot be the first parenthesis.
        Stack<Character> parenthesises = new Stack<>();
        for (String token : tokens)
        {
            if (token.charAt(0) == '(')
                parenthesises.push('(');
            else if (token.charAt(0) == ')')
            {
                if (parenthesises.isEmpty())
                    return false;
                parenthesises.pop();
            }
        }

        if (!parenthesises.isEmpty())
            return false;

        // Check if there is an illegal character.
        // Only operators ([+-*/]), parenthesises and digits are legal.
        StringBuilder expressionText = new StringBuilder();
        for (String token : tokens)
            expressionText.append(token);
        for (char c : expressionText.toString().toCharArray())
        {
            if ((c != '+') && (c != '-') && (c != '*') && (c != '/') && (c != '(') && (c != ')') && (!Character.isDigit(c)))
                return false;
        }

        for (int i = 0; i < tokens.length; i++)
        {
            char c = tokens[i].charAt(0);
            if (isNumber(tokens[i]))
            {
                if ((i > 0) && isNumber(tokens[i - 1]) || (i < tokens.length - 1) && isNumber(tokens[i + 1]))
                    return false;

                boolean hasOperatorOnLeft = i != 0;
                boolean hasOperatorOnRight = i != tokens.length - 1;

                if (hasOperatorOnLeft)
                {
                    char previousToken = tokens[i - 1].charAt(0);
                    if ((previousToken != '+') && (previousToken != '-') && (previousToken != '*') && (previousToken != '/'))
                        hasOperatorOnLeft = false;
                }

                if (hasOperatorOnRight)
                {
                    char nextToken = tokens[i + 1].charAt(0);
                    if ((nextToken != '+') && (nextToken != '-') && (nextToken != '*') && (nextToken != '/'))
                        hasOperatorOnRight = false;
                }

                // The expression is illegal
                if ((!hasOperatorOnLeft) && (!hasOperatorOnRight))
                    return false;
            }
            else if ((c == '+') || (c == '-') || (c == '*') || (c == '/'))
            {
                if ((i > 0) && isOperator(tokens[i - 1]) || (i < tokens.length - 1) && isOperator(tokens[i + 1]))
                    return false;

                boolean hasNumberOnLeft = i != 0;
                boolean hasNumberOnRight = i != tokens.length - 1;

                if (hasNumberOnLeft)
                {
                    if (!isNumber(tokens[i - 1]))
                        hasNumberOnLeft = false;
                }

                if (hasNumberOnRight)
                {
                    if (!isNumber(tokens[i + 1]))
                        hasNumberOnRight = false;
                }

                if ((!hasNumberOnLeft) && (!hasNumberOnRight))
                {
                    if (!(tokens[i - 1].charAt(0) == ')' && tokens[i + 1].charAt(0) == '('))
                        return false;
                }

                if (tokens[i - 1].charAt(0) == '(')
                    return false;
                if (tokens[i + 1].charAt(0) == ')')
                    return false;
            }
        }

        return true;
    }

    public static double calculate(String expression)
    {
        // TODO: Deal with leading '-'/'+'.

        String[] tokens = tokenize(expression);
        if (isLegalExpression(tokens))
            return calculate(tokens);
        else
            throw new IllegalArgumentException("The given expression is illegal.");
    }
}
