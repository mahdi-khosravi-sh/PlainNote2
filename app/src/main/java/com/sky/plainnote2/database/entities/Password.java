package com.sky.plainnote2.database.entities;

import android.util.Log;

import com.sky.plainnote2.utility.Security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class Password {
    private static final String TAG = "Password";
    private String password;

    public Password() {
    }

    public Password(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Security.toMD5(password);
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password1 = (Password) o;
        return password.equals(password1.password);
    }

    public boolean equals(String password) {
        Log.i(TAG, "MD5_1 : " + Security.toMD5(password));
        Log.i(TAG, "MD5_2 : " + this.password);
        return this.password.equals(Security.toMD5(password));
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }
}
