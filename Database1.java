package mart;



import java.sql.*;

public class DataBase {
    private final String host = "localhost";//хост
    private final String port = "5432";//порт
    private final String db_name = "db_zacaz";//подключение к бд
    private final String login = "uliana_sakulina";//логин
    private final String password = " ";//пароль
    //jdbc-подключение к бд
    
    private Connection dbCon;

    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String str = "jdbc:postgresql://" + host + ":" + port + "/" + db_name;
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Соединение установлено");
        } catch (ClassNotFoundException e) {
            System.out.println("соединение не установлено ");
        }
        try {
            dbCon = DriverManager.getConnection(str, login, password);
        } catch (SQLException e) {
            System.out.println("неверный путь");
        }
        return dbCon;
    }

    public void isConnection() throws SQLException, ClassNotFoundException {
        dbCon = getDbConnection();
        System.out.println(dbCon.isValid(1000));
    }

    public void createTable(String tableName) {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " ( id INT PRIMARY KEY , name VARCHAR(50) , description VARCHAR (100)";
        try {
            Statement statement = getDbConnection().createStatement();
            statement.executeUpdate(sql);
            System.out.println("таблица" + tableName + "создана");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void addOrder(String table ,  int id, String name, String description) throws SQLException {
        String sql = "INSERT INTO orders (id, name, description) VALUES (?, ?, ?)";
        try (Connection conn = DataConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, description);
            int rows = preparedStatement.executeUpdate();
            System.out.printf(" добавлено %d заказов\n", rows);
        }catch(SQLException e) {
            System.out.println("не удалось");
    }
    }

    public void selectTable(String tableName) throws SQLException, ClassNotFoundException {
        Statement statement = getDbConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM  " + tableName);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            int description = resultSet.getString(3);
            System.out.printf("%d.%s. \n", id, name, description);
        }
    }
    public void getTotalOrders(String tableName) throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        Statement statement = getDBConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            int total = resultSet.getInt(1);
            System.out.printf("Общее количество заказов: %d\n", total);
        }
    }
}



    public static class Main {
        public static void main(String[] args) throws SQLException, ClassNotFoundException {
            DataBase db = new DataBase();
            db.isConnection();
            

            db.createTable(" orders ");
            System.out.println("данные :");

            db.addOrder("orders", 1, "Order1", "Description1");
        db.addOrder("orders", 2, "Order2", "Description2");
            
            db.selectTable(" orders");

            System.out.println("вывод");
            

        }
    }
}
