package ru.netology.BD.test;

import org.junit.jupiter.api.Test;
import ru.netology.BD.BaseTest;
import ru.netology.BD.data.DataHelper;
import ru.netology.BD.db.DbHelper;
import ru.netology.BD.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class LoginTest extends BaseTest {

    @Test
    void shouldLoginSuccessfully() {
        var user = DataHelper.getValidUser();

        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(user);

        String code = DbHelper.getVerificationCode(user.getLogin());

        var dashboard = verificationPage.validVerify(code);
        dashboard.shouldBeVisible();
    }

    @Test
    void shouldShowErrorForWrongPassword() {
        var user = DataHelper.getInvalidUser();

        var loginPage = new LoginPage();
        loginPage.invalidLogin(user);
    }

    @Test
    void shouldShowErrorForWrongCode() {
        var user = DataHelper.getValidUser();

        var loginPage = new LoginPage();
        var verificationPage = loginPage.validLogin(user);

        verificationPage.invalidVerify("000000", "Ошибка! Неверно указан код! Попробуйте ещё раз.");
    }

    @Test
    void shouldShowBlockMessageAfterMultipleInvalidCodes() {
        var user = DataHelper.getValidUser();
        final String BLOCK_MESSAGE = "Превышено количество попыток ввода кода!";
        final String INVALID_CODE_MESSAGE = "Ошибка! Неверно указан код! Попробуйте ещё раз.";

        int maxAttempts = 10;
        int attempt = 0;
        boolean isBlocked = false;

        while (!isBlocked && attempt < maxAttempts) {
            attempt++;

            var loginPage = new LoginPage();
            var verificationPage = loginPage.validLogin(user);

            verificationPage.enterCode("000000");

            String actualMessage = verificationPage.getErrorMessage();

            if (actualMessage.contains(BLOCK_MESSAGE)) {
                isBlocked = true;
                System.out.println("Блокировка сработала на попытке: " + attempt);
            } else if (actualMessage.contains(INVALID_CODE_MESSAGE)) {
                System.out.println("Попытка " + attempt + ": неверный код, повторяем...");
                open("/");
            } else {
                fail("Неожиданное сообщение: " + actualMessage);
            }
        }

        assertTrue(isBlocked, "Система не заблокировалась после " + maxAttempts + " попыток");
    }
}