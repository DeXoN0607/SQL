package ru.netology.BD.page;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.visible;

import com.codeborne.selenide.SelenideElement;
import ru.netology.BD.data.DataHelper;

public class LoginPage {

    private SelenideElement loginInput = $("[data-test-id=login] input");
    private SelenideElement passwordInput = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement error = $("[data-test-id=error-notification]");

    public VerificationPage validLogin(DataHelper.AuthInfo authInfo) {
        loginInput.setValue(authInfo.getLogin());
        passwordInput.setValue(authInfo.getPassword());
        loginButton.click();
        return new VerificationPage();
    }

    public void invalidLogin(DataHelper.AuthInfo authInfo) {
        loginInput.setValue(authInfo.getLogin());
        passwordInput.setValue(authInfo.getPassword());
        loginButton.click();
        error.shouldBe(visible);
    }

    public void shouldShowError() {
        error.shouldBe(visible);
    }
}
