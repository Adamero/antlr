public class Integral {
    static double y(double x)
    {
        return (1 / (1 + x));
    }

    static double BooleRule(Double a, Double b)
    {


        int n = 4;
        int h;


        h = (int) ((b - a) / n);
        double sum = 0;


        double bl = (7 * y(a) + 32 * y(a + h)
                + 12 * y(a + 2 * h)
                + 32 * y(a + 3 * h)
                + 7 * y(a + 4 * h))
                * 2 * h / 45;

        sum = sum + bl;
        return sum;
    }

    // Driver code

}
