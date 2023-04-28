package data;

import lombok.Value;

public class DataHelper {

    @Value
    public static class UserInfo {
        private String cardNumber;
        private String cardMonth;
        private String cardYear;
        private String cardHolder;
        private String cardCvc;
    }

    public static UserInfo correctActiveUser() {
        return new UserInfo("4444444444444441", "08", "24", "IVANOV IVAN", "175");
    }

    public static UserInfo incorrectActiveUser(String month, String year, String cardHolder, String cardCvc) {
        return new UserInfo("4444444444444441", month, year, cardHolder, cardCvc);
    }

    public static UserInfo inactiveUser() {
        return new UserInfo("4444444444444442", "08", "24", "IVANOV IVAN", "175");
    }

    public static UserInfo incorrectInactiveUser(String month, String year, String cardHolder, String cardCvc) {
        return new UserInfo("4444444444444442", month, year, cardHolder, cardCvc);
    }

    public static UserInfo unregisteredUser() {
        return new UserInfo("1234 5678 9109 8765", "08", "24", "IVANOV IVAN", "175");
    }
}
