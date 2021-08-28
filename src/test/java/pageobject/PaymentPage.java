package pageobject;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.Keys.CONTROL;
import static org.openqa.selenium.Keys.DELETE;

public class PaymentPage {

    private SelenideElement header = $("[class = 'heading heading_size_m heading_theme_alfa-on-white']");
    private SelenideElement creditButton = $(byText("Купить в кредит"));
    private SelenideElement cardNumberField = $(byText("Номер карты")).parent().$(".input__control");
    private SelenideElement monthField = $(byText("Месяц")).parent().$(".input__control");
    private SelenideElement yearField = $(byText("Год")).parent().$(".input__control");
    private SelenideElement nameField = $(byText("Владелец")).parent().$(".input__control");
    private SelenideElement cvvNumberField = $(byText("CVC/CVV")).parent().$(".input__control");
    private SelenideElement nextButton = $(byText("Продолжить"));


    public PaymentPage() {
        header.shouldBe(visible).shouldHave(exactText("Оплата по карте"));
    }

    public CreditPage buyWithCredit() {
        creditButton.click();
        return new CreditPage();
    }

    private void clearField() {
        cardNumberField.sendKeys(CONTROL + "A", DELETE);
        monthField.sendKeys(CONTROL + "A", DELETE);
        yearField.sendKeys(CONTROL + "A", DELETE);
        nameField.sendKeys(CONTROL + "A", DELETE);
        cvvNumberField.sendKeys(CONTROL + "A", DELETE);
    }

    private void fillPaymentInfo (String card, String month, String year, String name, String cvv) {
        clearField();
        cardNumberField.setValue(card);
        monthField.setValue(month);
        yearField.setValue(year);
        nameField.setValue(name);
        cvvNumberField.setValue(cvv);
    }

    public void SucessfullSendingForm (String card, String month, String year, String name, String cvv) {
        fillPaymentInfo(card, month, year, name, cvv);

    }

}
