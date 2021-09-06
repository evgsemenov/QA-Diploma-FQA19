package tools;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseTool {
    public static String getCreditId() {
        var runner = new QueryRunner();
        var creditId = new String();
        var creditIdSQL = "SELECT credit_id FROM order_entity WHERE created = (SELECT max(created) FROM order_entity);";
        try (var conn = DriverManager.getConnection(System.getProperty("dbUrl"),
                System.getProperty("dbUser"), System.getProperty("dbPassword"));) {
            var creditIDResult = runner.query(conn, creditIdSQL, new ScalarHandler<>());
            creditId = String.valueOf(creditIDResult);
        } catch (SQLException e) {
            e.printStackTrace();
        }
            return creditId;
        }
    }




