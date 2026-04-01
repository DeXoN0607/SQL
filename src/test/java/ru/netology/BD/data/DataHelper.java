package ru.netology.BD.data;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;

import java.util.Locale;

@UtilityClass
public class DataHelper {
    private static final Faker faker = new Faker(new Locale("ru"));

    public static AuthInfo getValidUser() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getInvalidUser() {
        return new AuthInfo(faker.name().username(), faker.internet().password());
    }

    public static AuthInfo getUserWithInvalidPassword(String login) {
        return new AuthInfo(login, faker.internet().password());
    }

    public static VerificationCode getWrongCode() {
        return new VerificationCode(faker.number().digits(6));
    }
}