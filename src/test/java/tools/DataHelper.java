package tools;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;


public class DataHelper {
    private DataHelper() {
    }

    private static final String approvedCard = "4444 4444 4444 4441";
    private static final String declinedCard = "4444 4444 4444 4442";
    private static final Faker fakerEn = new Faker(new Locale("en-US"));

    @Value
    public static class PaymentInfo {
        private String cardNumber;
        private String month;
        private String year;
        private String cardHolder;
        private String cvv;
    }

    public static PaymentInfo approvedPayment(int plusMonth) {
        return new PaymentInfo(approvedCard, expiryMonth(plusMonth), expiryYear(plusMonth),
                getRandomName(), getRandomCVV());
    }

    public static PaymentInfo declinedPayment(int plusMonth) {
        return new PaymentInfo(declinedCard, expiryMonth(plusMonth), expiryYear(plusMonth),
                getRandomName(), getRandomCVV());
    }

    public static LocalDate expiryDate(int plusMonth) {
        var expiryDate = LocalDate.now().plusMonths(plusMonth);
        return expiryDate;
    }

    public static String expiryYear(int plusMonth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy");
        var year = expiryDate(plusMonth).format(formatter);
        return year;
    }

    public static String expiryMonth(int plusMonths) {
        var month = expiryDate(plusMonths).getMonthValue();
        if (month < 10) {
            String monthFormat= "0" + Integer.toString(month);
            return monthFormat;
        }
        return Integer.toString(month);
    }

    public static int randomPlusMonth() {
       Random random = new Random();
       int plusMonth = random.nextInt(58) + 1;
       return plusMonth;
    }

    public static String getRandomName(){
        String name = fakerEn.name().fullName();
        return name;
    }

    public static String getRandomCVV() {
        String cvv = fakerEn.numerify("###");
        return cvv;
    }

    public static String getRandomLatinSymbols() {
        String cyrillicSymbols = fakerEn.bothify("??????????????????");
        return cyrillicSymbols;
    }

    public static String getRandomCyrillicSymbols() {
        Faker fakerRu = new Faker(new Locale("ru"));
        String cyrillicSymbols = fakerRu.name().firstName();
        return cyrillicSymbols;
    }
}