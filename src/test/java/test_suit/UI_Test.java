package test_suit;

import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.javafaker.Faker;
import data.DataHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import pageobject.TourPurchasePage;

import java.util.Locale;

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
    @DisplayName("Должен успешно отправить форму со страницы оплаты")
    void shouldSuccessBuyTourOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        var approvedPayment = DataHelper.getApprovedPayment(DataHelper.randomPlusMonth());
        paymentPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    @DisplayName("Должен успешно отправить форму со страницы оформления кредита")
    void shouldSuccessBuyTourOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        var approvedPayment = DataHelper.getApprovedPayment(DataHelper.randomPlusMonth());
        creditPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
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
        String expected = "1";
        assertEquals(expected, paymentPage.getCardNumberField().getText());
    }

    @Test
    @DisplayName("Не должен разрешать ввод кириллицы в поле 'Номер карты' на странице оплаты")
    void shouldNotAllowCyrillicSymbolsInCardNumberFieldOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        paymentPage.getCardNumberField().setValue(DataHelper.getRandomCyrillicSymbols());
        String expected = "";
        assertEquals(expected, paymentPage.getCardNumberField().getText());
    }

    @Test
    @DisplayName("Не должен разрешать ввод спецсимволов в поле 'Номер карты' на странице оплаты")
    void shouldNotAllowSpecialCharactersInCardNumberFieldOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        paymentPage.getCardNumberField().setValue("1!@#$%^&*()_+-~|");
        String expected = "";
        assertEquals(expected, paymentPage.getCardNumberField().getValue());
    }
}

