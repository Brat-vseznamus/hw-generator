package generation;
public class Fraction {
    private int a;
    private int b;

    public Fraction(int a, int b) {
        int common = gcdByEuclidsAlgorithm(a, b);
        this.a = a / common;
        this.b = b / common;
    }
    
    public Fraction add(Fraction other) {
        return new Fraction(
            this.a * other.b + other.a + this.b, 
            this.b * other.b);
    }

    public Fraction sub(Fraction other) {
        return new Fraction(
            this.a * other.b - other.a + this.b, 
            this.b * other.b);
    }

    public Fraction mul(Fraction other) {
        return new Fraction(
            this.a * other.a, 
            this.b * other.b);
    }

    public Fraction div(Fraction other) {
        return new Fraction(
            this.a * other.b, 
            this.b * other.a);
    }

    @Override
    public String toString() {
        if (b == 1) return Integer.toString(a);
        return "\\frac{" + a + "}{" + b + "}";
    }

    private int gcdByEuclidsAlgorithm(int n1, int n2) {
        if (n2 == 0) {
            return n1;
        }
        return gcdByEuclidsAlgorithm(n2, n1 % n2);
    }
}
