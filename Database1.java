package mart;



import java.sql.*;

public class Data {
    private final String host = "localhost";//хост
    private final String port = "5432";//порт
    private final String db_name = "db_zacaz";//подключение к бд
    private final String login = "uliana_sakulina";//логин
    private final String password = " ";//пароль
    //jdbc-подключение к бд
    //zad-задание
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
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (customers_name INT PRIMARY KEY , product_name VARCHAR(100) , price int )";
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

    public void addData(String customerName, String productName, int price) throws SQLException {
        String sql = "INSERT INTO orders (customer_name, product_name,price) VALUES (?, ?, ?)";
        try (Connection conn = DataConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customerName);
            pstmt.setString(2, productName);
            pstmt.setInt(3, price);
            pstmt.executeUpdate();
        }
    }

    public void selectTable(String tableName) throws SQLException, ClassNotFoundException {
        Statement statement = getDbConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM  " + tableName);
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            int price = resultSet.getInt(3);
            System.out.printf("%d.%s. \n", id, name, price);
        }
    }


    public static class Main {
        public static void main(String[] args) throws SQLException, ClassNotFoundException {
            Data db = new Data();
            db.isConnection();

            db.createTable(" orders ");
            System.out.println("данные :");
            db.selectTable(" orders");

            System.out.println("вывод");

        }
    }
}