import java.lang.constant.Constable;
import java.util.LinkedList;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.lang.Math;

public class Calculator extends CalculatorBaseListener {
    private final LinkedList<Double> firstStack = new LinkedList<>();
    private final LinkedList<Double> secondStack = new LinkedList<>();
    private final LinkedList<Double> thirdStack = new LinkedList<>();


    public Double getResult() {
        return thirdStack.pop();
    }

    @Override
    public void exitExpression(CalculatorParser.ExpressionContext ctx) {
        Double result = thirdStack.removeLast();
        for (int i = 1; i < ctx.getChildCount(); i = i + 2) {
            if (symbolEquals(ctx.getChild(i), CalculatorParser.PLUS)) {
                result = result + thirdStack.removeLast();
            } else {
                result = result - thirdStack.removeLast();
            }
        }
        thirdStack.push(result);
        System.out.println("Expression: \"" + ctx.getText() + "\" -> " + result);
    }

    @Override
    public void exitMultiplyingExpression(CalculatorParser.MultiplyingExpressionContext ctx) {
        Double result = secondStack.removeLast();

        for (int i = 1; i < ctx.getChildCount(); i = i + 2) {
            if (symbolEquals(ctx.getChild(i), CalculatorParser.TIMES)) {
                result = result * secondStack.removeLast();
            } else {
                result = result / secondStack.removeLast();
            }
        }

        thirdStack.push(result);
        System.out.println("MultiplyingExpression: \"" + ctx.getText() + "\" -> " + result);
    }


    @Override
    public void exitPowerExpression(CalculatorParser.PowerExpressionContext ctx) {
        Double result = firstStack.removeLast();
        for (int i = 1; i < ctx.getChildCount(); i = i + 2) {
            if (symbolEquals(ctx.getChild(i), CalculatorParser.POW)) {
                result = Math.pow(result,firstStack.removeLast());
            } else if (symbolEquals(ctx.getChild(i), CalculatorParser.LOG)){
                result = result * Math.log10(firstStack.removeLast());
            } else if (symbolEquals(ctx.getChild(i), CalculatorParser.MAX)){
                result = Math.max(result,firstStack.removeLast());
            } else if(symbolEquals(ctx.getChild(i), CalculatorParser.MIN)){
                result = Math.min(result,firstStack.removeLast());
            } else {
                result = result * Math.sqrt(firstStack.removeLast());
            }
        }
        secondStack.push(result);
        System.out.println("MultiplyingExpression: \"" + ctx.getText() + "\" -> " + result);
    }

    private boolean symbolEquals(ParseTree child, int symbol) {
        return ((TerminalNode) child).getSymbol().getType() == symbol;
    }

    @Override
    public void exitIntegralExpression(CalculatorParser.IntegralExpressionContext ctx) {
        //double value = Double.parseDouble(ctx.NUMBER().getText());
        if (ctx.MINUS() != null) {
            if (ctx.INT() != null){
                firstStack.push(-1 * Double.parseDouble(ctx.INT().getText()));
            }else {
                firstStack.push(-1 * Double.parseDouble(ctx.DOUBLE().getText()));
            }
        } else {
            if (ctx.INT() != null){
                firstStack.push(Double.parseDouble(ctx.INT().getText()));
            }else {
                firstStack.push(Double.parseDouble(ctx.DOUBLE().getText()));
            }
        }



        System.out.println("IntegralExpression: \"" + ctx.getText() + "\" ");
    }

    public static Constable calc(CharStream charStream) {
        CalculatorLexer lexer = new CalculatorLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        System.out.println(tokens.getText());

        CalculatorParser parser = new CalculatorParser(tokens);
        ParseTree tree = parser.expression();

        ParseTreeWalker walker = new ParseTreeWalker();
        Calculator calculatorListener = new Calculator();
        walker.walk(calculatorListener, tree);
        return calculatorListener.getResult();
    }

    public static Double calc(String expression) {
        return (Double) calc(CharStreams.fromString(expression));
    }

    public static void main(String[] args) throws Exception {
        CharStream charStreams = CharStreams.fromFileName("D:\\java\\antlr\\src\\example.txt");
        Double result = (Double) calc(charStreams);
        System.out.println("Result = " + result);
    }

    int a = 0;
}
