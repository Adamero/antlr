import java.util.Stack;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class MainListen extends CalculatorBaseListener {

    private final Stack<Integer> stack = new Stack<>();

    public Integer getResult() {
        return stack.peek();
    }

    @Override
    public void exitExpression(CalculatorParser.ExpressionContext ctx) {
        if(ctx.multiplyingExpression().size() != 1){
            int right = stack.pop();
            int left = stack.pop();
            if(ctx.PLUS().size() != 0){
                stack.push(left + right);
            }else{
                stack.push(left - right);
            }
        }
    }

    @Override
    public void exitMultiplyingExpression(CalculatorParser.MultiplyingExpressionContext ctx) {
        if(ctx.powExpression().size() != 1){
            int right = stack.pop();
            int left = stack.pop();
            if(ctx.TIMES().size() != 0){
                stack.push(left * right);
            }else{
                stack.push(left/right);
            }
        }
    }

    @Override
    public void exitPowExpression(CalculatorParser.PowExpressionContext ctx) {
        if(ctx.integralExpression().size() != 1){

            double right = stack.pop();
            double left = stack.pop();

            if(ctx.POW().size() != 0){
                stack.push((int)Math.pow(left,right));
            }else{
                double result = left * Math.sqrt(right);
                stack.push((int)result);
            }
        }
    }

    public void exitIntegralExpression(CalculatorParser.IntegralExpressionContext ctx) {
        if(ctx.INT().size() == 1) {
            stack.push(Integer.valueOf(ctx.INT(0).getText()));
        }else{
            float a = stack.push(Integer.valueOf(ctx.INT(0).getText()));
            float b = stack.push(Integer.valueOf(ctx.INT(1).getText()));
            if(ctx.INTEGRAL().size() != 0){
                stack.push((int) Integral.BooleRule(a,b));
            }else{
                stack.push((int) Integral.BooleRule(b,a));
            }
        }
    }



    public static void main(String[] args) throws Exception {
        CharStream charStreams = CharStreams.fromFileName("D:\\java\\antlr\\src\\example.txt");
        CalculatorLexer lexer = new CalculatorLexer(charStreams);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        //System.out.println(tokens.getText());

        CalculatorParser parser = new CalculatorParser(tokens);
        ParseTree tree = parser.expression(); // parse

        ParseTreeWalker walker = new ParseTreeWalker();
        MainListen listener = new MainListen();
        walker.walk(listener, tree);
        System.out.println(listener.getResult());
    }


}