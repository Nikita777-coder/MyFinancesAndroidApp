package com.example.myfinances.activities.forgotpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.myfinances.R;
import com.example.myfinances.connectorservices.AuthConnectorService;
import com.example.myfinances.dto.ChangePasswordDto;
import com.example.myfinances.activities.MyStockPortfolioScreen;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordScreen extends AppCompatActivity {
    private EditText createPassword;
    private EditText repeatPassword;
    private AppCompatButton continueUpBtn;
    private final Map<Integer, Boolean> changePasswordElementsFlags = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_screen);

        setupElements();
    }
    private void setupElements() {
        initElements();

        changePasswordElementsFlags.put(createPassword.getId(), false);
        changePasswordElementsFlags.put(repeatPassword.getId(), false);

        addEditTextCompletionTextListener(createPassword);
        addEditTextCompletionTextListener(repeatPassword);

        continueUpBtn.setOnClickListener(view -> activateContinueBtn());
    }
    private void initElements() {
        createPassword = findViewById(R.id.createPassword);
        repeatPassword = findViewById(R.id.repeatPassword);
        continueUpBtn = findViewById(R.id.continueBtn);
    }
    private void addEditTextCompletionTextListener(EditText editText) {
        editText.setOnEditorActionListener(
                (v, actionId, event) -> {
                    if (event != null &&
                            event.getAction() == KeyEvent.ACTION_DOWN &&
                            event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        if (!event.isShiftPressed()) {
                            changePasswordElementsFlags.put(editText.getId(), true);
                            return true; // consume.
                        }
                    }

                    if (event != null
                            && !editText.getText().toString().equals("")
                            && event.getAction() != KeyEvent.ACTION_DOWN
                            && event.getKeyCode() != KeyEvent.KEYCODE_ENTER) {
                        changePasswordElementsFlags.put(editText.getId(), false);
                    }

                    continueUpBtn.setEnabled(Boolean.TRUE.equals(changePasswordElementsFlags.get(createPassword.getId()))
                            && Boolean.TRUE.equals(changePasswordElementsFlags.get(repeatPassword.getId()))
                            && createPassword.getText().toString().equals(repeatPassword.getText().toString()));

                    if (Boolean.TRUE.equals(changePasswordElementsFlags.get(createPassword.getId()))
                            && Boolean.TRUE.equals(changePasswordElementsFlags.get(repeatPassword.getId()))
                            && !createPassword.getText().toString().equals(repeatPassword.getText().toString())) {
                        repeatPassword.setError(getResources().getText(R.string.repeat_password_error));
                    }

                    return false; // pass on to other listeners.
                }
        );
    }
    private void activateContinueBtn() {
        ChangePasswordDto signUpRequest = new ChangePasswordDto(this.getIntent().getStringExtra("email"), repeatPassword.getText().toString());
        AuthConnectorService.changePassword(signUpRequest);
        startActivity(new Intent(this, MyStockPortfolioScreen.class));
    }
}