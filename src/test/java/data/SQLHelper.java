package data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SQLHelper {
    private static final QueryRunner runner = new QueryRunner();
    private static Properties properties;

    static {
        try {
            properties = new Properties();
            properties.load(new FileInputStream("gradle.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private SQLHelper() {
    }
    private static Connection getConn() throws SQLException {
        String dbType = System.getProperty("db.type");
        if (dbType.equals("postgresql")) {
            String dbUrl = "jdbc:postgresql://" + properties.getProperty("db.host") + ":" + properties.getProperty("db.port") + "/" + properties.getProperty("db.name");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");
            return DriverManager.getConnection(dbUrl, user, password);
        } else if (dbType.equals("mysql")) {
            String mySqlDbUrl = "jdbc:mysql://" + properties.getProperty("mysql.host") + ":" + properties.getProperty("mysql.port") + "/" + properties.getProperty("mysql.database");
            String mySqlUser = properties.getProperty("mysql.user");
            String mySqlPass = properties.getProperty("mysql.password");
            return DriverManager.getConnection(mySqlDbUrl, mySqlUser, mySqlPass);
        }
        throw new IllegalArgumentException("Unknown database type: " + dbType);
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
