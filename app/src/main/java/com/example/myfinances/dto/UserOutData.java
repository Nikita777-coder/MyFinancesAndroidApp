package com.example.myfinances.dto;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserOutData implements Parcelable {
    private String login;
    private String email;
    private String firstName;
    private String lastName;
    private String password;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(login);
        dest.writeString(email);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(password);
    }

    public static final Parcelable.Creator<UserOutData> CREATOR = new Parcelable.Creator<UserOutData>() {
        public UserOutData createFromParcel(Parcel in) {
            return new UserOutData(in);
        }

        public UserOutData[] newArray(int size) {
            return new UserOutData[size];
        }
    };

    private UserOutData(Parcel in) {
        login = in.readString();
        email = in.readString();
        firstName = in.readString();
        lastName = in.readString();
        password = in.readString();
    }
}
