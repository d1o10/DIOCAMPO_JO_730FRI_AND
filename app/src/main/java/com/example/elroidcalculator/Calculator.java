package com.example.elroidcalculator;

public class Calculator {
    public String add(double n1, double n2) {
        return String.valueOf(n1 + n2);
    }

    public String subtract(double n1, double n2) {
        return String.valueOf(n1 - n2);
    }

    public String multiply(double n1, double n2) {
        return String.valueOf(n1 * n2);
    }

    public String divide(double n1, double n2) {
        if (n2 == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return String.valueOf(n1 / n2);
    }
}
