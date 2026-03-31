package ru.netology.BD;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Selenide.open;

public class BaseTest {

    @BeforeAll
    static void setupAll() {
        Configuration.baseUrl = "http://localhost:9999";
        Configuration.headless = false;
    }

    @BeforeEach
    void setup() {
        open("/");
    }
}