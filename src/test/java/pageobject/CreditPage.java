package pageobject;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class CreditPage {
    private SelenideElement header = $("[class = 'heading heading_size_l heading_theme_alfa-on-white']");
    private SelenideElement payButton = $(byText("Купить"));
    private SelenideElement cardNumber = $(byText("Номер карты")).parent().$(".input__control");
    private SelenideElement monthField = $(byText("Месяц")).parent().$(".input__control");
    private SelenideElement yearField = $(byText("Год")).parent().$(".input__control");
    private SelenideElement nameField = $(byText("Владелец")).parent().$(".input__control");
    private SelenideElement cvvNumber = $(byText("CVC/CVV")).parent().$(".input__control");
    private SelenideElement nextButton = $(byText("Продолжить"));


    public CreditPage() {
        header.shouldBe(visible).shouldHave(exactText("Кредит по данным карты"));
    }

    public PaymentPage payForTour() {
        payButton.click();
        return new PaymentPage();
    }
}
