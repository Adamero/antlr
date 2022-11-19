import java.util.Stack;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class MainListen extends CalculatorBaseListener {

    private final Stack<Integer> stack = new Stack<>();

    public Integer getResult() {
        return stack.peek();
    }
    //test

    @Override
    public void exitExpression(CalculatorParser.ExpressionContext ctx) {
        if(ctx.plusExpression().size() != 1){
            float right = stack.pop();
            float left = stack.pop();
            if(ctx.INTEGRAL().size() != 0){
                //stack.push(left + right);
                stack.push((int) Integral.BooleRule(left,right));
            }else{
                //stack.push(left - right);
                stack.push((int) Integral.BooleRule(right,left));
            }
        }
    }
    //test

    @Override
    public void exitPlusExpression(CalculatorParser.PlusExpressionContext ctx) {
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

    public void exitPowExpression(CalculatorParser.PowExpressionContext ctx) {
        if(ctx.INT().size() == 1) {
            stack.push(Integer.valueOf(ctx.INT(0).getText()));
        }else{
            double a = Double.valueOf(ctx.INT(0).getText());
            double b = Double.valueOf(ctx.INT(1).getText());
            if(ctx.INT().size() != 0){
                stack.push((int)Math.pow(a,b));
            }else {
                stack.push((int)Math.pow(a,1.0/b));
            }
        }
    }



    public static void main(String[] args) throws Exception {
        CharStream charStreams = CharStreams.fromFileName("D:\\java\\antlr\\src\\example.txt");
        CalculatorLexer lexer = new CalculatorLexer(charStreams);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        System.out.println(tokens.getText());

        CalculatorParser parser = new CalculatorParser(tokens);
        ParseTree tree = parser.expression(); // parse

        ParseTreeWalker walker = new ParseTreeWalker();
        MainListen listener = new MainListen();
        walker.walk(listener, tree);
        System.out.println(listener.getResult());
    }


}