import data.DataHelper;
import data.SQLHelper;
import objects.BuyTour;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static data.SQLHelper.cleanDatabase;

public class ShopTests {

    @AfterAll
    static void tearDown() throws SQLException {
        cleanDatabase();
    }

    @Test
    public void shouldBuyActiveUser() {
        BuyTour shopPage = open("http://localhost:8080/", BuyTour.class);
        shopPage.buy();
        shopPage.inputCard(DataHelper.correctActiveUser());
        shopPage.confirm();
        shopPage.successMessageAppear();
        SQLHelper.buyDBCheck("APPROVED");
    }

    @Test
    public void shouldNotBuyBlockedUser() {
        BuyTour shopPage = open("http://localhost:8080/", BuyTour.class);
        shopPage.buy();
        shopPage.inputCard(DataHelper.inactiveUser());
        shopPage.confirm();
        shopPage.errorMessageAppear();
        SQLHelper.buyDBCheck("DECLINED");
    }

    @Test
    public void shouldBuyCreditCorrectUser() {
        BuyTour shopPage = open("http://localhost:8080/", BuyTour.class);
        shopPage.creditBuy();
        shopPage.inputCard(DataHelper.correctActiveUser());
        shopPage.confirm();
        shopPage.successMessageAppear();
        SQLHelper.creditDBCheck("APPROVED");
    }

    @Test
    public void shouldNotBuyCreditBlockedUser() {
        BuyTour shopPage = open("http://localhost:8080/", BuyTour.class);
        shopPage.creditBuy();
        shopPage.inputCard(DataHelper.inactiveUser());
        shopPage.confirm();
        shopPage.errorMessageAppear();
        SQLHelper.creditDBCheck("DECLINED");
    }

    @Test
    public void shouldNotBuyUnregisteredUser() {
        BuyTour shopPage = open("http://localhost:8080/", BuyTour.class);
        shopPage.buy();
        shopPage.inputCard(DataHelper.unregisteredUser());
        shopPage.confirm();
        shopPage.errorMessageAppear();
    }

    @Test
    public void shouldNotBuyCreditUnregisteredUser() {
        BuyTour shopPage = open("http://localhost:8080/", BuyTour.class);
        shopPage.creditBuy();
        shopPage.inputCard(DataHelper.unregisteredUser());
        shopPage.confirm();
        shopPage.errorMessageAppear();
    }

    @Test
    public void shouldNotBuyCorrectUserWithWrongMonth() {
        BuyTour shopPage = open("http://localhost:8080/", BuyTour.class);
        shopPage.buy();
        shopPage.inputCard(DataHelper.incorrectActiveUser("00", "24", "IVANOV IVAN", "175"));
        shopPage.confirm();
        shopPage.monthError();
    }

    @Test
    public void shouldNotBuyCreditCorrectUserWithWrongMonth() {
        BuyTour shopPage = open("http://localhost:8080/", BuyTour.class);
        shopPage.creditBuy();
        shopPage.inputCard(DataHelper.incorrectActiveUser("13", "24", "IVANOV IVAN", "175"));
        shopPage.confirm();
        shopPage.monthError();
    }

    @Test
    public void shouldNotBuyCorrectUserWithWrongYear() {
        BuyTour shopPage = open("http://localhost:8080/", BuyTour.class);
        shopPage.buy();
        shopPage.inputCard(DataHelper.incorrectActiveUser("08", "22", "IVANOV IVAN", "175"));
        shopPage.confirm();
        shopPage.yearError();
    }

    @Test
    public void shouldNotBuyCreditCorrectUserWithWrongYear() {
        BuyTour shopPage = open("http://localhost:8080/", BuyTour.class);
        shopPage.creditBuy();
        shopPage.inputCard(DataHelper.incorrectActiveUser("08", "22", "IVANOV IVAN", "175"));
        shopPage.confirm();
        shopPage.yearError();
    }

    @Test
    public void shouldNotBuyCorrectUserWithWrongCardHolder() {
        BuyTour shopPage = open("http://localhost:8080/", BuyTour.class);
        shopPage.buy();
        shopPage.inputCard(DataHelper.incorrectActiveUser("08", "24", "ИВАНОВ ИВАН", "175"));
        shopPage.confirm();
        shopPage.holderFieldMandatoryError();
    }

    @Test
    public void shouldNotBuyCreditCorrectUserWithWrongCardHolder() {
        BuyTour shopPage = open("http://localhost:8080/", BuyTour.class);
        shopPage.creditBuy();
        shopPage.inputCard(DataHelper.incorrectActiveUser("08", "24", "ИВАНОВ ИВАН", "175"));
        shopPage.confirm();
        shopPage.holderFieldMandatoryError();
    }

    @Test
    public void shouldNotBuyCorrectUserWithWrongCode() {
        BuyTour shopPage = open("http://localhost:8080/", BuyTour.class);
        shopPage.buy();
        shopPage.inputCard(DataHelper.incorrectActiveUser("08", "24", "IVANOV IVAN", " "));
        shopPage.confirm();
        shopPage.cvcError();
    }

    @Test
    public void shouldNotBuyCreditCorrectUserWithWrongCode() {
        BuyTour shopPage = open("http://localhost:8080/", BuyTour.class);
        shopPage.creditBuy();
        shopPage.inputCard(DataHelper.incorrectActiveUser("08", "24", "IVANOV IVAN", " "));
        shopPage.confirm();
        shopPage.cvcError();
    }
}
