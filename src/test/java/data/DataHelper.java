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
        private String name;
        private String cvv;

    }

    public static PaymentInfo getApprovedPayment() {
        return new PaymentInfo(approvedCard, randomMonth(localDate.getMonthValue(),12), localYear(),
                getRandomName(), getRandomCVV());
    }

    public static String localYear() {
        DateFormat dateFormat = new SimpleDateFormat("yy");
        var year = dateFormat.format(localDate.getYear());
        return year;
    }


    public static String localMonth() {
        var month = localDate.getMonthValue();
        if (month < 10) {
            String monthFormat= "0" + Integer.toString(month);
            return monthFormat;
        }
        return Integer.toString(month);
    }

    public static String randomMonth(int min, int max) {
        Random random = new Random();
        var month = random.ints(min,(max+1)).findFirst().getAsInt();
        if (month < 10) {
            String monthFormat= "0" + Integer.toString(month);
            return monthFormat;
        }
        return Integer.toString(month);
    }

    public static String getRandomName(){
        String name = faker.name().lastName();
        return name;
    }

    public static String getRandomCVV() {
        String cvv = faker.numerify("###");
        return cvv;
    }

}