package generation;
import java.util.Map;

public abstract class Expression {
    
    public abstract String toTex();
    public abstract Fraction calculate();


    public static final Map<Integer, String> NAMES_OF_BINARY_OPERATIONS = Map.of(
        0, "Сложение",
        1, "Вычитание",
        2, "Умножение",
        3, "Деление"
    );
    
    
    public static class Add extends Expression {

        private Expression left;
        private Expression right;

        public Add(Expression left, Expression right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public String toTex() {
            return left.toTex() + " + " + right.toTex();
        }

        @Override
        public Fraction calculate() {
            return left.calculate().add(right.calculate());
        }

    }

    public static class Subtract extends Expression {

        private Expression left;
        private Expression right;

        public Subtract(Expression left, Expression right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public String toTex() {
            return left.toTex() + " - " + right.toTex();
        }

        @Override
        public Fraction calculate() {
            return left.calculate().sub(right.calculate());
        }

    }

    public static class Multiply extends Expression {

        private Expression left;
        private Expression right;

        public Multiply(Expression left, Expression right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public String toTex() {
            return left.toTex() + " \\cdot " + right.toTex();
        }

        @Override
        public Fraction calculate() {
            return left.calculate().mul(right.calculate());
        }

    }

    public static class Divide extends Expression {

        private Expression left;
        private Expression right;

        public Divide(Expression left, Expression right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public String toTex() {
            return "\\frac{" + left.toTex() + "}{" + right.toTex() + "}";
        }

        @Override
        public Fraction calculate() {
            return left.calculate().div(right.calculate());
        }

    }

    public static class Number extends Expression {
        
        private int value;

        public Number(int value) {
            this.value = value;
        }

        @Override
        public String toTex() {
            return Integer.toString(value);
        }

        @Override
        public Fraction calculate() {
            return new Fraction(value, 1);
        }
    }

    public static class Brackets extends Expression {
        
        private Expression inner;

        public Brackets(Expression inner) {
            this.inner = inner;
        }

        @Override
        public String toTex() {
            return "(" + inner.toTex() + ")";
        }

        @Override
        public Fraction calculate() {
            return inner.calculate();
        }
    }
}
