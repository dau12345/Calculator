package com.example.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tvResult;

    Button btnAC, btnPlus_or_Sub, btnPercent, btnDivide,
            btnSeven, btnEight, btnNine, btnMultiply,
            btnFour, btnFive, btnSix, btnSubtract,
            btnOne, btnTwo, btnThree, btnPlus,
            btnZero, btnComma, btnEqual, btnHistory;

    String finalResult = "0";

    //pressedBtnId is used to store operation button's id which has been clicked
    int pressedBtnId = 0;

    //clickState is set to "true" when a operation button has been clicked
    boolean clickState = false;

    //needCalcState is set to "true" when user have entered complete math problem
    boolean needCalcState = false;

    //newSol is set to "true" when the equal button has been clicked
    boolean newSolState = false;

    BigDecimal currentNum = new BigDecimal("0");
    BigDecimal tempNum = new BigDecimal("0");
    BigDecimal resultNum = new BigDecimal("0");

    ArrayList<ArrayList> solList = new ArrayList<ArrayList>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.result);
        tvResult.setText("0");
        currentNum = new BigDecimal(tvResult.getText().toString());

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
        assignBtn(btnHistory, R.id.btnHistory);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("clickState", clickState);
        savedInstanceState.putBoolean("needCalcState", needCalcState);
        savedInstanceState.putBoolean("newSolState", newSolState);
        savedInstanceState.putInt("pressedBtnId", pressedBtnId);
        savedInstanceState.putString("finalResult", finalResult);
        savedInstanceState.putString("currentNum", currentNum.toString());
        savedInstanceState.putString("tempNum", tempNum.toString());
        savedInstanceState.putString("resultNum", resultNum.toString());
        savedInstanceState.putString("tvResult", tvResult.getText().toString());
        savedInstanceState.putSerializable("solList", solList);

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        clickState = savedInstanceState.getBoolean("clickState");
        needCalcState = savedInstanceState.getBoolean("needCalcState");
        newSolState = savedInstanceState.getBoolean("newSolState");
        finalResult = savedInstanceState.getString("finalResult");
        currentNum = new BigDecimal(savedInstanceState.getString("currentNum"));
        tempNum = new BigDecimal(savedInstanceState.getString("tempNum"));
        resultNum = new BigDecimal(savedInstanceState.getString("resultNum"));
        tvResult.setText(savedInstanceState.getString("tvResult"));
        pressedBtnId = savedInstanceState.getInt("pressedBtnId");
        solList = (ArrayList<ArrayList>) savedInstanceState.getSerializable("solList");
        if (pressedBtnId > 0) {
            changeBtnColor(findViewById(pressedBtnId));
        }
    }

    //Identify buttons and set onclick listener for those buttons
    void assignBtn(Button btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    //Change operation button's bg color and text color, also save the pressed button's id
    void changeBtnColor(@NonNull Button btn) {
        btn.setBackgroundColor(getColor(R.color.white));
        btn.setTextColor(Color.parseColor("#F39D3A"));
        pressedBtnId = btn.getId();
    }

    //Change operation button's bg color and text color back to default, also clear the pressed button's id
    void disposeBtnColor(@NonNull Button btn) {
        btn.setBackgroundColor(Color.parseColor("#F39D3A"));
        btn.setTextColor(getColor(R.color.white));
        pressedBtnId = 0;
    }

    void pressButton(Button btn) {
        //If user has entered complete math problem but not clicked equal button yet -> calculate it
        if (needCalcState) {
            calc();
            needCalcState = false;
        }

        //If pressed button's id is already existed -> dispose it
        if (pressedBtnId != 0) {
            disposeBtnColor(findViewById(pressedBtnId));
        }

        changeBtnColor(btn);
        tempNum = currentNum;
        clickState = true;
        newSolState = false;
    }

    //Format big number to scientific notation
    String formatSicNo(String bigNumber){
        DecimalFormat df1 = new DecimalFormat("@######");
        df1.setMinimumFractionDigits(0);
        DecimalFormat df2 = new DecimalFormat("@#####E0");
        df2.setMinimumFractionDigits(1);

        if (bigNumber.length() <= 8) {
            bigNumber = df1.format(new BigDecimal(bigNumber));
        } else {
            bigNumber = df2.format(new BigDecimal(bigNumber));
        }

        return bigNumber;
    }

    void calc() {
        //If there is no operation button has been clicked -> return
        if (pressedBtnId == 0) {
            return;
        }

        Button pressedBtn = findViewById(pressedBtnId);
        switch (pressedBtn.getId()) {
            case R.id.btnDivide:
                resultNum = tempNum.divide(currentNum, 2, RoundingMode.HALF_UP);
                break;
            case R.id.btnMultiply:
                resultNum = tempNum.multiply(currentNum);
                break;
            case R.id.btnSubtract:
                resultNum = tempNum.subtract(currentNum);
                break;
            case R.id.btnPlus:
                resultNum = tempNum.add(currentNum);
                break;
            default:
                break;
        }

        //newSol is set to "true" to pronounce that program is ready for a new math problem
        newSolState = true;

        //Format big number to scientific notation
        finalResult = formatSicNo(resultNum.stripTrailingZeros().toPlainString());
        String tempNumString = formatSicNo(tempNum.toPlainString());
        String currentNumString = formatSicNo(currentNum.toPlainString());

        //Save history to solList array
        ArrayList<Object> solution = new ArrayList<Object>();
        solution.add(tempNumString);
        Button btnTemp = findViewById(pressedBtnId);
        solution.add(btnTemp.getText().toString());
        solution.add(currentNumString);
        solution.add(finalResult);

        solList.add(solution);

        //Display result on screen
        if (finalResult.length() > 0) {
            tvResult.setText(finalResult);
        }

        //Update the new currentNum
        currentNum = new BigDecimal(finalResult);

        //Dispose the pressed operation button when calculated successfully
        disposeBtnColor(pressedBtn);
    }

    @Override
    public void onClick(View view) {
        Button btn = (Button) view;
        String btnText = btn.getText().toString();
        String resultText = tvResult.getText().toString();
        currentNum = new BigDecimal(resultText);

        switch (btn.getId()) {
            case R.id.btnAC:
                //If user has clicked any operation buttons -> dispose it
                if (pressedBtnId != 0) {
                    disposeBtnColor(findViewById(pressedBtnId));
                }
                needCalcState = false;
                clickState = false;
                newSolState = false;
                finalResult = "0";
                break;
            case R.id.btnPlus_or_Sub:
                //Program only permits users to change a positive number to negative or backwards if they haven't clicked any operation buttons yet
                if (clickState) {
                    currentNum = new BigDecimal("0").subtract(currentNum);
                    finalResult = currentNum.stripTrailingZeros() + "";
                    tempNum = currentNum;
                } else {
                    currentNum = new BigDecimal("0").subtract(currentNum);
                    finalResult = currentNum.stripTrailingZeros() + "";
                }
                break;
            case R.id.btnPercent:
                currentNum = currentNum.divide(new BigDecimal("100"));
                finalResult = currentNum.stripTrailingZeros() + "";
                break;
            case R.id.btnDivide:
                pressButton(findViewById(R.id.btnDivide));
                break;
            case R.id.btnMultiply:
                pressButton(findViewById(R.id.btnMultiply));
                break;
            case R.id.btnSubtract:
                pressButton(findViewById(R.id.btnSubtract));
                break;
            case R.id.btnPlus:
                pressButton(findViewById(R.id.btnPlus));
                break;
            case R.id.btnEqual:
                calc();
                break;
            case R.id.btnComma:
                if (!resultText.contains(".")) {
                    finalResult += btnText;
                }
                break;
            case R.id.btnHistory:
                //Pass the array to history activity
                Intent i = new Intent(getApplicationContext(), HistoryActivity.class);
                i.putExtra("solList", solList);
                startActivity(i);
                break;
            default:
                //If program is ready for new solution -> Clear screen and add new number
                if (newSolState) {
                    finalResult = btnText;
                    newSolState = false;
                } else {
                    //If the screen only has "0" then user cannot add more "0"
                    if (resultText.length() < 2 && resultText.indexOf("0") == 0) {
                        finalResult = btnText;
                    } else {
                        //If a operation button has been clicked -> clear A number and show B number
                        if (clickState) {
                            finalResult = btnText;
                            clickState = false;
                            needCalcState = true;
                        } else if (finalResult.length() <= 8){
                            finalResult += btnText;
                        }
                    }
                }
                break;
        }
        tvResult.setText(finalResult);
    }
}