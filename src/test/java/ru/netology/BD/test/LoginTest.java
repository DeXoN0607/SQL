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

    @Test
    @DisplayName("Успешная авторизация валидным пользователем")
    void shouldLoginSuccessfully() {
        var user = DataHelper.getValidUser();
        var loginPage = new LoginPage();

        VerificationPage verificationPage = loginPage.validLogin(user);
        var code = DbHelper.getVerificationCode(user.getLogin());

        verificationPage.invalidVerify(code);

        var dashboard = new DashboardPage();
        dashboard.shouldBeVisible();
    }

    @Test
    @DisplayName("Ошибка при неверном коде верификации")
    void shouldShowErrorForWrongCode() {
        var user = DataHelper.getValidUser();
        var loginPage = new LoginPage();

        VerificationPage verificationPage = loginPage.validLogin(user);
        var wrongCode = DataHelper.getWrongCode();

        verificationPage.invalidVerify(wrongCode.getCode());
        verificationPage.verifyInvalidCodeError();
    }

    @Test
    @DisplayName("Блокировка после 3 неудачных попыток ввода кода")
    void shouldBlockAfterThreeInvalidCodes() {
        var user = DataHelper.getValidUser();
        var wrongCode = DataHelper.getWrongCode();

        var loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.validLogin(user);

        verificationPage.invalidVerify(wrongCode.getCode());
        verificationPage.verifyInvalidCodeError();

        verificationPage.invalidVerify(wrongCode.getCode());
        verificationPage.verifyInvalidCodeError();

        verificationPage.invalidVerify(wrongCode.getCode());
        verificationPage.verifyBlockedError();
    }
}