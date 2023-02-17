package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    Button btnClose;
    ArrayList<ArrayList> solList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        linearLayout = findViewById(R.id.linearLayoutHistory);
        btnClose = findViewById(R.id.btnClose);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                finish();
            }
        });

        //Receive data from main activity
        Intent intent = getIntent();
        solList = (ArrayList<ArrayList>) intent.getSerializableExtra("solList");

        for (ArrayList arrTemp : solList) {
            String str = arrTemp.get(0) + " " + arrTemp.get(1) + " " + arrTemp.get(2) + " = " + arrTemp.get(3);

            // Create TextView programmatically.
            TextView textView = new TextView(this);
            textView.setText(str);
            textView.setTextColor(getColor(R.color.white));
            textView.setPadding(25, 0, 25, 0);
            textView.setTextSize(40);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );

            textView.setLayoutParams(params);

            // Add TextView to LinearLayout
            if (linearLayout != null) {
                linearLayout.addView(textView);
            }
        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable("solList", solList);

        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        solList = (ArrayList<ArrayList>) savedInstanceState.getSerializable("solList");
    }
}