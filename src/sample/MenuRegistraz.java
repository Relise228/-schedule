package sample;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class MenuRegistraz {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField signUpName;

    @FXML
    private TextField login_field;

    @FXML
    private TextField password_field;

    @FXML
    private TextField lesson;

    @FXML
    private Button SignUpButton;
    @FXML
    private Label LabelReg;

    @FXML
    void initialize() {

        SignUpButton.setOnAction(event -> {
            signUpNewUser();

        });
    }

    public DatabaseHandler dbHandler = new DatabaseHandler();
     public ResultSet result;
      public int counter = 0;

    ///////////ДОДАННЯ ДАНИХ В ТАБЛИЦЮ ПРОФЕСОРІВ
    private void signUpNewUser() {
        String name = signUpName.getText();
         String login = login_field.getText().trim();
          String pass = password_field.getText();
           String predmet = lesson.getText();
        if(!name.equals("")&&!login.equals("")&&!pass.equals("")&&!predmet.equals("")) {
            counter = 0;
            try {
                detect(login);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            switch (counter) {     ////// Якщо однакового логіну не виявлено(counter=0) то реєстрація успішна а якщо виявлено (counter=1) то вікно помилки
                case 0:
                    User user = new User(name, login, pass, predmet);
                     dbHandler.signUpUser(user);
                      LabelReg.setText("Реєстрація успішна,закрийте вкладку!");
                    break;
                case 1:
                    LabelReg.setText("Логін зайнятий,повторіть спробу!");
                    break;
            }
        }
        else LabelReg.setText("Заповніть всі поля!");
    }
    ////// Пошук співпадаючого логіну в базі даних
    private void detect(String login) throws SQLException {
        String SELECT = "SELECT *FROM proffesori WHERE login=?";
        try {
            PreparedStatement pr = dbHandler.getDbConnection().prepareStatement(SELECT);
             pr.setString(1, login);
              result = pr.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        while (result.next()) {
            counter++;

        }
    }
}


