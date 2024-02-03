package com.example.myfinances.auth.signin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myfinances.R;
import com.example.myfinances.auth.signin.reactions.HttpStatusBadRequestReaction;
import com.example.myfinances.auth.signin.reactions.HttpStatusOkReaction;
import com.example.myfinances.auth.signin.reactions.Reaction;
import com.example.myfinances.auth.signup.SignUpScreen;
import com.example.myfinances.services.AuthServiceService;
import com.example.myfinances.services.auth.mappers.SignInMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Response;

public class SignInScreen extends AppCompatActivity {
    private final Map<Integer, Reaction> httpStatusesReactions = new HashMap<Integer, Reaction>() {{
        put(200, new HttpStatusOkReaction());
        put(400, new HttpStatusBadRequestReaction());
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

        Response<String> response = AuthServiceService.signIn(
                SignInMapper.SIGN_IN_MAPPER.userDataToSignInRequest(
                        publicData.getText().toString(),
                        password.getText().toString()
                )
        );

        Objects.requireNonNull(httpStatusesReactions.get(response.code())).handle(response.body());
    }
}