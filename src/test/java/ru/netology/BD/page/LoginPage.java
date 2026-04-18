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


    private void fillForm(AuthInfo authInfo) {
        loginInput.setValue(authInfo.getLogin());
        passwordInput.setValue(authInfo.getPassword());
    }

    public void login(AuthInfo authInfo) {
        fillForm(authInfo);
        loginButton.click();
    }

    public void verifyErrorText(String expectedText) {
        error.shouldBe(visible).shouldHave(text(expectedText));
    }

}