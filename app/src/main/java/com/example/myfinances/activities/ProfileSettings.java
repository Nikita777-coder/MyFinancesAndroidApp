package com.example.myfinances.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myfinances.R;
import com.example.myfinances.connectorservices.AuthConnectorService;
import com.example.myfinances.dto.UserOutData;
import com.example.myfinances.mappers.UserMapper;

public class ProfileSettings extends AppCompatActivity {
    private ImageButton backArrow;
    private EditText name;
    private EditText surname;
    private EditText login;
    private TextView email;
    private UserOutData userOutData;
    private final UserMapper userMapper = UserMapper.INSTANCE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        setupElements();
    }
    private void setupElements() {
        initElements();
        fillElements();

        backArrow.setOnClickListener(view -> clickBackPage());
    }
    private void initElements() {
        backArrow = findViewById(R.id.back_arrow);
        name = findViewById(R.id.profile_name);
        surname = findViewById(R.id.profile_surname);
        login = findViewById(R.id.profile_login);
        email = findViewById(R.id.profile_email);
        userOutData = this.getIntent().getParcelableExtra(getResources().getString(R.string.user_data));
    }
    private void fillElements() {
        email.setText(userOutData.getEmail());

        if (userOutData.getFirstName() != null) {
            name.setText(userOutData.getFirstName());
        }

        if (userOutData.getLastName() != null) {
            surname.setText(userOutData.getLastName());
        }

        if (userOutData.getLogin() != null) {
            login.setText(userOutData.getLogin());
        }
    }
    private <T extends Class> void clickBackPage() {
        userOutData.setFirstName(name.getText().toString());
        userOutData.setLastName(surname.getText().toString());
        userOutData.setLogin(login.getText().toString());
        var updateUser = userMapper.userOutDataToUpdateUserDto(userOutData);

        var ans = AuthConnectorService.updateUser(updateUser).body();

        Intent returnIntent = new Intent();
        returnIntent.putExtra("user_data", userMapper.updateUserDtoToUserOutData(ans));
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}