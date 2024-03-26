package com.example.myfinances.httpreactions.signuppassword;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfinances.R;
import com.example.myfinances.httpreactions.HttpReactionInterface;

public class HttpStatusBadRequestReaction implements HttpReactionInterface {
    private final int displayTimeError = 3;
    private final int updateInterval = 1000;
    @Override
    public void handle(String message, AppCompatActivity activity) {
        TextView view = activity.findViewById(R.id.error_text_view);
        view.setVisibility(View.VISIBLE);
        view.setText(activity.getResources().getText(R.string.user_exists));
        new CountDownTimer(displayTimeError * 1000L, updateInterval) {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                view.setText("");
                view.setVisibility(View.GONE);
            }
        };
    }
}
