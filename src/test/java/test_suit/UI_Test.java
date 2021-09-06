package test_suit;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import pageobject.TourPurchasePage;
import tools.DataHelper;

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
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    @DisplayName("Должен отправить заполненную форму и одобрить операцию по действующей карте со страницы оформления кредита")
    void shouldSuccessBuyTourWithApprovedCardOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    @DisplayName("Должен отправить заполненную форму и отклонить операцию по заблокированной карте со страницы оплаты")
    void shouldGetErrorIfBuyTourWithDeclinedCardOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        var declinedPayment = DataHelper.declinedPayment(DataHelper.randomPlusMonth());
        paymentPage.unsuccessfulSendingForm(declinedPayment.getCardNumber(), declinedPayment.getMonth(),
                declinedPayment.getYear(), declinedPayment.getCardHolder(), declinedPayment.getCvv());
    }

    @Test
    @DisplayName("Должен отправить заполненную форму и отклонить операцию по заблокированной карте со страницы оформления кредита")
    void shouldGetErrorIfBuyTourWithDeclinedCardOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        var declinedPayment = DataHelper.declinedPayment(DataHelper.randomPlusMonth());
        creditPage.unsuccessfulSendingForm(declinedPayment.getCardNumber(), declinedPayment.getMonth(),
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
    @DisplayName("Должен успешно отправить форму и одобрить операцию по активной карте со сроком истечения 59 месяцев")
    void shouldSuccessBuyTourWith59MonthExpiredCardOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(59);
        paymentPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }
    @Test
    @DisplayName("Должен успешно отправить форму и одобрить операцию по активной карте со сроком истечения 60 месяцев")
    void shouldSuccessBuyTourWith60MonthExpiredCardOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(60);
        paymentPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    @DisplayName("Должен показать ошибку при оформлении покупки по активной карте со сроком истечения более 5 лет")
    void shouldGetErrorIfBuyTourWith61MonthExpiredCardOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(61);
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен успешно отправить форму и одобрить операцию по активной карте со сроком истечения 1 месяц")
    void shouldSuccessBuyTourWithOneMonthExpiredCardOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(1);
        paymentPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    @DisplayName("Должен успешно отправить форму и одобрить операцию по активной карте со сроком истечения в этом месяце")
    void shouldSuccessBuyTourWithZeroMonthExpiredCardOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(0);
        paymentPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    @DisplayName("Должен показать ошибку при оформлении покупки по активной карте со сроком истечения более 5 лет")
    void shouldGetErrorIfBuyTourWithExpiredCardOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(-1);
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(), approvedPayment.getYear(),
                approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен успешно отправить форму и одобрить кредит по активной карте со сроком истечения 59 месяцев")
    void shouldSuccessBuyTourWith59MonthExpiredCardOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(59);
        creditPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    @DisplayName("Должен успешно отправить форму и одобрить кредит по активной карте со сроком истечения 60 месяцев")
    void shouldSuccessBuyTourWith60MonthExpiredCardOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(60);
        creditPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    @DisplayName("Должен показать ошибку при оформлении покупки по активной карте со сроком истечения более 5 лет")
    void shouldGetErrorIfBuyTourWith61MonthExpiredCardOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(61);
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(), approvedPayment.getYear(),
                approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен успешно отправить форму и одобрить кредит по активной карте со сроком истечения 1 месяц")
    void shouldSuccessBuyTourWithOneMonthExpiredCardOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(1);
        creditPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    @DisplayName("Должен успешно отправить форму и одобрить кредит по активной карте со сроком истечения в этом месяце")
    void shouldSuccessBuyTourWithZeroMonthExpiredCardOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(0);
        creditPage.successfulSendingForm(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    @DisplayName("Должен показать ошибку при оформлении кредита по активной карте со сроком истечения более 5 лет")
    void shouldGetErrorIfBuyTourWithExpiredCardOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(-1);
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(), approvedPayment.getYear(),
                approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен успешно отправить форму и одобрить операцию по активной карте с указанием 01 месяца")
    void shouldSuccessBuyTourWith01MonthValueOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(24);
        paymentPage.successfulSendingForm(approvedPayment.getCardNumber(), "01",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    @DisplayName("Должен показать ошибку при оформлении покупки по активной карте с указанием 00 месяца")
    void shouldGetErrorIfBuyTourWithZeroMonthValueOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(24);
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), "00",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен успешно отправить форму и одобрить кредит по активной карте с указанием 12 месяца")
    void shouldSuccessBuyTourWith12MonthValueOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(24);
        paymentPage.successfulSendingForm(approvedPayment.getCardNumber(), "12",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    @DisplayName("Должен показать ошибку при оформлении покупки по активной карте с указанием 13 месяца")
    void shouldGetErrorIfBuyTourWith13MonthValueOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(24);
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), "13",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен успешно отправить форму и одобрить кредит по активной карте с указанием 01 месяца")
    void shouldSuccessBuyTourWith01MonthValueOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(24);
        creditPage.successfulSendingForm(approvedPayment.getCardNumber(), "01",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    @DisplayName("Должен показать ошибку при оформлении кредита по активной карте с указанием 00 месяца")
    void shouldGetErrorIfBuyTourWithZeroMonthValueOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(24);
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), "00",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен успешно отправить форму и одобрить кредит по активной карте с указанием 12 месяца")
    void shouldSuccessBuyTourWith12MonthValueOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(24);
        creditPage.successfulSendingForm(approvedPayment.getCardNumber(), "12",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
    }

    @Test
    @DisplayName("Должен показать ошибку при оформлении кредита по активной карте с указанием 13 месяца")
    void shouldGetErrorIfBuyTourWith13MonthValueOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(24);
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), "13",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.inputInvalidError();
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
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.fillAndSendPaymentInfo("", approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показывать ошибку при отправке пустого поля 'Месяц' на странице оплаты")
    void shouldRequireMonthOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), "",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показывать ошибку при отправке пустого поля 'Год' на странице оплаты")
    void shouldRequireYearOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                "", approvedPayment.getCardHolder(), approvedPayment.getCvv());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показывать ошибку при отправке пустого поля 'Владелец' на странице оплаты")
    void shouldRequireCardHolderOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), "", approvedPayment.getCvv());
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показывать ошибку при отправке пустого поля 'CVC/CVV' на странице оплаты")
    void shouldRequireCvvOnPaymentPageTest() {
        var paymentPage = tourPurchasePage.payForTour();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        paymentPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), "");
        paymentPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показывать ошибку при отправке пустого поля 'Номер карты' на странице оформления кредита")
    void shouldRequireCardNumberOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.fillAndSendPaymentInfo("", approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показывать ошибку при отправке пустого поля 'Месяц' на странице оформления кредита")
    void shouldRequireMonthOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), "",
                approvedPayment.getYear(), approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показывать ошибку при отправке пустого поля 'Год' на странице оформления кредита")
    void shouldRequireYearOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                "", approvedPayment.getCardHolder(), approvedPayment.getCvv());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показывать ошибку при отправке пустого поля 'Владелец' на странице оформления кредита")
    void shouldRequireCardHolderOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), "", approvedPayment.getCvv());
        creditPage.inputInvalidError();
    }

    @Test
    @DisplayName("Должен показывать ошибку при отправке пустого поля 'CVC/CVV' на странице оформления кредита")
    void shouldRequireCvvOnCreditPageTest() {
        var creditPage = tourPurchasePage.buyWithCredit();
        var approvedPayment = DataHelper.approvedPayment(DataHelper.randomPlusMonth());
        creditPage.fillAndSendPaymentInfo(approvedPayment.getCardNumber(), approvedPayment.getMonth(),
                approvedPayment.getYear(), approvedPayment.getCardHolder(), "");
        creditPage.inputInvalidError();
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

