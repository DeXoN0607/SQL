package ru.netology.BD.data;

public class DataHelper {

    public static AuthInfo getValidUser() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getInvalidUser() {
        return new AuthInfo("vasya", "wrongpass");
    }

    public static VerificationCode getWrongCode() {
        return new VerificationCode("000000");
    }

    public static class AuthInfo {
        private final String login;
        private final String password;

        public AuthInfo(String login, String password) {
            this.login = login;
            this.password = password;
        }

        public String getLogin() { return login; }
        public String getPassword() { return password; }
    }

    public static class VerificationCode {
        private final String code;

        public VerificationCode(String code) {
            this.code = code;
        }

        public String getCode() { return code; }
    }
}