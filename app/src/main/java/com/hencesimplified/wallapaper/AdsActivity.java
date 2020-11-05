package com.hencesimplified.wallapaper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.unity3d.ads.IUnityAdsListener;
import com.unity3d.ads.UnityAds;

public class AdsActivity extends AppCompatActivity {

    private String url_img;
    Button btn_retry;
    TextView txt_internet_err, txt_internet_err2;
    ProgressBar pgr_internet;

    UnityAdsListener listener = new UnityAdsListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad__screen);

        UnityAds.initialize(AdsActivity.this, "UNITY_GAME_ID", listener);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        Intent intent = getIntent();
        url_img = intent.getStringExtra("img_url");

        btn_retry = findViewById(R.id.btn_retry_internet);
        txt_internet_err = findViewById(R.id.txt_internet_err);
        txt_internet_err2 = findViewById(R.id.txt_internet_err2);
        pgr_internet = findViewById(R.id.pgr_internet);

        btn_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UnityAds.initialize(AdsActivity.this, "UNITY_GAME_ID", listener);
                onStart();

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private class UnityAdsListener implements IUnityAdsListener {

        @Override
        public void onUnityAdsReady(String s) {
            btn_retry.setVisibility(View.VISIBLE);
        }

        @Override
        public void onUnityAdsStart(String s) {

        }

        @Override
        public void onUnityAdsFinish(String s, UnityAds.FinishState finishState) {

            finish();
            Intent PhotoIntent1 = new Intent(AdsActivity.this, PhotoViewActivity.class);
            PhotoIntent1.putExtra("img_url_ad", url_img);
            startActivity(PhotoIntent1);

        }

        @Override
        public void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String s) {

            txt_internet_err.setVisibility(View.VISIBLE);
            txt_internet_err2.setVisibility(View.VISIBLE);
            UnityAds.initialize(AdsActivity.this, "UNITY_GAME_ID", listener);
            UnityAds.show(AdsActivity.this, "rewardedVideo");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (UnityAds.isReady("rewardedVideo")) {
            //UnityAds.initialize(ad_Screen.this,"3290908",listener);
            UnityAds.show(AdsActivity.this, "rewardedVideo");
        } else {
            UnityAds.initialize(AdsActivity.this, "UNITY_GAME_ID", listener);
            txt_internet_err.setVisibility(View.VISIBLE);
            txt_internet_err2.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {

        GoHome();
    }

    public void GoHome() {
        finish();
        Intent MainIntent = new Intent(AdsActivity.this, MainActivity.class);
        startActivity(MainIntent);
    }


}
