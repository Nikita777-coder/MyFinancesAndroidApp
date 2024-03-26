package com.example.myfinances.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfinances.R;
import com.example.myfinances.httpreactions.HttpReactionInterface;
import com.example.myfinances.httpreactions.signuppage.HttpStatusBadRequestReaction;
import com.example.myfinances.httpreactions.signuppage.HttpStatusForbiddenReaction;
import com.example.myfinances.httpreactions.signuppage.HttpStatusNotFoundReaction;
import com.example.myfinances.httpreactions.signuppage.HttpStatusOkReaction;
import com.example.myfinances.connectorservices.AuthConnectorService;
import com.example.myfinances.dto.EmailVerificationRequest;
import com.goodiebag.pinview.Pinview;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Response;

public class SignUpScreen extends AppCompatActivity {
    private EditText email;
    private Pinview pinview;
    private AppCompatButton signUpButton;
    private LinearLayout verifyCodeElements;
    private final int totalSeconds = 60;
    private final int updateInterval = 1000;
    private TextView sendCodeView;
    private Intent passwordPageIntent;
    private Map<Integer, HttpReactionInterface> httpStatusesReactions = new HashMap<>() {{
        put(200, new HttpStatusOkReaction());
        put(400, new HttpStatusBadRequestReaction());
        put(404, new HttpStatusNotFoundReaction());
        put(403, new HttpStatusForbiddenReaction());
    }};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        setupScreenElements();
    }
    private void setupScreenElements() {
        initElements();

        final TextView signInPage = findViewById(R.id.signInPage);
        signInPage.setOnClickListener(view ->
                startActivity(new Intent(SignUpScreen.this, SignInScreen.class))
        );

        passwordPageIntent = new Intent(this, SignUpPasswordScreen.class);
        signUpButton.setOnClickListener(view ->
                startActivity(passwordPageIntent)
        );

        setupPinview();
        setupSendCodeView();

        addEditTextCompletionTextListener(email);
    }
    private void onToggleButtonClicked() {
        AuthConnectorService.sendEmailVerificationCode(email.getText().toString());
        verifyCodeElements.setVisibility(View.VISIBLE);
        sendCodeView.setTextColor(getResources().getColor(R.color.hint));
        sendCodeView.setClickable(false);

        new CountDownTimer(totalSeconds * 1000L, updateInterval) {
            int time = totalSeconds;

            @Override
            public void onTick(long millisUntilFinished) {
                int minutes = time / 60;
                int seconds = time % 60;
                sendCodeView.setText(String.format("%s %s:%s", getResources().getString(R.string.send_code_const_string), getTime(minutes), getTime(seconds)));
                time--;
            }

            @Override
            public void onFinish() {
                verifyCodeElements.setVisibility(View.GONE);
                pinview.setPinBackgroundRes(R.drawable.code_element_background);
                pinview.clearValue();
                findViewById(R.id.verify_error_message).setVisibility(View.GONE);
                sendCodeView.setText(getResources().getText(R.string.send_code_const_string));
                sendCodeView.setTextColor(getResources().getColor(R.color.white));
                sendCodeView.setClickable(true);
            }
        }.start();

    }
    private void addEditTextCompletionTextListener(EditText editText) {
        editText.setOnEditorActionListener(
                (v, actionId, event) -> {
                    if (event != null &&
                            event.getAction() == KeyEvent.ACTION_DOWN &&
                            event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        if (!event.isShiftPressed()) {
                            if (!isEmail()) {
                                return true;
                            }

                            sendCodeView.setEnabled(true);
                            return true; // consume.
                        }
                    }

                    if (event != null
                            && !editText.getText().toString().equals("")
                            && event.getAction() != KeyEvent.ACTION_DOWN
                            && event.getKeyCode() != KeyEvent.KEYCODE_ENTER) {
                        sendCodeView.setEnabled(false);
                    }

                    return false; // pass on to other listeners.
                }
        );
    }
    private String getTime(int time) {
        return time <= 9 ? "0" + time : String.valueOf(time);
    }
    private boolean isEmail() {
        if (!email.getText().toString().matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
            email.setError("The email string is not email");
            return false;
        }

        return true;
    }
    private void setupPinview() {
        pinview.setPinViewEventListener((pinview, fromUser) -> {
            if (pinview.getValue().length() == 4) {
                var request = new EmailVerificationRequest(email.getText().toString(), pinview.getValue());
                Response<String> verifyResponse = AuthConnectorService.verifyEmail(request);
                passwordPageIntent.putExtra("email", email.getText().toString());
                Objects.requireNonNull(httpStatusesReactions.get(verifyResponse.code())).handle(verifyResponse.body(), this);
            }

            Toast.makeText(SignUpScreen.this, pinview.getValue(), Toast.LENGTH_SHORT).show();
        });

        pinview.setTextColor(getResources().getColor(R.color.white));
    }
    private void setupSendCodeView() {
        sendCodeView.setOnClickListener(
                v -> {
                    onToggleButtonClicked();
                }
        );
    }
    private void initElements() {
        email = findViewById(R.id.email);
        pinview = findViewById(R.id.verify_code);
        signUpButton = findViewById(R.id.signUpBtn);
        sendCodeView = findViewById(R.id.sendCodeView);
        verifyCodeElements = findViewById(R.id.verify_code_elements);
    }
}