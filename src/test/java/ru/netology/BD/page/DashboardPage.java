package ru.netology.BD.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;

public class DashboardPage {

    private SelenideElement heading = $$("h2").findBy(text("Личный кабинет"));

    public void shouldBeVisible() {
        heading.shouldBe(visible);
    }
}