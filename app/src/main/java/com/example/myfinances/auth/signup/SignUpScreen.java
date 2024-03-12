package com.example.myfinances.auth.signup;

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
import android.widget.ToggleButton;

import com.example.myfinances.R;
import com.example.myfinances.auth.signin.reactions.HttpStatusBadRequestReaction;
import com.example.myfinances.auth.signin.reactions.HttpStatusNotFoundReaction;
import com.example.myfinances.auth.signin.reactions.HttpStatusOkReaction;
import com.example.myfinances.auth.signin.reactions.Reaction;
import com.example.myfinances.auth.signin.SignInScreen;
import com.example.myfinances.services.AuthService;
import com.example.myfinances.services.auth.dto.EmailVerificationRequest;
import com.goodiebag.pinview.Pinview;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Response;

public class SignUpScreen extends AppCompatActivity {
    private EditText email;
    private Pinview pinview;
    private AppCompatButton signUpButton;
    private LinearLayout verifyCodeElements;
    private ToggleButton sendCodeButton;
//    private Map<Integer, Reaction> httpStatusesReactions = new HashMap<>() {{
//        put(200, new HttpStatusOkReaction());
//        put(400, new HttpStatusBadRequestReaction());
//        put(404, new HttpStatusNotFoundReaction());
//        put(403, new HttpStatusForbiddenReaction());
//    }};
    public void onToggleButtonClicked() {
        AuthService.sendEmailVerificationCode(email.getText().toString());
        new CountDownTimer(60000, 1000) {
            int time=60;
            public void onTick(long millisUntilFinished) {
                verifyCodeElements.setVisibility(View.VISIBLE);
                int minutes = time / 60;
                int seconds = time % 60;
                sendCodeButton.setTextColor(getResources().getColor(R.color.hint));
                sendCodeButton.setTextOn(String.format("%s %s:%s", getResources().getString(R.string.send_code_const_string), checkDigit(minutes), checkDigit(seconds)));
                time--;
            }

            public void onFinish() {
                verifyCodeElements.setVisibility(View.GONE);
                sendCodeButton.setTextColor(getResources().getColor(R.color.white));
                sendCodeButton.setTextOn(sendCodeButton.getTextOff());
            }

        }.start();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_screen);
        setupScreenElements();
    }
    private void setupScreenElements() {
        email = findViewById(R.id.email);
        pinview = findViewById(R.id.verify_code);
        signUpButton = findViewById(R.id.signUpBtn);
        sendCodeButton = findViewById(R.id.sendCodeButton);
        verifyCodeElements = findViewById(R.id.verify_code_elements);
        final TextView signInPage = findViewById(R.id.signInPage);

        signInPage.setOnClickListener(view ->
                startActivity(new Intent(SignUpScreen.this, SignInScreen.class))
        );

        signUpButton.setOnClickListener(view -> activateSignUpBtn());

        addEditTextCompletionTextListener(email);
        pinview.setPinViewEventListener((pinview, fromUser) ->
                Toast.makeText(SignUpScreen.this, pinview.getValue(), Toast.LENGTH_SHORT).show());

        pinview.setPinViewEventListener((pinview, fromUser) ->
                signUpButton.setEnabled(pinview.getValue().length() == pinview.getPinLength()));
    }
    private void addEditTextCompletionTextListener(EditText editText) {
        editText.setOnEditorActionListener(
                (v, actionId, event) -> {
                    if (event != null &&
                            event.getAction() == KeyEvent.ACTION_DOWN &&
                            event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        if (!event.isShiftPressed()) {
                            if (!checkEmail()) {
                                return true;
                            }
                            sendCodeButton.setEnabled(true);
                            return true; // consume.
                        }
                    }

                    if (event != null
                            && !editText.getText().toString().equals("")
                            && event.getAction() != KeyEvent.ACTION_DOWN
                            && event.getKeyCode() != KeyEvent.KEYCODE_ENTER) {
                        sendCodeButton.setEnabled(false);
                    }

                    return false; // pass on to other listeners.
                }
        );
    }

    private String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }
    private void activateSignUpBtn() {
        EmailVerificationRequest emailVerificationRequest = new EmailVerificationRequest(email.getText().toString(), pinview.getValue());
        Response<String> verifyResponse = AuthService.verifyEmail(emailVerificationRequest);

    }
    private boolean checkEmail() {
        if (!email.getText().toString().matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
            email.setError("The email string is not email");
            return false;
        }

        return true;
    }
}