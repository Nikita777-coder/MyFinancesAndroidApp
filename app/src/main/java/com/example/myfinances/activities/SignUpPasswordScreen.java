package com.example.myfinances.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;

import com.example.myfinances.R;
import com.example.myfinances.httpreactions.HttpReactionInterface;
import com.example.myfinances.httpreactions.signuppassword.HttpStatusBadRequestReaction;
import com.example.myfinances.httpreactions.signuppassword.HttpStatusOkReaction;
import com.example.myfinances.connectorservices.AuthConnectorService;
import com.example.myfinances.dto.SignUpRequest;

import java.util.HashMap;
import java.util.Map;

public class SignUpPasswordScreen extends AppCompatActivity {
    private EditText createPassword;
    private EditText repeatPassword;
    private AppCompatButton signUpBtn;
    private final Map<Integer, Boolean> signInScreenElementsFlags = new HashMap<>();
    private Map<Integer, HttpReactionInterface> httpStatusesReactions = new HashMap<>() {{
        put(200, new HttpStatusOkReaction());
        put(400, new HttpStatusBadRequestReaction());
    }};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_password_screen);

        setupElements();
    }
    private void setupElements() {
        initElements();

        signInScreenElementsFlags.put(createPassword.getId(), false);
        signInScreenElementsFlags.put(repeatPassword.getId(), false);

        addEditTextCompletionTextListener(createPassword);
        addEditTextCompletionTextListener(repeatPassword);

        signUpBtn.setOnClickListener(view -> activateSignUpBtn());
    }
    private void initElements() {
        createPassword = findViewById(R.id.createPassword);
        repeatPassword = findViewById(R.id.repeatPassword);
        signUpBtn = findViewById(R.id.signUpBtn);
    }
    private void addEditTextCompletionTextListener(EditText editText) {
        editText.setOnEditorActionListener(
                (v, actionId, event) -> {
                    if (event != null &&
                            event.getAction() == KeyEvent.ACTION_DOWN &&
                            event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        if (!event.isShiftPressed()) {
                            signInScreenElementsFlags.put(editText.getId(), true);
                            return true; // consume.
                        }
                    }

                    if (event != null
                            && !editText.getText().toString().equals("")
                            && event.getAction() != KeyEvent.ACTION_DOWN
                            && event.getKeyCode() != KeyEvent.KEYCODE_ENTER) {
                        signInScreenElementsFlags.put(editText.getId(), false);
                    }

                    signUpBtn.setEnabled(Boolean.TRUE.equals(signInScreenElementsFlags.get(createPassword.getId()))
                            && Boolean.TRUE.equals(signInScreenElementsFlags.get(repeatPassword.getId()))
                            && createPassword.getText().toString().equals(repeatPassword.getText().toString()));

                    if (Boolean.TRUE.equals(signInScreenElementsFlags.get(createPassword.getId()))
                            && Boolean.TRUE.equals(signInScreenElementsFlags.get(repeatPassword.getId()))
                            && !createPassword.getText().toString().equals(repeatPassword.getText().toString())) {
                        repeatPassword.setError(getResources().getText(R.string.repeat_password_error));
                    }

                    return false; // pass on to other listeners.
                }
        );
    }
    private void activateSignUpBtn() {
        SignUpRequest signUpRequest = new SignUpRequest(this.getIntent().getStringExtra("email"), repeatPassword.getText().toString());
        var response = AuthConnectorService.signUp(signUpRequest);
        httpStatusesReactions.get(response.code()).handle("", this);
    }
}