package data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final QueryRunner runner = new QueryRunner();

    private SQLHelper() {

    }

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    @SneakyThrows
    public static void cleanDatabase() { //метод для удаления всех записей из БД после тестов
        var connection = getConn();
        runner.execute(connection, "DELETE FROM credit_request_entity"); //таблица хранит данные о покупке в кредит
        runner.execute(connection, "DELETE FROM order_entity");          //таблица хранит данные о всех покупках
        runner.execute(connection, "DELETE FROM payment_entity");       //таблица хранит данные о покупке дебетовой картой
    }

    @SneakyThrows
    public static boolean buyDBCheck(String result) { //метод для проверки записи в БД покупки дебетовой картой и их соответствия
        var connection = getConn();
        String fact = String.valueOf(runner.execute(connection, "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1"));
        if (fact.equals(result)) {
            return true;
        }
        else {
            return false;
        }
    }

    @SneakyThrows
    public static boolean creditDBCheck(String result) {//метод для проверки записи в БД покупки в кредит
        var connection = getConn();
        String fact = String.valueOf(runner.execute(connection, "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1"));
        if (fact.equals(result)) {
            return true;
        }
        else {
            return false;
        }
    }
}
