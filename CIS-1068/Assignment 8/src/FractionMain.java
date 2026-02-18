public class FractionMain {
    public static void main(String[] args) {
        Fraction f1 = new Fraction(3, 4);
        Fraction f2 = new Fraction(1, 4);

        System.out.println("f1: " + f1);
        System.out.println("f2: " + f2);
     
        Fraction sum = f1.add(f2);
        System.out.println("Sum: " + sum);

     
        System.out.println("f1 equals f2 ? " + f1.equals(f2));
        System.out.println();
        
        f1.setNum(2);
        f1.setDenom(5);
        
        System.out.println("New numerator f1: " + f1.getNum());
        System.out.println("New denominator f1: " + f1.getDenom());
        
        System.out.println("Updated f1: " + f1);
        System.out.println();

        // Additional testing for thorough validation
        int originalNumerator = 5;
        int originalDenominator = 10;
        Fraction f3 = new Fraction(originalNumerator, originalDenominator);        
        System.out.println("f3: " + originalNumerator + "/" + originalDenominator);
        System.out.println("f3 (reduced): " + f3);
        System.out.println();

        Fraction f4 = new Fraction(7, 8);
        Fraction f5 = new Fraction(14, 16);
        System.out.println("f4: " + f1);
        System.out.println("f5: " + f2);
        Fraction sum2 = f4.add(f5);
        System.out.println("Sum of f4 and f5: " + sum2);

        System.out.println("f4 equals f5? " + f4.equals(f5));

}

    
}