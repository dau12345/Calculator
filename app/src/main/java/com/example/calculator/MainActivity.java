package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvResult;

    Button btnAC, btnPlus_or_Sub, btnPercent, btnDivide,
            btnSeven, btnEight, btnNine, btnMultiply,
            btnFour, btnFive, btnSix, btnSubtract,
            btnOne, btnTwo, btnThree, btnPlus,
            btnZero, btnComma, btnEqual;

    String finalResult = "";

    //pressedBtnId is used to store operation button's id which has been clicked
    int pressedBtnId = 0;

    //clickState is set to "true" when a operation button has been clicked
    boolean clickState = false;

    //needCalcState is set to "true" when user have entered complete math problem
    boolean needCalcState = false;

    //newSol is set to "true" when the equal button has been clicked
    boolean newSolState = false;

    ArrayList<ArrayList> solList = new ArrayList<>();

    BigDecimal currentNum = new BigDecimal("0");
    BigDecimal tempNum = new BigDecimal("0");
    BigDecimal resultNum = new BigDecimal("0");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.result);
        assignBtn(btnAC, R.id.btnAC);
        assignBtn(btnPlus_or_Sub, R.id.btnPlus_or_Sub);
        assignBtn(btnPercent, R.id.btnPercent);
        assignBtn(btnDivide, R.id.btnDivide);
        assignBtn(btnSeven, R.id.btnSeven);
        assignBtn(btnEight, R.id.btnEight);
        assignBtn(btnNine, R.id.btnNine);
        assignBtn(btnMultiply, R.id.btnMultiply);
        assignBtn(btnFour, R.id.btnFour);
        assignBtn(btnFive, R.id.btnFive);
        assignBtn(btnSix, R.id.btnSix);
        assignBtn(btnSubtract, R.id.btnSubtract);
        assignBtn(btnOne, R.id.btnOne);
        assignBtn(btnTwo, R.id.btnTwo);
        assignBtn(btnThree, R.id.btnThree);
        assignBtn(btnPlus, R.id.btnPlus);
        assignBtn(btnZero, R.id.btnZero);
        assignBtn(btnComma, R.id.btnComma);
        assignBtn(btnEqual, R.id.btnEqual);
    }

    //Identify buttons and set onclick listener for those buttons
    void assignBtn(Button btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    //Change operation button's bg color and text color, also save the pressed button's id
    void createState(@NonNull Button btn) {
        btn.setBackgroundColor(getColor(R.color.white));
        btn.setTextColor(Color.parseColor("#F39D3A"));
        pressedBtnId = btn.getId();
    }

    //Change operation button's bg color and text color back to default, also clear the pressed button's id
    void disposeState(@NonNull Button btn) {
        btn.setBackgroundColor(Color.parseColor("#F39D3A"));
        btn.setTextColor(getColor(R.color.white));
        pressedBtnId = 0;
    }

    void pressAction(Button btn) {
        //If user has entered complete math problem but not clicked equal button yet -> calc
        if (needCalcState) {
            calc();
            needCalcState = false;
        }

        //If pressed button's id is already existed -> dispose it
        if (pressedBtnId != 0) {

            disposeState(findViewById(pressedBtnId));
        }

        createState(btn);
        tempNum = currentNum;
        clickState = true;
        newSolState = false;
    }

    void calc() {
        //If user hasn't clicked any operation buttons yet -> break
        if (pressedBtnId == 0) {
            return;
        }

        Button pressedBtn = findViewById(pressedBtnId);
        switch (pressedBtn.getText().toString()) {
            case "/":
                resultNum = tempNum.divide(currentNum);
                break;
            case "X":
                resultNum = tempNum.multiply(currentNum);
                break;
            case "-":
                resultNum = tempNum.subtract(currentNum);
                break;
            case "+":
                resultNum = tempNum.add(currentNum);
                break;
            default:
                break;
        }

        //Dispose the pressed operation button when calculated successfully
        disposeState(pressedBtn);

        //newSol is set to "true" to pronounce that program is ready for a new math problem
        newSolState = true;

        //Display the result on screen
        finalResult = resultNum.stripTrailingZeros() + "";
        if (finalResult.length() > 0) {
            tvResult.setText(finalResult);
        }

        //Update the new currentNum
        currentNum = new BigDecimal(finalResult);

        //Save history

    }

    @Override
    public void onClick(View view) {
        Button btn = (Button) view;
        String btnText = btn.getText().toString();
        String resultText = tvResult.getText().toString();
        currentNum = new BigDecimal(resultText);

        switch (btnText) {
            case "AC":
                //If user has clicked any operation buttons -> dispose it
                if (pressedBtnId != 0) {
                    disposeState(findViewById(pressedBtnId));
                }
                needCalcState = false;
                clickState = false;
                newSolState = false;
                finalResult = "0";
                break;
            case "+/-":
                //Program only permits users to change a positive number to negative or backwards if they haven't clicked any operation buttons yet
                if (!clickState) {
                    currentNum = new BigDecimal("0").subtract(currentNum);
                    finalResult = currentNum.stripTrailingZeros() + "";
                }
                break;
            case "%":
                currentNum = currentNum.divide(new BigDecimal("100"));
                finalResult = currentNum.stripTrailingZeros() + "";
                break;
            case "/":
                pressAction(findViewById(R.id.btnDivide));
                break;
            case "X":
                pressAction(findViewById(R.id.btnMultiply));
                break;
            case "-":
                pressAction(findViewById(R.id.btnSubtract));
                break;
            case "+":
                pressAction(findViewById(R.id.btnPlus));
                break;
            case "=":
                calc();
                break;
            case ".":
                if (!resultText.contains(".")) {
                    finalResult += btnText;
                }
                break;
            default:
                //If program is ready for new solution
                if (newSolState) {
                    finalResult = btnText;
                    newSolState = false;
                } else {
                    if (resultText.length() < 2 && resultText.indexOf("0") == 0) {
                        finalResult = btnText;
                    } else {
                        if (clickState) {
                            finalResult = btnText;
                            clickState = false;
                            needCalcState = true;
                        } else {
                            finalResult += btnText;
                        }
                    }
                }
                break;
        }
        tvResult.setText(finalResult);
    }
}