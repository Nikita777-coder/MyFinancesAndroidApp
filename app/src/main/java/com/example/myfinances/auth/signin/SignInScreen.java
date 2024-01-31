package com.example.myfinances.auth.signin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myfinances.R;
import com.example.myfinances.auth.SignUpScreen;
import com.example.myfinances.auth.signin.reactions.HttpStatusBadRequestReaction;
import com.example.myfinances.auth.signin.reactions.HttpStatusOkReaction;
import com.example.myfinances.auth.signin.reactions.Reaction;

import java.util.HashMap;
import java.util.Map;

public class SignInScreen extends AppCompatActivity {
    private final AuthServiceSender authServiceSender;
    private final Map<HttpStatusCode, Reaction> httpStatusesReactions = new HashMap<Integer, Reaction>() {{
        put(HttpStatus.OK, new HttpStatusOkReaction());
        put(HttpStatus.BadReques, new tHttpStatusBadRequestReaction());
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_screen);

        final EditText publicData = findViewById(R.id.publicData);
        final EditText password = findViewById(R.id.password);
        final TextView signUpPage = findViewById(R.id.signUpPage);

        signUpPage.setOnClickListener(view ->
                startActivity(new Intent(SignInScreen.this, SignUpScreen.class))
        );

        AuthResponse authResponse = authServiceSender.signIn(AuthRequest.Builder.publicData(publicData).password(password).build());
        httpStatusesReactions.get(authResponse.getHttpStatusCode()).handle(authResponse.getMessage());
    }
}