package data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final QueryRunner runner = new QueryRunner();
    private static String dbName = System.getProperty("mysqlDatabase");
    private static String user = System.getProperty("mysqlUser");
    private static String pass = System.getProperty("mysqlPassword");

    private SQLHelper() {

    }

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, user, pass);
    }

    @SneakyThrows
    private static void closeConn(Connection connection) {
        if (connection != null) {
            connection.close();
        }
    }

    @SneakyThrows
    public static void cleanDatabase() {
        var connection = getConn();
        try {
            runner.execute(connection, "DELETE FROM credit_request_entity");
            runner.execute(connection, "DELETE FROM order_entity");
            runner.execute(connection, "DELETE FROM payment_entity");
        } finally {
            closeConn(connection);
        }
    }

    @SneakyThrows
    public static boolean buyDBCheck(String result) {
        var connection = getConn();
        try {
            String fact = String.valueOf(runner.execute(connection, "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1"));
            return fact.equals(result);
        } finally {
            closeConn(connection);
        }
    }

    @SneakyThrows
    public static boolean creditDBCheck(String result) {
        var connection = getConn();
        try {
            String fact = String.valueOf(runner.execute(connection, "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1"));
            return fact.equals(result);
        } finally {
            closeConn(connection);
        }
    }
}
