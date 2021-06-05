package com.kheileang.numberinburmese;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.kheileang.numberinburmese.Activity.SettingActivity;
import com.kheileang.numberinburmese.SimpleClass.NumberConverterEng;
import com.kheileang.numberinburmese.SimpleClass.NumberConverterMm;
import com.tapadoo.alerter.Alerter;

import java.text.DecimalFormat;
import es.dmoral.toasty.Toasty;

import static android.os.Build.VERSION.SDK;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ImageView imageView1;
    private TextView numberView, numberTextView;
    private StringBuilder number = new StringBuilder();
    private StringBuilder numberText = new StringBuilder();
    private ClipboardManager clipboardManager;
    private NumberConverterMm converterMm = new NumberConverterMm();
    private NumberConverterEng converterEng = new NumberConverterEng();
    private String numberInText = "";
    private Vibrator vibrator;
    private final int VIBRATE_AMPLITUDE = 80;
    private final int VIBRATE_DURATION = 50;
    private final String DEFAULT_TEXT_MM = "သုည";
    private final String DEFAULT_TEXT_ENG = "zero";
    private boolean burmese = true;
    private AdView adView;
    private InterstitialAd minternstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        setContentView(R.layout.activity_main);

        // initialize ads
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        // Banner Ads
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        // Interstitial Ad
        InterstitialAd.load(this,
                "ca-app-pub-1589742015897303/9695684474",
                new AdRequest.Builder().build(),
                new InterstitialAdLoadCallback(){
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        super.onAdLoaded(interstitialAd);
                        minternstitialAd = interstitialAd;
                        minternstitialAd.show(getParent());
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                    }
                });

        numberTextView = findViewById(R.id.numberText);
        numberView = findViewById(R.id.number);

        imageView1 = findViewById(R.id.delete);

        // Make number text view scrollable
        numberTextView.setMovementMethod(new ScrollingMovementMethod());
        imageView1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clearAllDigits();
                return true;
            }
        });
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
            case R.id.share:
                Log.i(TAG, "onClick: SHARE");
                shareContent();
                break;
            case R.id.setting:
                Log.i(TAG, "onClick: SETTING");
                goToSetting();
                break;
            case R.id.translate:
                Log.i(TAG, "onClick: TRANSLATE");
                translateNumber();
                break;
        }

    }

    private void translateNumber() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(VIBRATE_DURATION, VIBRATE_AMPLITUDE));
        }
        burmese = !burmese;

        // update Number Text View
        // convert the number to
        if (number.length() >= 1 ) {
            if (burmese) {
                numberInText = converterMm.convertToWord(Long.parseLong(number.toString()));
            } else {
                numberInText = converterEng.convertToWord(Long.parseLong(number.toString()));
            }

            // set it on UI
            numberTextView.setText(numberInText);
        }else if(number.length() == 0){
            number.setLength(0);
            numberView.setText("0");
            numberTextView.setText(burmese?DEFAULT_TEXT_MM:DEFAULT_TEXT_ENG);
        }
    }

    private void goToSetting() {
        startActivity(new Intent(this, SettingActivity.class));
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);
    }

    private void shareContent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(VIBRATE_DURATION, VIBRATE_AMPLITUDE));
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, numberInText);
        intent.putExtra(Intent.EXTRA_SUBJECT, numberInText);
        intent.putExtra(Intent.EXTRA_TITLE, numberInText);
        intent.setType("text/plain");
        startActivity(new Intent().createChooser(intent, "Share Via"));
    }

    private void copyContent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(VIBRATE_DURATION, VIBRATE_AMPLITUDE));
        }
        ClipData clipData = ClipData.newPlainText("text/numberinburmese", numberInText);
        clipboardManager.setPrimaryClip(clipData);
        Alerter.create(this)
                .setTitle("Text copied.")
                .setDuration(1000)
                .setBackgroundColorRes(R.color.orange) // or setBackgroundColorInt(Color.CYAN)
                .show();
    }

    private void clearAllDigits() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(50, VIBRATE_AMPLITUDE));
        }
        // clear all characters
        number.setLength(0);
        numberText.setLength(0);

        // update it on UI
        numberView.setText("0");
        numberTextView.setText(burmese?DEFAULT_TEXT_MM:DEFAULT_TEXT_ENG);
        Log.i(TAG, "clearAllDigits: Clear string builder");
        System.gc();
    }

    private void deleteOneDigit() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(VIBRATE_DURATION, VIBRATE_AMPLITUDE));
        }
        Log.i(TAG, "deleteOneDigit: Delete One Character");
        // remove last character
        if (number.length() > 1 ) {
            number.deleteCharAt(number.length() - 1);

            // add thousand separtor
            String formatted_str = addThousandSeparator();
            // update the string again
            numberView.setText(formatted_str);

            // update Number Text View
            // convert the number to
            if (burmese){
                numberInText =  converterMm.convertToWord(Long.parseLong(number.toString()));
            }else{
                numberInText = converterEng.convertToWord(Long.parseLong(number.toString()));
            }

            // set it on UI
            numberTextView.setText(numberInText);
        }else if(number.length() == 1 && number.charAt(0) != '0'){
            number.setLength(0);
            numberView.setText("0");
            numberTextView.setText(burmese?DEFAULT_TEXT_MM:DEFAULT_TEXT_ENG);
        }
    }



    private void buildNumber(int num){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(30, 10));
        }
        if (number.length()>12){
            // reach limit
            Toasty.custom(this,
                    "The number reached limit of 13 digits.",
                    R.drawable.ic_baseline_info_24,
                    R.color.radiant,
                    Toasty.LENGTH_SHORT,
                    true,
                    true).show();
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
        if (burmese) {
            numberInText = number.toString().equals("") ? DEFAULT_TEXT_MM : converterMm.convertToWord(Long.parseLong(number.toString()));
        }else{
            numberInText = number.toString().equals("") ? DEFAULT_TEXT_ENG : converterEng.convertToWord(Long.parseLong(number.toString()));
        }
        // set it on UI
        numberTextView.setText(numberInText);

    }

    private String addThousandSeparator(){
        DecimalFormat decim = new DecimalFormat("#,###");
        return number.length()>3?decim.format(Long.parseLong(number.toString())):number.toString();
    }
}