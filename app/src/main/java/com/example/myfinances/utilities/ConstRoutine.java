package com.example.myfinances.utilities;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.example.myfinances.R;
import com.goodiebag.pinview.Pinview;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ConstRoutine {
    private static final int displayTimeError = 3;
    private static final int updateInterval = 1000;
    public static void setupVerificationCodeElementsOnError(Pinview pinview, TextView textView) {
        pinview.clearValue();
        pinview.setPinBackgroundRes(R.drawable.pin_error_background);

        textView.setVisibility(View.VISIBLE);
        pinview.showCursor(false);
        new CountDownTimer(displayTimeError * 1000L, updateInterval) {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                pinview.setPinBackgroundRes(R.drawable.code_element_background);
                textView.setVisibility(View.GONE);
                pinview.showCursor(true);
            }
        }.start();
    }
}
