package com.sky.plainnote2.database.type_converter;

import androidx.room.TypeConverter;

import com.sky.plainnote2.database.entities.Password;

public class PasswordConverter {
    @TypeConverter
    public static Password toPassword(String str) {
        return str == null ? null : new Password(str);
    }

    @TypeConverter
    public static String toString(Password password) {
        return password == null ? null : password.getPassword();
    }
}
