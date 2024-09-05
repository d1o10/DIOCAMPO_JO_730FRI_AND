package com.example.elroidcalculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText num1, num2;
    private TextView result;
    private Button addBtn, subBtn, multiplyBtn, divBtn, clearBtn;
    private Calculator calculator;
    private DecimalFormat df = new DecimalFormat("#.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        result = findViewById(R.id.result);
        addBtn = findViewById(R.id.addBtn);
        subBtn = findViewById(R.id.subBtn);
        multiplyBtn = findViewById(R.id.multiplyBtn);
        divBtn = findViewById(R.id.divBtn);
        clearBtn = findViewById(R.id.clearBtn);
        calculator = new Calculator();

        addBtn.setOnClickListener(v -> calculate('+'));
        subBtn.setOnClickListener(v -> calculate('-'));
        multiplyBtn.setOnClickListener(v -> calculate('*'));
        divBtn.setOnClickListener(v -> calculate('/'));
        clearBtn.setOnClickListener(v -> doClear());
    }

    private void calculate(char operator) {
        if (validateInputs()) {
            double n1 = Double.parseDouble(num1.getText().toString());
            double n2 = Double.parseDouble(num2.getText().toString());
            double resultValue = 0;

            switch (operator) {
                case '+':
                    resultValue = Double.parseDouble(calculator.add(n1, n2));
                    break;
                case '-':
                    resultValue = Double.parseDouble(calculator.subtract(n1, n2));
                    break;
                case '*':
                    resultValue = Double.parseDouble(calculator.multiply(n1, n2));
                    break;
                case '/':
                    try {
                        resultValue = Double.parseDouble(calculator.divide(n1, n2));
                    } catch (ArithmeticException e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    break;
            }
            result.setText(df.format(resultValue));
        }
    }

    private void doClear() {
        num1.setText("");
        num2.setText("");
        result.setText("Result");
    }

    private boolean validateInputs() {
        String num1Text = num1.getText().toString().trim();
        String num2Text = num2.getText().toString().trim();

        if (num1Text.isEmpty() && num2Text.isEmpty()) {
            Toast.makeText(this, "Please enter both numbers", Toast.LENGTH_SHORT).show();
            return false;
        } else if (num1Text.isEmpty()) {
            Toast.makeText(this, "Please enter the first number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (num2Text.isEmpty()) {
            Toast.makeText(this, "Please enter the second number", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}