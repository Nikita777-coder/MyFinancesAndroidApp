package com.example.myfinances.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myfinances.R;
import com.example.myfinances.activities.forgotpassword.ForgotPasswordScreen;
import com.example.myfinances.dto.UserOutData;
import com.example.myfinances.httpreactions.signin.HttpStatusBadRequestReaction;
import com.example.myfinances.httpreactions.signin.HttpStatusNotFoundReaction;
import com.example.myfinances.httpreactions.signin.HttpStatusOkReaction;
import com.example.myfinances.httpreactions.HttpReactionInterface;
import com.example.myfinances.connectorservices.AuthConnectorService;
import com.example.myfinances.mappers.SignInMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Response;

public class SignInScreen extends AppCompatActivity {
    private EditText publicData;
    private EditText password;
    private AppCompatButton signInButton;
    private TextView forgotPassword;
    private final Map<Integer, HttpReactionInterface> httpStatusesReactions = new HashMap<>() {{
        put(200, new HttpStatusOkReaction());
        put(400, new HttpStatusBadRequestReaction());
        put(404, new HttpStatusNotFoundReaction());
    }};

    /**
     * store boolean flags for different elements which need for
     * different signInScreen if
     */
    private final Map<Integer, Boolean> signInScreenElementsFlags = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (BuildConfig.DEBUG) {
//            Timber.plant(new Timber.DebugTree());
//        }

        setContentView(R.layout.activity_sign_in_screen);
        setupScreenElements();
        final TextView signUpPage = findViewById(R.id.signUpPage);

        signUpPage.setOnClickListener(view ->
                startActivity(new Intent(SignInScreen.this, SignUpScreen.class))
        );
    }

    private void setupScreenElements() {
        publicData = findViewById(R.id.publicData);
        password = findViewById(R.id.password);
        signInButton = findViewById(R.id.signInBtn);
        forgotPassword = findViewById(R.id.forgotPassword);

        signInScreenElementsFlags.put(publicData.getId(), false);
        signInScreenElementsFlags.put(password.getId(), false);

//        addEditTextListenerAfterAction(publicData);
//        addEditTextListenerAfterAction(password);
        addEditTextCompletionTextListener(publicData);
        addEditTextCompletionTextListener(password);

        signInButton.setOnClickListener(view -> {
            activateSignIn();
        });

        publicData.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    publicData.setError(null);
                    publicData.setTextColor(getResources().getColor(R.color.white)); // Исходный цвет текста
                }
            }
        });

        forgotPassword.setOnClickListener(view -> startActivity(new Intent(this, ForgotPasswordScreen.class)));

    }
//    private void addEditTextListenerAfterAction(EditText editText) {
//        editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//        });
//    }
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

                    signInButton.setEnabled(Boolean.TRUE.equals(signInScreenElementsFlags.get(publicData.getId())) && Boolean.TRUE.equals(signInScreenElementsFlags.get(password.getId())));

                    return false; // pass on to other listeners.
                }
        );
    }
    private void activateSignIn() {
        Response<UserOutData> response = AuthConnectorService.signIn(
                SignInMapper.SIGN_IN_MAPPER.userDataToSignInRequest(
                        publicData.getText().toString(),
                        password.getText().toString()
                )
        );

        Objects.requireNonNull(httpStatusesReactions.get(response.code())).handle(response.body(), this);
    }
}