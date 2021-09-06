package tools;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseTool {

    public static String getCreditId() {
        var creditIdSQL = "SELECT credit_id FROM order_entity WHERE created = (SELECT max(created) FROM order_entity);";
        return getValue(creditIdSQL);
    }

    public static String getCreditStatus() {
        var creditStatusSQL = "SELECT status FROM credit_request_entity WHERE created = (SELECT max(created) FROM credit_request_entity);";
        return getValue(creditStatusSQL);
    }

    public static String getPaymentStatus() {
        var paymentStatusSQL = "SELECT status FROM payment_entity WHERE created = (SELECT max(created) FROM payment_entity);";
        return getValue(paymentStatusSQL);
    }

    public static String getValue(String request) {
        var runner = new QueryRunner();
        var value = new String();
        try (var conn = DriverManager.getConnection(System.getProperty("dbUrl"),
                System.getProperty("dbUser"), System.getProperty("dbPassword"));) {
            var result = runner.query(conn, request, new ScalarHandler<>());
            value = String.valueOf(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value;
    }
}



