package ru.netology.BD.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    private SelenideElement codeInput = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private SelenideElement error = $("[data-test-id=error-notification]");
    private SelenideElement errorText = error.$(".notification__content");


    public void verify(String code) {
        codeInput.setValue(code);
        verifyButton.click();
    }

    public void verifyErrorText(String expectedText) {
        errorText.shouldBe(visible).shouldHave(text(expectedText));
    }
}