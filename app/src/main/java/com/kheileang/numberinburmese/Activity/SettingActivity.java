package com.kheileang.numberinburmese.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnPaidEventListener;
import com.google.android.gms.ads.ResponseInfo;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.kheileang.numberinburmese.R;

import es.dmoral.toasty.Toasty;

public class SettingActivity extends AppCompatActivity {

    private AdView adView;
    private InterstitialAd minterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        // Banner ad
        adView = findViewById(R.id.adView1);
        adView.loadAd(new AdRequest.Builder().build());

        // Interstitial Ad
        InterstitialAd.load(this, "ca-app-pub-1589742015897303/6953136916",
                new AdRequest.Builder().build(),
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        super.onAdLoaded(interstitialAd);
                        minterstitialAd = interstitialAd;
                        minterstitialAd.show(getParent());
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                    }
                });
    }

    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.rate_setting:
                rateMyApp();
                break;
            case R.id.share_setting:
                shareMyApp();
                break;
            case R.id.other_app_setting:
                viewOtherApps();
                break;
            case R.id.policy_setting:
                showPolicy();
                break;
            case R.id.howto_setting:
                showHowTo();
                break;
            case R.id.demo_setting:
                showDemo();
                break;
            case R.id.notworking_setting:
                reportDev("App Not Working", "App Not Working");
                break;
            case R.id.feedback_setting:
                reportDev("Feedback", "Your app can be improved..");
                break;
            case R.id.aboutus_setting:
                aboutUs();
                break;
        }
    }

    private void aboutUs() {
        AlertDialog.Builder aboutus = new AlertDialog.Builder(this);
        aboutus.setTitle("Abous This App");
        aboutus.setMessage(R.string.aboutus);
        aboutus.show();
    }

    private void reportDev(String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"+"lover.music.sick@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);

        startActivity(Intent.createChooser(intent, "Send Email"));
    }


    private void showDemo() {
        Toasty.info(this, "Demo Video Unavailable", Toasty.LENGTH_SHORT).show();
    }


    private void showHowTo() {
        AlertDialog.Builder howTo = new AlertDialog.Builder(this);
        howTo.setTitle("How To Use");
        howTo.setMessage(R.string.howto);
        howTo.setCancelable(true);
        howTo.setPositiveButton("OK", null);
        howTo.show();

    }

    private void showPolicy() {
        final String privacyUrl = "https://sites.google.com/view/alpa-privacy-policy";

        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(privacyUrl)));
    }

    private void viewOtherApps() {
        final String comName = "King";

        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?pub="+comName)));
    }

    private void shareMyApp() {
        final String appPackageName = "https://play.google.com/store/apps/details?id=com.king.candycrushsaga";

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "MY APP");
        intent.putExtra(Intent.EXTRA_TEXT, appPackageName);
        startActivity(Intent.createChooser(intent, "Share Via"));
    }

    private void rateMyApp() {
        final String appPackageName = "com.king.candycrushsaga";
        try{
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id="+appPackageName)));
        }catch (android.content.ActivityNotFoundException e){
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://detail?id="+appPackageName)));
        }
    }

}