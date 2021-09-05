package test_suit;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import pageobject.TourPurchasePage;

import static com.codeborne.selenide.Selenide.closeWindow;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UI_Test {
    TourPurchasePage tourPurchasePage;

    @BeforeAll
    static void allureSetup() {
        SelenideLogger.addListener("allure", new AllureSelenide().
                screenshots(true).savePageSource(false));
    }

    @BeforeEach
    void browserSetUp(){
        open("http://localhost:8080/");
        tourPurchasePage = new TourPurchasePage();
    }

    @AfterEach
    void tearDown() {
        closeWindow();
    }

    @AfterAll
    static void tearDownAllure() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Должен отправить заполненную форму и одобрить операцию по действующей карте со страницы оплаты")
    void shouldSuccessBuyTourWithApprovedCardOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        var approvedPayment = DataHelper.getApprovedPayment(DataHelper.randomPlusMonth());
        paymentPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    @DisplayName("Должен отправить заполненную форму и одобрить операцию по действующей карте со страницы оформления кредита")
    void shouldSuccessBuyTourWithApprovedCardOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        var approvedPayment = DataHelper.getApprovedPayment(DataHelper.randomPlusMonth());
        creditPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    @DisplayName("Должен отправить заполненную форму и отклонить операцию по заблокированной карте со страницы оплаты")
    void shouldGetErrorIfBuyTourWithDeclinedCardOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        var declinedPayment = DataHelper.getDeclinePayment(DataHelper.randomPlusMonth());
        paymentPage.unsuccessfulSendingForm(declinedPayment.getCardNumber(), declinedPayment.getMonth(),
                declinedPayment.getYear(), declinedPayment.getCardHolder(), declinedPayment.getCvv());
    }

    @Test
    @DisplayName("Должен отправить заполненную форму и отклонить операцию по заблокированной карте со страницы оформления кредита")
    void shouldGetErrorIfBuyTourWithDeclinedCardOnCreditPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        var declinedPayment = DataHelper.getDeclinePayment(DataHelper.randomPlusMonth());
        paymentPage.unsuccessfulSendingForm(declinedPayment.getCardNumber(), declinedPayment.getMonth(),
                declinedPayment.getYear(), declinedPayment.getCardHolder(), declinedPayment.getCvv());
    }

    @Test
    @DisplayName("Должен успешно переключиться со страницы оплаты на страницу оформления кредита")
    void shouldSwitchFromPaymentPageToCreditPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        var creditPage = paymentPage.buyWithCredit();
    }

    @Test
    @DisplayName("Должен успешно переключиться со страницы оформления кредита на страницу оплаты")
    void shouldSwitchFromCreditPageToPaymentPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        var paymentPage = creditPage.payForTour();
    }

    @Test
    @DisplayName("Должен показывать ошибку при отправке пустой формы со страницы оплаты")
    void shouldRequireFillPaymentFormFieldsTest() {
        var paymentPage = tourPurchasePage.payForTour();
        paymentPage.sendClearForm();
    }

    @Test
    @DisplayName("Должен показывать ошибку при отправке пустой формы со страницы оформления кредита")
    void shouldRequireFillCreditFormFieldsTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        creditPage.sendClearForm();
    }

    @Test
    @DisplayName("Должен показывать ошибку при отправке пустого поля 'Номер карты' на странице оплаты")
    void shouldRequireCardNumberOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        var approvedPayment = DataHelper.getApprovedPayment(DataHelper.randomPlusMonth());
        paymentPage.fillPaymentInfo("", approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.sendInvalidForm();
    }

    @Test
    @DisplayName("Должен показывать ошибку при отправке пустого поля 'Месяц' на странице оплаты")
    void shouldRequireMonthOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        var approvedPayment = DataHelper.getApprovedPayment(DataHelper.randomPlusMonth());
        paymentPage.fillPaymentInfo(approvedPayment.getCardNumber(), "",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.sendInvalidForm();
    }

    @Test
    @DisplayName("Должен показывать ошибку при отправке пустого поля 'Год' на странице оплаты")
    void shouldRequireYearOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        var approvedPayment = DataHelper.getApprovedPayment(DataHelper.randomPlusMonth());
        paymentPage.fillPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                "", approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.sendInvalidForm();
    }

    @Test
    @DisplayName("Должен показывать ошибку при отправке пустого поля 'Владелец' на странице оплаты")
    void shouldRequireCardHolderOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        var approvedPayment = DataHelper.getApprovedPayment(DataHelper.randomPlusMonth());
        paymentPage.fillPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), "", approvedPayment.getCvv());
        paymentPage.sendInvalidForm();
    }

    @Test
    @DisplayName("Должен показывать ошибку при отправке пустого поля 'CVC/CVV' на странице оплаты")
    void shouldRequireCvvOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        var approvedPayment = DataHelper.getApprovedPayment(DataHelper.randomPlusMonth());
        paymentPage.fillPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), "");
        paymentPage.sendInvalidForm();
    }

    @Test
    @DisplayName("Должен показывать ошибку при отправке пустого поля 'Номер карты' на странице оформления кредита")
    void shouldRequireCardNumberOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        var approvedPayment = DataHelper.getApprovedPayment(DataHelper.randomPlusMonth());
        creditPage.fillPaymentInfo("", approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.sendInvalidForm();
    }

    @Test
    @DisplayName("Должен показывать ошибку при отправке пустого поля 'Месяц' на странице оформления кредита")
    void shouldRequireMonthOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        var approvedPayment = DataHelper.getApprovedPayment(DataHelper.randomPlusMonth());
        creditPage.fillPaymentInfo(approvedPayment.getCardNumber(), "",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.sendInvalidForm();
    }

    @Test
    @DisplayName("Должен показывать ошибку при отправке пустого поля 'Год' на странице оформления кредита")
    void shouldRequireYearOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        var approvedPayment = DataHelper.getApprovedPayment(DataHelper.randomPlusMonth());
        creditPage.fillPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                "", approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.sendInvalidForm();
    }

    @Test
    @DisplayName("Должен показывать ошибку при отправке пустого поля 'Владелец' на странице оформления кредита")
    void shouldRequireCardHolderOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        var approvedPayment = DataHelper.getApprovedPayment(DataHelper.randomPlusMonth());
        creditPage.fillPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), "", approvedPayment.getCvv());
        creditPage.sendInvalidForm();
    }

    @Test
    @DisplayName("Должен показывать ошибку при отправке пустого поля 'CVC/CVV' на странице оформления кредита")
    void shouldRequireCvvOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        var approvedPayment = DataHelper.getApprovedPayment(DataHelper.randomPlusMonth());
        creditPage.fillPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), "");
        creditPage.sendInvalidForm();
    }

    @Test
    @DisplayName("Не должен разрешать ввод латиницы в поле 'Номер карты' на странице оплаты")
    void shouldNotAllowLatinSymbolsInCardNumberFieldOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        paymentPage.getCardNumberField().setValue(DataHelper.getRandomLatinSymbols());
        assertEquals("", paymentPage.getCardNumberField().getValue());
    }

    @Test
    @DisplayName("Не должен разрешать ввод кириллицы в поле 'Номер карты' на странице оплаты")
    void shouldNotAllowCyrillicSymbolsInCardNumberFieldOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        paymentPage.getCardNumberField().setValue(DataHelper.getRandomCyrillicSymbols());
        assertEquals("", paymentPage.getCardNumberField().getValue());
    }

    @Test
    @DisplayName("Не должен разрешать ввод спецсимволов в поле 'Номер карты' на странице оплаты")
    void shouldNotAllowSpecialCharactersInCardNumberFieldOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        creditPage.getCardNumberField().setValue("!@#$%^&*()_+-~|");
        assertEquals("", creditPage.getCardNumberField().getValue());
    }

    @Test
    @DisplayName("Не должен разрешать ввод латиницы в поле 'Номер карты' на странице оформления кредита")
    void shouldNotAllowLatinSymbolsInCardNumberFieldOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        creditPage.getCardNumberField().setValue(DataHelper.getRandomLatinSymbols());
        assertEquals("", creditPage.getCardNumberField().getValue());
    }

    @Test
    @DisplayName("Не должен разрешать ввод кириллицы в поле 'Номер карты' на странице оформления кредита")
    void shouldNotAllowCyrillicSymbolsInCardNumberFieldOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        creditPage.getCardNumberField().setValue(DataHelper.getRandomCyrillicSymbols());
        assertEquals("", creditPage.getCardNumberField().getValue());
    }

    @Test
    @DisplayName("Не должен разрешать ввод спецсимволов в поле 'Номер карты' на странице оформления кредита")
    void shouldNotAllowSpecialCharactersInCardNumberFieldOnPaymentPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        creditPage.getCardNumberField().setValue("!@#$%^&*()_+-~|");
        assertEquals("", creditPage.getCardNumberField().getValue());
    }

    @Test
    @DisplayName("Не должен разрешать ввод латиницы в поле 'Месяц' на странице оплаты")
    void shouldNotAllowLatinSymbolsInMonthFieldOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        paymentPage.getMonthField().setValue(DataHelper.getRandomLatinSymbols());
        assertEquals("", paymentPage.getMonthField().getValue());
    }

    @Test
    @DisplayName("Не должен разрешать ввод кириллицы в поле 'Месяц' на странице оплаты")
    void shouldNotAllowCyrillicSymbolsInMonthFieldOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        paymentPage.getMonthField().setValue(DataHelper.getRandomCyrillicSymbols());
        assertEquals("", paymentPage.getMonthField().getValue());
    }

    @Test
    @DisplayName("Не должен разрешать ввод спецсимволов в поле 'Месяц' на странице оплаты")
    void shouldNotAllowSpecialNumbersInMonthFieldOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        paymentPage.getMonthField().setValue("!@#$%^&*()_+-~|");
        assertEquals("", paymentPage.getMonthField().getValue());
    }

    @Test
    @DisplayName("Не должен разрешать ввод латиницы в поле 'Месяц' на странице оформления кредита")
    void shouldNotAllowLatinSymbolsInMonthFieldOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        creditPage.getMonthField().setValue(DataHelper.getRandomLatinSymbols());
        assertEquals("", creditPage.getMonthField().getValue());
    }

    @Test
    @DisplayName("Не должен разрешать ввод кириллицы в поле 'Месяц' на странице оформления кредита")
    void shouldNotAllowCyrillicSymbolsInMonthFieldOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        creditPage.getMonthField().setValue(DataHelper.getRandomCyrillicSymbols());
        assertEquals("", creditPage.getMonthField().getValue());
    }

    @Test
    @DisplayName("Не должен разрешать ввод спецсимволов в поле 'Месяц' на странице оформления кредита")
    void shouldNotAllowSpecialNumbersInMonthFieldOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        creditPage.getMonthField().setValue("!@#$%^&*()_+-~|");
        assertEquals("", creditPage.getMonthField().getValue());
    }

    @Test
    @DisplayName("Не должен разрешать ввод кириллицы в поле 'Год' на странице оплаты")
    void shouldNotAllowCyrillicSymbolsInYearFieldOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        paymentPage.getYearField().setValue(DataHelper.getRandomCyrillicSymbols());
        assertEquals("", paymentPage.getYearField().getValue());
    }

    @Test
    @DisplayName("Не должен разрешать ввод латиницы в поле 'Год' на странице оплаты")
    void shouldNotAllowLatinZOSymbolsInYearFieldOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        paymentPage.getYearField().setValue(DataHelper.getRandomLatinSymbols());
        assertEquals("", paymentPage.getYearField().getValue());
    }

    @Test
    @DisplayName("Не должен разрешать ввод спецсимволов в поле 'Год' на странице оплаты")
    void shouldNotAllowSpecialCharactersInYearFieldOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        paymentPage.getYearField().setValue("!@#$%^&*()_+-~|");
        assertEquals("", paymentPage.getYearField().getValue());
    }

    @Test
    @DisplayName("Не должен разрешать ввод кириллицы в поле 'Год' на странице оформления кредита")
    void shouldNotAllowCyrillicSymbolsInYearFieldOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        creditPage.getYearField().setValue(DataHelper.getRandomCyrillicSymbols());
        assertEquals("", creditPage.getYearField().getValue());
    }

    @Test
    @DisplayName("Не должен разрешать ввод латиницы в поле 'Год' на странице оформления кредита")
    void shouldNotAllowLatinZOSymbolsInYearFieldOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        creditPage.getYearField().setValue(DataHelper.getRandomLatinSymbols());
        assertEquals("", creditPage.getYearField().getValue());
    }

    @Test
    @DisplayName("Не должен разрешать ввод спецсимволов в поле 'Год' на странице оформления кредита")
    void shouldNotAllowSpecialCharactersInYearFieldOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        creditPage.getYearField().setValue("!@#$%^&*()_+-~|");
        assertEquals("", creditPage.getYearField().getValue());
    }

    @Test
    @DisplayName("Не должен разрешать ввод цифр в поле 'Владелец' на странице оплаты")
    void shouldNotAllowNumbersInCardHolderFieldOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        paymentPage.getCardHolderField().setValue("0123456789");
        assertEquals("", paymentPage.getCardHolderField().getValue());
    }

    @Test
    @DisplayName("Не должен разрешать ввод спецсимволов в поле 'Владелец' на странице оплаты")
    void shouldNotAllowSpecialNumbersInCardHolderFieldOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        paymentPage.getCardHolderField().setValue("!@#$%^&*()_+-~|");
        assertEquals("", paymentPage.getCardHolderField().getValue());
    }

    @Test
    @DisplayName("Не должен разрешать ввод цифр в поле 'Владелец' на странице оформления кредита")
    void shouldNotAllowNumbersInCardHolderFieldOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        creditPage.getCardHolderField().setValue("0123456789");
        assertEquals("", creditPage.getCardHolderField().getValue());
    }

    @Test
    @DisplayName("Не должен разрешать ввод спецсимволов в поле 'Владелец' на странице оформления кредита")
    void shouldNotAllowSpecialNumbersInCardHolderFieldOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        creditPage.getCardHolderField().setValue("!@#$%^&*()_+-~|");
        assertEquals("", creditPage.getCardHolderField().getValue());
    }

    @Test
    @DisplayName("Не должен разрешать ввод кириллицы в поле 'CVC/CVV' на странице оплаты")
    void shouldNotAllowCyrillicSymbolsInCvvNumberFieldOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        paymentPage.getCvvNumberField().setValue(DataHelper.getRandomCyrillicSymbols());
        assertEquals("", paymentPage.getCvvNumberField().getValue());
    }

    @Test
    @DisplayName("Не должен разрешать ввод латиницы в поле 'CVC/CVV' на странице оплаты")
    void shouldNotAllowLatinSymbolsInCvvNumberFieldOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        paymentPage.getCvvNumberField().setValue(DataHelper.getRandomLatinSymbols());
        assertEquals("", paymentPage.getCvvNumberField().getValue());
    }

    @Test
    @DisplayName("Не должен разрешать ввод спецсимволов в поле 'CVC/CVV' на странице оплаты")
    void shouldNotAllowSpecialNumbersInCvvNumberFieldOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        paymentPage.getCvvNumberField().setValue("!@#$%^&*()_+-~|");
        assertEquals("", paymentPage.getCvvNumberField().getValue());
    }

    @Test
    @DisplayName("Не должен разрешать ввод кириллицы в поле 'CVC/CVV' на странице оформления кредита")
    void shouldNotAllowCyrillicSymbolsInCvvNumberFieldOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        creditPage.getCvvNumberField().setValue(DataHelper.getRandomCyrillicSymbols());
        assertEquals("", creditPage.getCvvNumberField().getValue());
    }

    @Test
    @DisplayName("Не должен разрешать ввод латиницы в поле 'CVC/CVV' на странице оформления кредита")
    void shouldNotAllowLatinSymbolsInCvvNumberFieldOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        creditPage.getCvvNumberField().setValue(DataHelper.getRandomLatinSymbols());
        assertEquals("", creditPage.getCvvNumberField().getValue());
    }

    @Test
    @DisplayName("Не должен разрешать ввод спецсимволов в поле 'CVC/CVV' на странице оформления кредита")
    void shouldNotAllowSpecialNumbersInCvvNumberFieldOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        creditPage.getCvvNumberField().setValue("!@#$%^&*()_+-~|");
        assertEquals("", creditPage.getCvvNumberField().getValue());
    }
}

