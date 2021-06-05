package com.kheileang.numberinburmese;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kheileang.numberinburmese.SimpleClass.NumberConverterEng;
import com.kheileang.numberinburmese.SimpleClass.NumberConverterMm;

import java.text.DecimalFormat;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView textView0, textView1, textView2, textView3, textView4, textView5,
    textView6, textView7, textView8, textView9;
    private ImageView imageView1, imageView2, imageView3, imageView4, imageView5;
    private TextView numberView, numberTextView;
    private StringBuilder number = new StringBuilder();
    private StringBuilder numberText = new StringBuilder();
    private NumberConverterMm converter = new NumberConverterMm();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numberTextView = findViewById(R.id.numberText);
        numberView = findViewById(R.id.number);

        textView0 = findViewById(R.id.zero);
        textView1 = findViewById(R.id.one);
        textView2 = findViewById(R.id.two);

        textView3 = findViewById(R.id.three);
        textView4 = findViewById(R.id.four);
        textView5 = findViewById(R.id.five);

        textView6 = findViewById(R.id.six);
        textView7 = findViewById(R.id.seven);
        textView8 = findViewById(R.id.eight);
        textView9 = findViewById(R.id.nine);

        imageView1 = findViewById(R.id.delete);
        imageView2 = findViewById(R.id.clear);
        imageView3 = findViewById(R.id.share);
        imageView4 = findViewById(R.id.delete);
        imageView5 = findViewById(R.id.setting);

        textView0.setOnClickListener(this);
        textView1.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);
        textView4.setOnClickListener(this);
        textView5.setOnClickListener(this);
        textView6.setOnClickListener(this);
        textView7.setOnClickListener(this);
        textView8.setOnClickListener(this);
        textView9.setOnClickListener(this);
        imageView1.setOnClickListener(this);
        imageView1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clearAllDigits();
                return true;
            }
        });
        imageView2.setOnClickListener(this);
        imageView3.setOnClickListener(this);
        imageView4.setOnClickListener(this);
        imageView5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int view_id = v.getId();
        switch (view_id){
            case R.id.zero:
                Log.i(TAG, "onClick: 0");
                buildNumber(0);
                break;
            case R.id.one:
                Log.i(TAG, "onClick: one");
                buildNumber(1);
                break;
            case R.id.two:
                Log.i(TAG, "onClick: two");
                buildNumber(2);
                break;
            case R.id.three:
                Log.i(TAG, "onClick: three");
                buildNumber(3);
                break;
            case R.id.four:
                Log.i(TAG, "onClick: 4");
                buildNumber(4);
                break;
            case R.id.five:
                Log.i(TAG, "onClick: 5");
                buildNumber(5);
                break;
            case R.id.six:
                Log.i(TAG, "onClick: 6");
                buildNumber(6);
                break;
            case R.id.seven:
                Log.i(TAG, "onClick: 7");
                buildNumber(7);
                break;
            case R.id.eight:
                Log.i(TAG, "onClick: 8");
                buildNumber(8);
                break;
            case R.id.nine:
                Log.i(TAG, "onClick: 9");
                buildNumber(9);
                break;
            case R.id.delete:
                Log.i(TAG, "onClick: Delete");
                deleteOneDigit();
                break;
            case R.id.clear:
                Log.i(TAG, "onClick: Clear");
                clearAllDigits();
                break;
            case R.id.copy:
                Log.i(TAG, "onClick: COPY");
                copyContent();
                break;
        }

    }

    private void copyContent() {
    }

    private void clearAllDigits() {
        // clear all characters
        number.setLength(0);
        numberText.setLength(0);

        // update it on UI
        numberView.setText("0");
        numberTextView.setText("zero");
        Log.i(TAG, "clearAllDigits: Clear string builder");
        System.gc();
    }

    private void deleteOneDigit() {
        Log.i(TAG, "deleteOneDigit: Delete One Character");
        // remove last character
        if (number.length() > 1 ) {
            number.deleteCharAt(number.length() - 1);

            // add thousand separtor
            String formatted_str = addThousandSeparator();
            // update the string again
            numberView.setText(formatted_str);

            // update Number Text View
            // convert the number to text
            String numberInText =  converter.convertToWord(Long.parseLong(number.toString()));
            // set it on UI
            numberTextView.setText(numberInText);
        }else if(number.length() == 1 && number.charAt(0) != '0'){
            number.setLength(0);
            numberView.setText("0");
            numberTextView.setText("zero");
        }
    }



    private void buildNumber(int num){
        if (number.length()>12){
            // reach limit
            Toasty.info(this, "The number reached limit of 13 digits.", Toasty.LENGTH_SHORT).show();
            return;
        }
        if ((number.length() >= 1 && num == 0) || num != 0) {
            number.append(String.valueOf(num));
        }

        if ((number.length() == 0 || number.length() == 1 ) && num == 0) {
            numberView.setText("0");
        }

        String formatted_str = addThousandSeparator().length() == 0 ? "0" : addThousandSeparator();
        numberView.setText(formatted_str);

        // convert the number to text
        String numberInText = number.toString().equals("") ? "zero":converter.convertToWord(Long.parseLong(number.toString()));
        // set it on UI
        numberTextView.setText(numberInText);

    }

    private String addThousandSeparator(){
        DecimalFormat decim = new DecimalFormat("#,###");
        return number.length()>3?decim.format(Long.parseLong(number.toString())):number.toString();
    }
}