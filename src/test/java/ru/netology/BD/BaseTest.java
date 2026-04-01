package ru.netology.BD;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import ru.netology.BD.db.DbHelper;

import static com.codeborne.selenide.Selenide.open;

public class BaseTest {

    @BeforeEach
    void setup() {
        Configuration.baseUrl = "http://localhost:9999";
        open("/");
    }

    @AfterAll
    static void tearDown() {
        DbHelper.cleanDatabase();
    }
}