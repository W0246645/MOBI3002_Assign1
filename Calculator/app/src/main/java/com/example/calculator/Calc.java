package com.example.calculator;

public class Calc {
    public double Calculate(double left, double right, char operator) {
        double output = 0.0;
        switch (operator) {
            case '+':
                output = left + right;
                break;
            case '-':
                output = left - right;
                break;
            case '÷':
                output = left / right;
                break;
            case '×':
                output = left * right;
                break;

        }
        return output;
    }
}
