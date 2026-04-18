package ru.netology.BD.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.BD.BaseTest;
import ru.netology.BD.data.DataHelper;
import ru.netology.BD.db.DbHelper;
import ru.netology.BD.page.LoginPage;
import ru.netology.BD.page.VerificationPage;
import ru.netology.BD.page.DashboardPage;

public class LoginTest extends BaseTest {
    public class ErrorMessages {
        public static final String INVALID_CODE = "Неверно указан код! Попробуйте ещё раз.";
        public static final String BLOCKED = "Превышено количество попыток ввода кода";
    }

    @Test
    @DisplayName("Успешная авторизация валидным пользователем")
    void shouldLoginSuccessfully() {
        var user = DataHelper.getValidUser();
        var loginPage = new LoginPage();

        VerificationPage verificationPage = loginPage.validLogin(user);
        var code = DbHelper.getVerificationCode(user.getLogin());

        verificationPage.verify(code);

        new DashboardPage().shouldBeVisible();
    }

    @Test
    @DisplayName("Ошибка при неверном коде верификации")
    void shouldShowErrorForWrongCode() {
        var user = DataHelper.getValidUser();
        var loginPage = new LoginPage();

        VerificationPage verificationPage = loginPage.validLogin(user);
        var wrongCode = DataHelper.getWrongCode();

        verificationPage.verify(wrongCode.getCode());
        verificationPage.verifyErrorText(ErrorMessages.INVALID_CODE);
    }

    @Test
    @DisplayName("Блокировка после 3 неудачных попыток ввода кода")
    void shouldBlockAfterThreeInvalidCodes() {
        var user = DataHelper.getValidUser();
        var wrongCode = DataHelper.getWrongCode();

        var loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.validLogin(user);

        verificationPage.verify(wrongCode.getCode());
        verificationPage.verifyErrorText(ErrorMessages.INVALID_CODE);

        verificationPage.verify(wrongCode.getCode());
        verificationPage.verifyErrorText(ErrorMessages.INVALID_CODE);

        verificationPage.verify(wrongCode.getCode());
        verificationPage.verifyErrorText(ErrorMessages.BLOCKED);
    }
}