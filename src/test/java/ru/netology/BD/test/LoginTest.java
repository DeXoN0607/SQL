package ru.netology.BD.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.BD.BaseTest;
import ru.netology.BD.data.DataHelper;
import ru.netology.BD.db.DbHelper;
import ru.netology.BD.page.*;

public class LoginTest extends BaseTest {

    private static final String INVALID_CODE =
            "Неверно указан код! Попробуйте ещё раз.";
    private static final String BLOCKED_CODE =
            "Превышено количество попыток ввода кода";

    @Test
    @DisplayName("Успешная авторизация валидным пользователем")
    void shouldLoginSuccessfully() {
        var user = DataHelper.getValidUser();
        var loginPage = new LoginPage();

        loginPage.login(user);

        var verificationPage = new VerificationPage();
        var code = DbHelper.getVerificationCode(user.getLogin());

        verificationPage.verify(code);

        new DashboardPage().shouldBeVisible();
    }

    @Test
    @DisplayName("Ошибка при неверном логине")
    void shouldShowErrorForInvalidLogin() {
        var user = DataHelper.getInvalidUser();
        var loginPage = new LoginPage();

        loginPage.login(user);

        loginPage.verifyErrorText("Неверно указан логин или пароль");
    }

    @Test
    @DisplayName("Ошибка при неверном коде верификации")
    void shouldShowErrorForWrongCode() {
        var user = DataHelper.getValidUser();
        var loginPage = new LoginPage();

        loginPage.login(user);

        var verificationPage = new VerificationPage();
        var wrongCode = DataHelper.getWrongCode();

        verificationPage.verify(wrongCode.getCode());
        verificationPage.verifyErrorText(INVALID_CODE);
    }

    @Test
    @DisplayName("Блокировка после 3 неудачных попыток")
    void shouldBlockAfterThreeInvalidCodes() {
        var user = DataHelper.getValidUser();
        var wrongCode = DataHelper.getWrongCode();

        var loginPage = new LoginPage();
        loginPage.login(user);

        var verificationPage = new VerificationPage();

        verificationPage.verify(wrongCode.getCode());
        verificationPage.verifyErrorText(INVALID_CODE);

        verificationPage.verify(wrongCode.getCode());
        verificationPage.verifyErrorText(INVALID_CODE);

        verificationPage.verify(wrongCode.getCode());
        verificationPage.verifyErrorText(BLOCKED_CODE);
    }
}