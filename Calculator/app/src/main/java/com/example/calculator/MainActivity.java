package com.example.calculator;

import static java.lang.Character.isDigit;

import androidx.appcompat.app.AppCompatActivity;
import android.os.*;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    Calc calc = new Calc();
    Button btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7,
            btn_8, btn_9, btn_multiply, btn_divide, btn_plus, btn_minus,
            btn_plusminus, btn_decimal, btn_equals, btn_clear, btn_backspace;
    TextView txt_display;

    double leftNum;
    String leftNumStr = "";
    boolean leftNumFull = false;
    char operator;
    double rightNum;
    boolean wasLastButtonOperator = true;
    StringBuilder rightNumStr = new StringBuilder();

    public void setOperator(View v) {
        switch (v.getId()) {
            case R.id.btn_plus:
                operator = '+';
                break;
            case R.id.btn_minus:
                operator = '-';
                break;
            case R.id.btn_divide:
                operator = '÷';
                break;
            case R.id.btn_multiply:
                operator = '×';
                break;
        }
    }

    public void clear() {
        leftNum = 0.0;
        leftNumStr = "";
        leftNumFull = false;
        operator = Character.MIN_VALUE;
        rightNum = 0.0;
        wasLastButtonOperator = true;
        rightNumStr.setLength(0);//clears the string builder
        txt_display.setText("");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_0 = findViewById(R.id.btn_0);
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);
        btn_5 = findViewById(R.id.btn_5);
        btn_6 = findViewById(R.id.btn_6);
        btn_7 = findViewById(R.id.btn_7);
        btn_8 = findViewById(R.id.btn_8);
        btn_9 = findViewById(R.id.btn_9);

        btn_multiply = findViewById(R.id.btn_multiply);
        btn_divide = findViewById(R.id.btn_divide);
        btn_plus = findViewById(R.id.btn_plus);
        btn_minus = findViewById(R.id.btn_minus);
        btn_plusminus  = findViewById(R.id.btn_plusminus);
        btn_decimal = findViewById(R.id.btn_decimal);
        btn_equals = findViewById(R.id.btn_equals);
        btn_clear = findViewById(R.id.btn_clear);
        btn_backspace = findViewById(R.id.btn_backspace);

        txt_display = findViewById(R.id.txt_display);

        //sets number buttons to same listener
        btn_0.setOnClickListener(onNumberClicked);
        btn_1.setOnClickListener(onNumberClicked);
        btn_2.setOnClickListener(onNumberClicked);
        btn_3.setOnClickListener(onNumberClicked);
        btn_4.setOnClickListener(onNumberClicked);
        btn_5.setOnClickListener(onNumberClicked);
        btn_6.setOnClickListener(onNumberClicked);
        btn_7.setOnClickListener(onNumberClicked);
        btn_8.setOnClickListener(onNumberClicked);
        btn_9.setOnClickListener(onNumberClicked);

        btn_multiply.setOnClickListener(onOperatorClicked);
        btn_divide.setOnClickListener(onOperatorClicked);
        btn_plus.setOnClickListener(onOperatorClicked);
        btn_minus.setOnClickListener(onOperatorClicked);
        btn_plusminus.setOnClickListener(onPlusminusClick);
        btn_decimal.setOnClickListener(onDecimalClick);
        btn_equals.setOnClickListener(onEqualsClicked);
        btn_clear.setOnClickListener(onClearClicked);
        btn_backspace.setOnClickListener(onBackspaceClicked);

    }

    public View.OnClickListener onNumberClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if ((leftNumFull && operator == Character.MIN_VALUE) || rightNumStr.length() > 10) { //set max input length to 10
                return;
            }
                switch (v.getId()) {
                    case R.id.btn_0:
                        rightNumStr.append(0);
                        break;
                    case R.id.btn_1:
                        rightNumStr.append(1);
                        break;
                    case R.id.btn_2:
                        rightNumStr.append(2);
                        break;
                    case R.id.btn_3:
                        rightNumStr.append(3);
                        break;
                    case R.id.btn_4:
                        rightNumStr.append(4);
                        break;
                    case R.id.btn_5:
                        rightNumStr.append(5);
                        break;
                    case R.id.btn_6:
                        rightNumStr.append(6);
                        break;
                    case R.id.btn_7:
                        rightNumStr.append(7);
                        break;
                    case R.id.btn_8:
                        rightNumStr.append(8);
                        break;
                    case R.id.btn_9:
                        rightNumStr.append(9);
                        break;
                }//end switch
                wasLastButtonOperator = false;
                txt_display.setText(leftNumStr + operator + " " + rightNumStr.toString());

        }//end method onClick
    };//end inner class

    public View.OnClickListener onBackspaceClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (rightNumStr.length() > 1) {
                rightNumStr.deleteCharAt(rightNumStr.length() - 1);
                if (leftNumFull) {
                    txt_display.setText(leftNumStr + operator + " " + rightNumStr.toString());
                } else {
                    txt_display.setText(rightNumStr);
                }
            } else if (rightNumStr.length() == 1) {
                rightNumStr.setLength(0);
                wasLastButtonOperator = true;
                if (leftNumFull) {
                    txt_display.setText(leftNumStr + operator);
                } else {
                    txt_display.setText(rightNumStr);
                }
            } else {
                operator = Character.MIN_VALUE;
                wasLastButtonOperator = true;
                if (leftNumFull) {
                    txt_display.setText(leftNumStr);
                } else {
                    txt_display.setText("");
                }
            }
        }//end method onClick
    };//end inner class

    public View.OnClickListener onOperatorClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (wasLastButtonOperator) {
                if (leftNumFull) {
                    setOperator(v);
                }
            } else {
                if (leftNumFull) {
                    rightNum = Double.parseDouble(rightNumStr.toString());
                    leftNum = calc.Calculate(leftNum, rightNum, operator);
                    leftNumStr = String.valueOf(leftNum);
                    leftNumStr = leftNumStr.replaceFirst("\\.0$", "") + " ";
                    leftNumFull = true;

                    rightNum = 0.0;
                    rightNumStr.setLength(0);//clears the string builder
                    setOperator(v);
                } else {
                    leftNum = Double.parseDouble(rightNumStr.toString());
                    leftNumStr = String.valueOf(leftNum);
                    leftNumStr = leftNumStr.replaceFirst("\\.0$", "") + " ";
                    leftNumFull = true;

                    rightNum = 0.0;
                    rightNumStr.setLength(0);//clears the string builder
                    setOperator(v);
                }//end inner if/else
            }//end if/else
            wasLastButtonOperator = true;
            if (leftNumStr.equals("Infinity ")) {
                clear();
                txt_display.setText(R.string.txt_NaN);
            } else {
                txt_display.setText(leftNumStr + operator);
            }
        }//end method onClick
    };//end inner class

    public View.OnClickListener onEqualsClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (rightNumStr.length() == 0 && leftNumStr.length() == 0) {
                txt_display.setText("");
                return;
            }
            if (!wasLastButtonOperator && rightNumStr.length() > 0 && leftNumStr.length() > 0) {
                rightNum = Double.parseDouble(rightNumStr.toString());
                leftNum = calc.Calculate(leftNum, rightNum, operator);
                leftNumStr = String.valueOf(leftNum);
                leftNumStr = leftNumStr.replaceFirst("\\.0$", "") + " ";
            } else if (leftNumStr.length() == 0) {
                leftNum = Double.parseDouble(rightNumStr.toString());
                leftNumStr = String.valueOf(leftNum);
                leftNumStr = leftNumStr.replaceFirst("\\.0$", "") + " ";
            }

            if (leftNumStr.equals("Infinity ")) {
                clear();
                txt_display.setText(R.string.txt_NaN);
            } else {
                operator = Character.MIN_VALUE;
                wasLastButtonOperator = true;
                rightNum = 0.0;
                rightNumStr.setLength(0);//clears the string builder
                leftNumFull = true;
                txt_display.setText(leftNumStr);
            }
        }//end method onClick
    };//end inner class

    public View.OnClickListener onClearClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            clear();
        }//end method onClick
    };//end inner class

    public View.OnClickListener onDecimalClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!rightNumStr.toString().contains(".")) {
                rightNumStr.append('.');
            }
            txt_display.setText(leftNumStr + operator + " " + rightNumStr.toString());
        }//end method onClick
    };//end inner class

    public View.OnClickListener onPlusminusClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (wasLastButtonOperator && operator == Character.MIN_VALUE) {
                return;
            }
            if (rightNumStr.toString().contains("-")) {
                rightNumStr.deleteCharAt(0);
            } else {
                rightNumStr.insert(0, "-");
            }
            txt_display.setText(leftNumStr + operator + " " + rightNumStr.toString());
        }//end method onClick
    };//end inner class
}//end class