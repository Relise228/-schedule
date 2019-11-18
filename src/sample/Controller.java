package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller {
    public  ResultSet result = null;
     public ResultSet curRes = null;
      public  User currentUser = new User();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login_field;

    @FXML
    private TextField password_field;

    @FXML
    private Button authLogInButton;

    @FXML
    private Button loginSignUpButton;


    @FXML
    private Label labelvhid;

    @FXML
    void initialize() {
        /////////////////////КНОПКА АВТОРИЗАЦІЇ

        authLogInButton.setOnAction(event -> {
           counter = 0;
            DelCur();
             perevirka();


        });
        //////////////КНОПКА РЕЄСТРАЦІЇ
        loginSignUpButton.setOnAction(event -> {
            perehidMenuRegistraz();

        });

    }

    int counter ; ///// - Лічилник знайденого користувача
    ///////    Метод пошуку по базі даних користувача
    private void loginUser(String loginText, String passText) throws SQLException {
        DatabaseHandler dbHandler = new DatabaseHandler();
         String SELECT = "SELECT *FROM proffesori WHERE login=? AND pass=?";


        try {
            PreparedStatement pr = dbHandler.getDbConnection().prepareStatement(SELECT);
             pr.setString(1, loginText);
              pr.setString(2, passText);
               result = pr.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        while (result.next()) {
            counter++;

        }


    }
     /////////  Перехід на форму реєстрації
    private void perehidMenuRegistraz() {


        FXMLLoader Loader = new FXMLLoader();
         Loader.setLocation(getClass().getResource("/sample/MenuProffesor.fxml"));

        try {
            Loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = Loader.getRoot();
         Stage stage = new Stage();
          stage.setScene(new Scene(root));
           stage.showAndWait();

    }
     ////////  Перехід в Меню
    private void perehidMenuProff() {
        FXMLLoader Loader = new FXMLLoader();
         Loader.setLocation(getClass().getResource("MenuV4iteli.fxml"));  ////////Вікно меню Професорів

        try {
            Loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = Loader.getRoot();
         Stage stage = new Stage();
          stage.setScene(new Scene(root));
           stage.showAndWait();
    }
     ///////  Метод входу або помилки входу
    private void perevirka() {
        String loginText = login_field.getText().trim(); //////////Зчитування логіну без пропусків
         String passText = password_field.getText().trim();   //////////Зчитування паролю без пропусків

        try {
            loginUser(loginText, passText);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        switch (counter) {
            case 0:
                    labelvhid.setText("ПОМИЛКА");break;
            case 1:

                    SelCur(loginText, passText);
                     perehidMenuProff();

                break;
            }

    }
    ////////  Метод встановлення поточного користуувача і запис його в окрему таблицю в базі данних
    private void SelCur(String loginText, String passText) {

        DatabaseHandler dbHandler = new DatabaseHandler();
         String SELECT = "SELECT *FROM proffesori WHERE login=? AND pass=?";
        try {
            PreparedStatement pr = dbHandler.getDbConnection().prepareStatement(SELECT);
             pr.setString(1, loginText);
              pr.setString(2, passText );

            curRes = pr.executeQuery();

            while (curRes.next()){
                currentUser.setIdusers(curRes.getInt("idusers"));
                 currentUser.setName(curRes.getString("name"));
                  currentUser.setLogin(curRes.getString("login"));
                   currentUser.setPass(curRes.getString("pass"));
                    currentUser.setPredmet(curRes.getString("predmet"));

            }

             String INSERT = "INSERT INTO cur(id,curname,curlogin,curpass,curpredm)VALUES(?,?,?,?,?)";

            try {
                PreparedStatement prST = dbHandler.getDbConnection().prepareStatement(INSERT);
                 prST.setInt(1,currentUser.getIdusers());
                  prST.setString(2,currentUser.getName());
                   prST.setString(3,currentUser.getLogin());
                    prST.setString(4,currentUser.getPass());
                     prST.setString(5,currentUser.getPredmet());
                      prST.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
       labelvhid.setText("Вхід");
    }
    //////// Метод очищення поточного користувача з бази даних
    private void DelCur(){
        DatabaseHandler dbHandler = new DatabaseHandler();
         String DELETE = "DELETE FROM cur";
          Statement ps = null;
        try {
            ps = dbHandler.getDbConnection().createStatement();
             ps.executeUpdate(DELETE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





}




