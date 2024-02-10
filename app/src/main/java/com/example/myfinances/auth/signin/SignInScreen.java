package com.example.myfinances.auth.signin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myfinances.R;
import com.example.myfinances.auth.signin.reactions.HttpStatusBadRequestReaction;
import com.example.myfinances.auth.signin.reactions.HttpStatusOkReaction;
import com.example.myfinances.auth.signin.reactions.Reaction;
import com.example.myfinances.auth.signup.SignUpScreen;
import com.example.myfinances.services.AuthService;
import com.example.myfinances.services.auth.mappers.SignInMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Response;
import timber.log.Timber;

public class SignInScreen extends AppCompatActivity {
    private EditText publicData;
    private EditText password;
    private AppCompatButton signInButton;
    private final Map<Integer, Reaction> httpStatusesReactions = new HashMap<>() {{
        put(200, new HttpStatusOkReaction());
        put(400, new HttpStatusBadRequestReaction());
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

        signInScreenElementsFlags.put(publicData.getId(), false);
        signInScreenElementsFlags.put(password.getId(), false);
        signInScreenElementsFlags.put(signInButton.getId(), false);

        addEditTextListenerAfterAction(publicData);
        addEditTextListenerAfterAction(password);
        addEditTextCompletionTextListener(publicData);
        addEditTextCompletionTextListener(password);

        signInButton.setOnClickListener(view -> {
            if (Boolean.TRUE.equals(signInScreenElementsFlags.get(signInButton.getId()))) {
                activateSignIn();
            }
        });
    }
    private void addEditTextListenerAfterAction(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                changeBackgroundColorOfSignInButton();
            }
        });
    }
    private void addEditTextCompletionTextListener(EditText editText) {
        editText.setOnEditorActionListener(
                (v, actionId, event) -> {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                            actionId == EditorInfo.IME_ACTION_DONE ||
                            event != null &&
                                    event.getAction() == KeyEvent.ACTION_DOWN &&
                                    event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        if (event == null || !event.isShiftPressed()) {
                            signInScreenElementsFlags.put(editText.getId(), true);
                            return true; // consume.
                        }
                    }

                    signInScreenElementsFlags.put(editText.getId(), false);
                    return false; // pass on to other listeners.
                }
        );
    }
    private void changeBackgroundColorOfSignInButton() {
        int color;

        if (Boolean.TRUE.equals(signInScreenElementsFlags.get(publicData.getId())) && Boolean.TRUE.equals(signInScreenElementsFlags.get(password.getId()))) {
            color = ContextCompat.getColor(this, R.color.activeSignInButton);
            signInButton.setBackgroundColor(color);
            signInScreenElementsFlags.put(signInButton.getId(), true);
        } else {
            color = ContextCompat.getColor(this, R.color.notActiveSignInButton);
            signInButton.setBackgroundColor(color);
            signInScreenElementsFlags.put(signInButton.getId(), false);
        }
    }
    private void activateSignIn() {
        Response<String> response = AuthService.signIn(
                SignInMapper.SIGN_IN_MAPPER.userDataToSignInRequest(
                        publicData.getText().toString(),
                        password.getText().toString()
                )
        );

        Objects.requireNonNull(httpStatusesReactions.get(response.code())).handle(response.body());
    }
}