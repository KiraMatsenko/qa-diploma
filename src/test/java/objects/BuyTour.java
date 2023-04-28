package objects;

import com.codeborne.selenide.SelenideElement;
import data.DataHelper;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class BuyTour {

    private SelenideElement header = $(By.xpath("//h2[text()='Путешествие дня']"));
    private SelenideElement buyButton = $x("//span[text()='Купить']");
    private SelenideElement creditButton = $x("//span[text()='Купить в кредит']");
    private SelenideElement cardFields = $x("//h3[text()='Оплата по карте']");
    private SelenideElement creditBuyFields = $x("//h3[text()='Кредит по данным карты']");
    //    private SelenideElement continueButton = $x("//span[text()='Продолжить']");
    private SelenideElement continueButton = $$("[class='button__content']").findBy(text("Продолжить"));
    private SelenideElement paymentSuccessMessage = $x("//div[text()='Успешно']");
    private SelenideElement paymentFailureMessage = $x("//div[text()='Ошибка']");
    private SelenideElement cardNumberIncorrectMessage = $x("//span[text()='Номер карты']/following::span[2]");
    private SelenideElement monthIncorrectMessage = $x("//span[text()='Месяц']/following::span[2]");
    private SelenideElement yearIncorrectMessage = $x("//span[text()='Год']/following::span[2]");
    private SelenideElement cvcIncorrectMessage = $x("//span[text()='CVC/CVV']/following::span[2]");
    private SelenideElement holderFieldMandatory = $x("//span[text()='Владелец']/following::span[2]");
    private SelenideElement cardNumber = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement cardMonth = $$(".input__control").findBy(attribute("placeholder", "08"));

    private SelenideElement cardYear = $$(".input__control").findBy(attribute("placeholder", "22"));

    private SelenideElement cardHolder = $x("//span[@class='input__top' and text()='Владелец']/parent::span/child::span/input");
    private SelenideElement cardCvv = $$(".input__control").findBy(attribute("placeholder", "999"));

    private void checkPage() {                          //проверяем, что страница открылась
        header.shouldBe(visible);
    }

    private void cardFieldsShouldAppear() {             //проверяем, что после нажатия кнопки купить открылась форма карты
        cardFields.shouldBe(visible);
    }

    private void creditBuyFieldsShouldAppear() {   //проверяем, что после нажатия кнопки купить в кредит открылась форма карты
        creditBuyFields.shouldBe(visible);
    }

    public void buy() {                                 //нажимаем купить
        buyButton.click();
        cardFields.shouldBe(visible);
    }

    public void creditBuy() {                          //нажимаем купить в кредит
        creditButton.click();
        creditBuyFields.shouldBe(visible);
    }

    public void inputCard(DataHelper.UserInfo user) { //вставляем данные карты из дата-хелпера в поля
        cardNumber.setValue(user.getCardNumber());
        cardMonth.setValue(user.getCardMonth());
        cardYear.setValue(user.getCardYear());
        cardHolder.setValue(user.getCardHolder());
        cardCvv.setValue(user.getCardCvc());
        confirm();
    }

    public void confirm() {                          //нажимаем кнопку продолжить
        continueButton.click();
    }

    public void successMessageAppear() {            //проверяем, что появилось сообщение об успехе
        paymentSuccessMessage.shouldBe(visible);
    }

    public void errorMessageAppear() {              //проверяем, что появилось сообщение об ошибке
        paymentFailureMessage.shouldBe(visible);
    }

    public void cardNumberError() {
        cardNumberIncorrectMessage.shouldBe(visible);
    }

    public void monthError() {
        monthIncorrectMessage.shouldBe(visible);
    }

    public void yearError() {
        yearIncorrectMessage.shouldBe(visible);
    }

    public void cvcError() {
        cvcIncorrectMessage.shouldBe(visible);
    }

    public void holderFieldMandatoryError() {
        holderFieldMandatory.shouldBe(visible);
    }

}
