public class Fraction {
    private int numerator;
    private int denominator;

    // Constructor
    public Fraction(int n, int d) {
        if (d == 0) {
            throw new ArithmeticException("Denominator cannot be zero");
        }
        this.numerator = n;
        this.denominator = d;
        reduce();
    }

    // Getters
    public int getNum() {
        return numerator;
    }

    public int getDenom() {
        return denominator;
    }

    // Setters
    public void setNum(int n) {
        this.numerator = n;
    }

    public void setDenom(int d) {
        if (d == 0) {
            throw new ArithmeticException("Denominator cannot be zero");
        }
        this.denominator = d;
    }
    
    
    public Fraction add(Fraction a) {
        int newNumerator = this.numerator * a.denominator + a.numerator * this.denominator;
        int newDenominator = this.denominator * a.denominator;
        return new Fraction(newNumerator, newDenominator);
    }

    // Check equality
    public boolean equals(Fraction a) {
        return this.numerator * a.denominator == a.numerator * this.denominator;
    }

    // toString method
    @Override
    public String toString() {
        return numerator + "/" + denominator;
    }

    // Reduce fraction to lowest terms
    private void reduce() {
        int gcd = gcd(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;
    }

    // Helper method to find GCD using Euclidean Algorithm
    private int gcd(int a, int b) {
        if (b == 0) {
            return Math.abs(a);
        }
        return gcd(b, a % b);
    }
}
