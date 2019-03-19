package com.example.lakeenterprises;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void add(View v) {
        EditText numOneText = (EditText) findViewById(R.id.firstNum);
        EditText numTwoText = (EditText) findViewById(R.id.firstNum2);

        String numOneStr = numOneText.getText().toString();
        String numTwoStr = numTwoText.getText().toString();

        if (numOneStr.isEmpty()) numOneStr = "0";
        if (numTwoStr.isEmpty()) numTwoStr = "0";

        // Log.DEBUG(TAG, "In calculate method - user input: " + numOneStr);
        // Log.DEBUG(TAG, "In calculate method - user input: " + numTwoStr);

        Double numOne = Double.parseDouble(numOneStr);
        Double numTwo = Double.parseDouble(numTwoStr);
        Double add = numOne+numTwo;

        TextView answerText = (TextView) findViewById(R.id.answerText);
        answerText.setText(add.toString());

    }
    public void multiply(View v) {
        EditText numOneText = (EditText) findViewById(R.id.firstNum);
        EditText numTwoText = (EditText) findViewById(R.id.firstNum2);

        String numOneStr = numOneText.getText().toString();
        String numTwoStr = numTwoText.getText().toString();

        Double numOne = Double.parseDouble(numOneStr);
        Double numTwo = Double.parseDouble(numTwoStr);
        Double add = numOne*numTwo;

        TextView answerText = (TextView) findViewById(R.id.answerText);
        answerText.setText(add.toString());

    }
    public void subtract(View v) {
        EditText numOneText = (EditText) findViewById(R.id.firstNum);
        EditText numTwoText = (EditText) findViewById(R.id.firstNum2);

        String numOneStr = numOneText.getText().toString();
        String numTwoStr = numTwoText.getText().toString();

        Double numOne = Double.parseDouble(numOneStr);
        Double numTwo = Double.parseDouble(numTwoStr);
        Double add = numOne-numTwo;

        TextView answerText = (TextView) findViewById(R.id.answerText);
        answerText.setText(add.toString());

    }
    public void divide(View v) {
        EditText numOneText = (EditText) findViewById(R.id.firstNum);
        EditText numTwoText = (EditText) findViewById(R.id.firstNum2);

        String numOneStr = numOneText.getText().toString();
        String numTwoStr = numTwoText.getText().toString();

        Double numOne = Double.parseDouble(numOneStr);
        Double numTwo = Double.parseDouble(numTwoStr);
        Double add = numOne/numTwo;

        TextView answerText = (TextView) findViewById(R.id.answerText);
        answerText.setText(add.toString());

    }
}
