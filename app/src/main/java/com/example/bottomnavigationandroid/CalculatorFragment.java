package com.example.bottomnavigationandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CalculatorFragment extends Fragment {
    private TextView resultView;
    private TextView solutionView;
    private String input = "";
    private String operator = "";
    private String currentNumber = "";
    private double firstOperand = 0.0;
    private boolean isOperatorClicked = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calculator, container, false);

        // Initialize views
        resultView = view.findViewById(R.id.resultView);
        solutionView = view.findViewById(R.id.solutionView);

        // Set up button click listeners
        view.findViewById(R.id.button_0).setOnClickListener(v -> appendNumber("0"));
        view.findViewById(R.id.button_1).setOnClickListener(v -> appendNumber("1"));
        view.findViewById(R.id.button_2).setOnClickListener(v -> appendNumber("2"));
        view.findViewById(R.id.button_3).setOnClickListener(v -> appendNumber("3"));
        view.findViewById(R.id.button_4).setOnClickListener(v -> appendNumber("4"));
        view.findViewById(R.id.button_5).setOnClickListener(v -> appendNumber("5"));
        view.findViewById(R.id.button_6).setOnClickListener(v -> appendNumber("6"));
        view.findViewById(R.id.button_7).setOnClickListener(v -> appendNumber("7"));
        view.findViewById(R.id.button_8).setOnClickListener(v -> appendNumber("8"));
        view.findViewById(R.id.button_9).setOnClickListener(v -> appendNumber("9"));
        view.findViewById(R.id.button_dot).setOnClickListener(v -> appendDot());

        view.findViewById(R.id.button_plus).setOnClickListener(v -> setOperator("+"));
        view.findViewById(R.id.button_minus).setOnClickListener(v -> setOperator("-"));
        view.findViewById(R.id.button_multiply).setOnClickListener(v -> setOperator("*"));
        view.findViewById(R.id.button_divide).setOnClickListener(v -> setOperator("/"));

        view.findViewById(R.id.button_equals).setOnClickListener(v -> calculateResult());
        view.findViewById(R.id.button_ac).setOnClickListener(v -> resetCalculator());

        return view; // Return the inflated view
    }

    private void appendNumber(String number) {
        if (isOperatorClicked) {
            input = "";
            isOperatorClicked = false;
        }
        input += number;
        currentNumber = input;
        updateResultView();
    }

    private void appendDot() {
        if (!input.contains(".")) {
            input += ".";
            updateResultView();
        }
    }

    private void setOperator(String op) {
        if (!currentNumber.isEmpty()) {
            firstOperand = Double.parseDouble(currentNumber);
            operator = op;
            solutionView.setText(currentNumber + " " + operator);
            isOperatorClicked = true;
        }
    }

    private void calculateResult() {
        if (!currentNumber.isEmpty() && !operator.isEmpty()) {
            double secondOperand = Double.parseDouble(currentNumber);
            double result = 0.0;

            switch (operator) {
                case "+":
                    result = firstOperand + secondOperand;
                    break;
                case "-":
                    result = firstOperand - secondOperand;
                    break;
                case "*":
                    result = firstOperand * secondOperand;
                    break;
                case "/":
                    if (secondOperand != 0.0) {
                        result = firstOperand / secondOperand;
                    } else {
                        resultView.setText("Error");
                        return;
                    }
                    break;
            }
            String formatResult = String.format("%.2f", result);

            solutionView.setText(firstOperand + " " + operator + " " + secondOperand);
            resultView.setText(String.valueOf(result));
            input = formatResult;
            operator = "";
            currentNumber = "";
        }
    }

    private void resetCalculator() {
        input = "";
        currentNumber = "";
        firstOperand = 0.0;
        operator = "";
        isOperatorClicked = false;
        resultView.setText("0");
        solutionView.setText("");
    }

    private void updateResultView() {
        resultView.setText(input);
    }
}
