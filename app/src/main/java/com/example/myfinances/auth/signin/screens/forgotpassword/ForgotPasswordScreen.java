package com.example.myfinances.auth.signin.screens.forgotpassword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;

import com.example.myfinances.R;
import com.example.myfinances.connectorservices.AuthConnectorService;

public class ForgotPasswordScreen extends AppCompatActivity {
    private EditText email;
    private AppCompatButton continueButton;
    private Intent changePasswordIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_screen);

        setupElements();
    }
    private void setupElements() {
        initElements();
        continueButton.setOnClickListener(view -> {
            try {
                startActivity(changePasswordIntent);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        addEditTextCompletionTextListener(email);
    }
    private void addEditTextCompletionTextListener(EditText editText) {
        editText.setOnEditorActionListener(
                (v, actionId, event) -> {
                    if (event != null &&
                            event.getAction() == KeyEvent.ACTION_DOWN &&
                            event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        if (!event.isShiftPressed()) {
                            String emailText = email.getText().toString();
                            var response = AuthConnectorService.isEmailExistsInApp(emailText);

                            if (response.body().booleanValue()) {
                                changePasswordIntent.putExtra("email", emailText);
                                continueButton.setEnabled(true);
                            } else {
                                email.setError(getResources().getText(R.string.email_not_exist));
                            }

                            return true; // consume.
                        }
                    }

                    return false; // pass on to other listeners.
                }
        );
    }
    private void initElements() {
        email = findViewById(R.id.email);
        continueButton = findViewById(R.id.continueBtn);
        changePasswordIntent = new Intent(this, ChangePasswordScreen.class);
    }
}