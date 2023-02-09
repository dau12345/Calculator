package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView result;

    ArrayList<Button> buttons = new ArrayList<>();
    Button btnAC, btnPlus_or_Sub, btnPercent, btnDivide,
            btnSeven, btnEight, btnNine, btnMultiply,
            btnFour, btnFive, btnSix, btnSubtract,
            btnOne, btnTwo, btnThree, btnPlus,
            btnZero, btnComma, btnEqual;
    int pressedBtnId = 0;
    boolean state = false;

    BigDecimal currentNum = new BigDecimal("0");
    BigDecimal tempNum = new BigDecimal("0");
    BigDecimal resultNum = new BigDecimal("0");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);
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

    void assignBtn(Button btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    void createState(Button btn) {
        btn.setBackgroundColor(getColor(R.color.white));
        btn.setTextColor(Color.parseColor("#F39D3A"));
        pressedBtnId = btn.getId();
    }

    void disposeState(Button btn) {
        btn.setBackgroundColor(Color.parseColor("#F39D3A"));
        btn.setTextColor(getColor(R.color.white));
        pressedBtnId = 0;
    }

    void pressAction(Button btn) {
        if (pressedBtnId != 0) {
            disposeState(findViewById(pressedBtnId));
        } else {
            createState(btn);
            tempNum = currentNum;
            state = true;
        }
    }

    @Override
    public void onClick(View view) {
        Button btn = (Button) view;
        String btnText = btn.getText().toString();
        String resultText = result.getText().toString();
        currentNum = new BigDecimal(resultText);

        switch (btnText) {
            case "AC":
                resultText = "0";
                break;
            case "+/-":
                if (state) {

                } else {
                    currentNum = new BigDecimal("0").subtract(currentNum);
                    resultText = currentNum.stripTrailingZeros() + "";
                }
                break;
            case "%":
                currentNum = currentNum.divide(new BigDecimal("100"));
                resultText = currentNum.stripTrailingZeros() + "";
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
                if (pressedBtnId == 0){
                    break;
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
                disposeState(pressedBtn);
                resultText = resultNum.stripTrailingZeros() + "";
                break;
            case ".":
                if (!resultText.contains(".")){
                    resultText += btnText;
                }
                break;
            default:
                if (resultText.length() < 2 && resultText.indexOf("0") == 0) {
                    resultText = btnText;
                } else {
                    if (state) {
                        resultText = btnText;
                        state = false;
                    } else {
                        resultText += btnText;
                    }
                }
                break;
        }
        result.setText(resultText);
    }
}