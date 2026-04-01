package ru.netology.BD.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.BD.data.AuthInfo;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    private SelenideElement loginInput = $("[data-test-id=login] input");
    private SelenideElement passwordInput = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement error = $("[data-test-id=error-notification]");
    private SelenideElement errorText = error.$(".notification__content");

    private void fillForm(AuthInfo authInfo) {
        loginInput.setValue(authInfo.getLogin());
        passwordInput.setValue(authInfo.getPassword());
    }

    public VerificationPage validLogin(AuthInfo authInfo) {
        fillForm(authInfo);
        loginButton.click();
        return new VerificationPage();
    }

    public void invalidLogin(AuthInfo authInfo) {
        fillForm(authInfo);
        loginButton.click();
    }

    // Проверка текста ошибки, не только видимости
    public void verifyErrorText(String expectedText) {
        error.shouldBe(visible).shouldHave(text(expectedText));
    }

    public void verifyWrongCredentialsError() {
        verifyErrorText("Неверно указан логин или пароль");
    }

    public void verifyBlockedError() {
        verifyErrorText("Пользователь заблокирован");
    }
}