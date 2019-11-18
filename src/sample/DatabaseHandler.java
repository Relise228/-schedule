package sample;
import java.sql.*;


public class DatabaseHandler  extends Config{

    Connection dbConnection;

/////////////////Підєднання до ДБ
    public Connection getDbConnection() throws SQLException {
        String connectionString = "jdbc:mysql://127.0.0.1:3306/rozklad?autoReconnect=true&useSSL=false&serverTimezone=UTC";
         dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
          Statement statement = dbConnection.createStatement();
           statement.getConnection();
        if (!dbConnection.isClosed()) {
            System.out.println("Зєднання з БД Встановленно!");
        }
         return dbConnection;
    }

/////////////////Регістрація Користувача
    public void signUpUser(User user){
        String insert = "INSERT INTO " +  Const.PRPFFESOR_TABLE + "(" + Const.PROFFESOR_NAME + "," + Const.PROFFESOR_LOGIN
                + "," + Const.PROFFESOR_PASS + "," + Const.PROFFESOR_PREDMET + ")" + "VALUES(?,?,?,?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
             prSt.setString(1,user.getName());
              prSt.setString(2,user.getLogin());
               prSt.setString(3,user.getPass());
                prSt.setString(4,user.getPredmet());
                 prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            }
    }
}
