package data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Random;


public class DataHelper {
    private DataHelper() {
    }

    private static final String approvedCard = "4444 4444 4444 4441";
    private static final String declinedCard = "4444 4444 4444 4442";
    private static final Faker faker = new Faker();
    private static final LocalDate localDate = LocalDate.now();

    @Value
    public static class PaymentInfo {
        private String cardNumber;
        private String month;
        private String year;
        private String cardHolder;
        private String cvv;

    }

    public static PaymentInfo getApprovedPayment(int plusMonth) {
        return new PaymentInfo(approvedCard, expiryMonth(plusMonth), expiryYear(plusMonth),
                getRandomName(), getRandomCVV());
    }

    public static LocalDate expiryDate(int plusMonth) {
        var expiryDate = localDate.now().plusMonths(plusMonth);
        return expiryDate;
    }


    public static String expiryYear(int plusMonth) {
        DateFormat dateFormat = new SimpleDateFormat("yy");
        var year = dateFormat.format(expiryDate(plusMonth).getYear());
        // TODO Всегда возвращает 70  !!???
        return year;
    }


    public static String expiryMonth(int plusMonths) {
        DateFormat dateFormat = new SimpleDateFormat("MM");
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
        String name = faker.name().fullName();
        return name;
    }

    public static String getRandomCVV() {
        String cvv = faker.numerify("###");
        return cvv;
    }

}