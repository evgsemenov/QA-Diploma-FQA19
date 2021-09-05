package pageobject;

import com.codeborne.selenide.SelenideElement;
import lombok.Data;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.Keys.CONTROL;
import static org.openqa.selenium.Keys.DELETE;

@Data
public class PaymentPage {
    private SelenideElement header = $("[class = 'heading heading_size_m heading_theme_alfa-on-white']");
    private SelenideElement creditButton = $(byText("Купить в кредит"));
    private SelenideElement cardNumberField = $(byText("Номер карты")).parent().$(".input__control");
    private SelenideElement monthField = $(byText("Месяц")).parent().$(".input__control");
    private SelenideElement yearField = $(byText("Год")).parent().$(".input__control");
    private SelenideElement cardHolderField = $(byText("Владелец")).parent().$(".input__control");
    private SelenideElement cvvNumberField = $(byText("CVC/CVV")).parent().$(".input__control");
    private SelenideElement nextButton = $(byText("Продолжить"));
    private SelenideElement successNotification = $(".notification_status_ok");
    private SelenideElement errorNotification = $(".notification_status_error");
    private SelenideElement inputInvalid = $(".input__sub");

    public PaymentPage() {
        header.shouldBe(visible).shouldHave(exactText("Оплата по карте"));
    }

    public CreditPage buyWithCredit() {
        creditButton.click();
        return new CreditPage();
    }

    private void clearForm() {
        cardNumberField.sendKeys(CONTROL + "A", DELETE);
        monthField.sendKeys(CONTROL + "A", DELETE);
        yearField.sendKeys(CONTROL + "A", DELETE);
        cardHolderField.sendKeys(CONTROL + "A", DELETE);
        cvvNumberField.sendKeys(CONTROL + "A", DELETE);
    }

    public void fillPaymentInfo (String card, String month, String year, String name, String cvv) {
        clearForm();
        cardNumberField.setValue(card);
        monthField.setValue(month);
        yearField.setValue(year);
        cardHolderField.setValue(name);
        cvvNumberField.setValue(cvv);
    }

    public void successfulSendingForm (String card, String month, String year, String name, String cvv) {
        fillPaymentInfo(card, month, year, name, cvv);
        nextButton.click();
        successNotification.shouldBe(visible, Duration.ofSeconds(15)).
                shouldHave(exactText("Успешно\n" + "Операция одобрена Банком."));
    }

    public void unsuccessfulSendingForm (String card, String month, String year, String name, String cvv) {
        fillPaymentInfo(card, month, year, name, cvv);
        nextButton.click();
        errorNotification.shouldBe(visible, Duration.ofSeconds(15)).
                shouldHave(exactText("Ошибка\n" + "Ошибка! Банк отказал в проведении операции."));
    }

    public void sendClearForm() {
        clearForm();
        nextButton.click();
        inputInvalid.shouldBe(visible);
    }

    public void sendInvalidForm() {
        nextButton.click();
        inputInvalid.shouldBe(visible);
    }
}
