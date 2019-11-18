package sample;


import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.scene.control.TableColumn.CellEditEvent;
import javax.swing.text.html.ImageView;


public class menuV4iteli  {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lb;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private Label label4;

    @FXML
    private Label LabelVitanya;

    @FXML
    private TableView<rozkladclass> TableRozklad;

    @FXML
    private TableColumn<rozkladclass, String> ColumPonedilok;

    @FXML
    private TableColumn<rozkladclass, String> ColumVivtorok;

    @FXML
    private TableColumn<rozkladclass, String> ColumSereda;

    @FXML
    private TableColumn<rozkladclass, String> ColumChetver;

    @FXML
    private TableColumn<rozkladclass, String> ColumPatnica;

    @FXML
    private Button SetRequest;

    @FXML
    private Button ShowRequest;

    @FXML
    private Button GetResult;

    @FXML
    private Label LabelZayavku;

    @FXML
    private TextField kst;

    @FXML
    private TextField prdmet_hh;

    @FXML
    private Button SetRequestRg1;

    @FXML
    private TableView<Request> TableZayavku;

    @FXML
    private TableColumn<Request, String> ColumVchitel;

    @FXML
    private TableColumn<Request, String> ColumGrup;

    @FXML
    private TableColumn<Request, String> ColumPredm;

    @FXML
    private TableColumn<Request, Integer> ColumParu;

    @FXML
    private Button Save;

    @FXML
    private ComboBox<String> combobox;

    int ct=0;
    @FXML
    void initialize() {
        combobox.setItems(combo);
         visible();
          curUs();
           LabelVitanya.setText("Вітаю - "+currentUser.getName()+" !");
           lb.setText(currentUser.getPredmet());

        SetRequest.setOnAction(event -> {
            label1.setVisible(false);
             label2.setVisible(false);
              label3.setVisible(false);
              label4.setVisible(false);
            prdmet_hh.setVisible(true);
             kst.setVisible(true);
            SetRequestRg1.setVisible(true);
             TableZayavku.setVisible(false);
              TableRozklad.setVisible(false);
               combobox.setVisible(true);
                LabelZayavku.setVisible(true);
        });

        Save.setOnAction(event -> {
            deletroz();
             insert();
        });

        ShowRequest.setOnAction(event -> {
            label1.setVisible(false);
             label2.setVisible(false);
              label3.setVisible(false);
               label4.setVisible(false);
            TableRozklad.setVisible(false);
             TableZayavku.setVisible(true);
              combobox.setVisible(false);
               LabelZayavku.setVisible(false);
            if (!TableZayavku.getItems().isEmpty()) TableZayavku.getItems().clear();
             if (TableZayavku.getItems().isEmpty()) VuvidZayavok();
        });

        SetRequestRg1.setOnAction(event -> {
                podatuZayavku();
                 LabelZayavku.setText("Заявку подано !");
        });

        GetResult.setOnAction(event -> {
            combobox.setVisible(false);
             LabelZayavku.setVisible(false);
              label1.setVisible(true);
               label2.setVisible(true);
                label3.setVisible(true);
                 label4.setVisible(true);
                  TableZayavku.setVisible(false);
                   TableRozklad.setVisible(true);

                if(currentUser.getLogin().equals("admin") )
                 Save.setVisible(true);
                  redagColum();
                 if(!currentUser.getLogin().equals("admin")) coutRozkladBaza();
        });

        TableRozklad.setEditable(true);
    }

    private  ObservableList<String> combo = FXCollections.observableArrayList("1KT15");    /////// Ліст для комбобокса
     private ObservableList<Request> list = FXCollections.observableArrayList();                   /////// Ліст для виводу заявок в таблицю TableZayavku
      private ObservableList<rozkladclass> data =
            FXCollections.observableArrayList(
                    new rozkladclass("-------", "-------", "-------","-------","-------"),
                     new rozkladclass("-------", "-------", "-------","-------","-------"),
                      new rozkladclass("-------", "-------", "-------","-------","-------"),
                       new rozkladclass("-------", "-------", "-------","-------","-------"));    ///// Ліст для чистого розкладу
       private ResultSet curRess = null;
        private User currentUser = new User();
         private ResultSet Res = null;
          private rozkladclass rozklad = new rozkladclass();

         ///////// Метод який записує дані заявки в базу даних таблицю zayavku
    public void podatuZayavku() {
         String grupaa = combobox.getValue();
          String prdmet_h = prdmet_hh.getText();
           int kstt = Integer.parseInt(kst.getText());

            DatabaseHandler dbHandler = new DatabaseHandler();
             String INSERT = "INSERT INTO zayavku(id,vchitel,grup,nazpred,kstpar)VALUES(?,?,?,?,?)";
            try {
                PreparedStatement prST = dbHandler.getDbConnection().prepareStatement(INSERT);
                 prST.setInt(1, currentUser.getIdusers());
                  prST.setString(2, currentUser.getName());
                   prST.setString(3, grupaa);
                    prST.setString(4, prdmet_h);
                     prST.setInt(5, kstt);
                      prST.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
         //////// Метод виводу заявок з бази даних в таблицю TableZayavku
    public void VuvidZayavok() {
        ResultSet res = null;
         DatabaseHandler dbHandler = new DatabaseHandler();
          String SELECT = "SELECT *FROM zayavku";
        try {
            PreparedStatement pr = dbHandler.getDbConnection().prepareStatement(SELECT);
             res = pr.executeQuery();
            while (res.next()) {
                list.add(new Request(res.getString("vchitel"), res.getString("grup"), res.getString("nazpred"),
                        res.getInt("kstpar")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ColumVchitel.setCellValueFactory(new PropertyValueFactory<Request, String>("vchitel"));
         ColumGrup.setCellValueFactory(new PropertyValueFactory<Request, String>("grupaa"));
          ColumPredm.setCellValueFactory(new PropertyValueFactory<Request, String>("prdmet_h"));
           ColumParu.setCellValueFactory(new PropertyValueFactory<Request, Integer>("kstt"));
            TableZayavku.setItems(list);
    }
         //////// Метод ля адміністратора виводу пустого розкладу в таблицю
    public void insert() {
        DatabaseHandler dbHandler = new DatabaseHandler();
         String INSERT = "INSERT INTO rozkklad(ponedilok,vivtorok,sereda,chetver,patnica)VALUES(?,?,?,?,?)";
      try(PreparedStatement pr =dbHandler.getDbConnection().prepareStatement(INSERT)) {
          for(rozkladclass s:data)
          {
              pr.setString(1,s.getPonedilok());
               pr.setString(2,s.getVivtorok());
                pr.setString(3,s.getSereda());
                 pr.setString(4,s.getChetver());
                  pr.setString(5,s.getPatnica());
                   pr.addBatch();
          }
          pr.executeBatch();
      } catch (SQLException e) {
          e.printStackTrace();
      } ;

    }
         //////// Метод зчитування поточного користувача з бази даних таблиці cur та запис його в публічний обєкт
    public void curUs() {
       DatabaseHandler dbHandler = new DatabaseHandler();
        String SELECT = "SELECT *FROM cur";
         PreparedStatement pr = null;
        try {
            pr = dbHandler.getDbConnection().prepareStatement(SELECT);
             curRess = pr.executeQuery();

            while (curRess.next()) {
                currentUser.setIdusers(curRess.getInt("id"));
                 currentUser.setName(curRess.getString("curname"));
                  currentUser.setLogin(curRess.getString("curlogin"));
                   currentUser.setPass(curRess.getString("curpass"));
                    currentUser.setPredmet(curRess.getString("curpredm"));
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
          /////// Метод редагування комірок в таблиці (для адміністратора)
    public void redagColum() {

        ColumPonedilok.setCellValueFactory(
                new PropertyValueFactory<rozkladclass, String>("ponedilok"));   ////////// Встановлення значення для колонки понеділок з ліста data
         ColumPonedilok.setCellFactory(TextFieldTableCell.forTableColumn());     //////// Дозвіл на редагування комірки
          ColumPonedilok.setOnEditCommit(
                new EventHandler<CellEditEvent<rozkladclass, String>>() {
                    @Override
                    public void handle(CellEditEvent<rozkladclass, String> t) {
                        ((rozkladclass) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setPonedilok(t.getNewValue());       ////// Встановлення нових відредагованих значень(зберігання)
                    }
                }
        );
        ColumVivtorok.setCellValueFactory(
                new PropertyValueFactory<rozkladclass, String>("vivtorok"));
         ColumVivtorok.setCellFactory(TextFieldTableCell.forTableColumn());
            ColumVivtorok.setOnEditCommit(
                new EventHandler<CellEditEvent<rozkladclass, String>>() {
                    @Override
                    public void handle(CellEditEvent<rozkladclass, String> t) {
                        ((rozkladclass) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setVivtorok(t.getNewValue());
                    }
                }
        );
        ColumSereda.setCellValueFactory(
                new PropertyValueFactory<rozkladclass, String>("sereda"));
         ColumSereda.setCellFactory(TextFieldTableCell.forTableColumn());
          ColumSereda.setOnEditCommit(
                new EventHandler<CellEditEvent<rozkladclass, String>>() {
                    @Override
                    public void handle(CellEditEvent<rozkladclass, String> t) {
                        ((rozkladclass) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setSereda(t.getNewValue());
                    }
                }
        );
        ColumChetver.setCellValueFactory(
                new PropertyValueFactory<rozkladclass, String>("chetver"));
         ColumChetver.setCellFactory(TextFieldTableCell.forTableColumn());
          ColumChetver.setOnEditCommit(
                new EventHandler<CellEditEvent<rozkladclass, String>>() {
                    @Override
                    public void handle(CellEditEvent<rozkladclass, String> t) {
                        ((rozkladclass) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setChetver(t.getNewValue());
                    }
                }
        );

        ColumPatnica.setCellValueFactory(
                new PropertyValueFactory<rozkladclass, String>("patnica"));
         ColumPatnica.setCellFactory(TextFieldTableCell.forTableColumn());
          ColumPatnica.setOnEditCommit(
                new EventHandler<CellEditEvent<rozkladclass, String>>() {
                    @Override
                    public void handle(CellEditEvent<rozkladclass, String> t) {
                        ((rozkladclass) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setPatnica(t.getNewValue());
                    }
                }
        );
        TableRozklad.setItems(data);
    }
          /////// Видалення попереднього розкладу з бази даних
    public void deletroz() {
    DatabaseHandler dbHandler = new DatabaseHandler();
     String DELETE = "DELETE FROM rozkklad";
    try {
        PreparedStatement pr = dbHandler.getDbConnection().prepareStatement(DELETE);
         pr.execute();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
        //////// Видимість деяких компонентів
    public void visible() {
      prdmet_hh.setVisible(false);
       kst.setVisible(false);
        TableZayavku.setVisible(false);
         SetRequestRg1.setVisible(false);
          ShowRequest.setVisible(true);
     GetResult.setVisible(true);
      SetRequest.setVisible(true);
       TableRozklad.setVisible(false);
        label1.setVisible(false);
         label2.setVisible(false);
           label3.setVisible(false);
            label4.setVisible(false);
              Save.setVisible(false);
        combobox.setVisible(false);
         LabelZayavku.setVisible(false);
 }
       //////// Вигрузка розкладу з БД в таблицю TableRozklad(для простих користувачів)
    public void coutRozkladBaza() {
     DatabaseHandler dbHandler = new DatabaseHandler();
      String SELECT = "SELECT *FROM rozkklad";
       ObservableList<rozkladclass> razkladBD = FXCollections.observableArrayList();
        PreparedStatement pr = null;
     try {
         pr = dbHandler.getDbConnection().prepareStatement(SELECT);
          Res = pr.executeQuery();

         while (Res.next()) {
            razkladBD.add(new rozkladclass(Res.getString("ponedilok"),Res.getString("vivtorok"),Res.getString("sereda"),Res.getString("chetver"),Res.getString("patnica")));
             rozklad.setPonedilok(Res.getString("ponedilok"));
              rozklad.setVivtorok(Res.getString("vivtorok"));
               rozklad.setSereda(Res.getString("sereda"));
                rozklad.setChetver(Res.getString("chetver"));
                 rozklad.setPatnica(Res.getString("patnica"));
         }
     } catch (SQLException e1) {
         e1.printStackTrace();
     }
     ColumPonedilok.setCellValueFactory(new PropertyValueFactory<rozkladclass, String>("ponedilok"));
      ColumVivtorok.setCellValueFactory(new PropertyValueFactory<rozkladclass, String>("vivtorok"));
       ColumSereda.setCellValueFactory(new PropertyValueFactory<rozkladclass, String>("sereda"));
        ColumChetver.setCellValueFactory(new PropertyValueFactory<rozkladclass, String >("chetver"));
         ColumPatnica.setCellValueFactory(new PropertyValueFactory<rozkladclass, String >("patnica"));
          TableRozklad.setItems(razkladBD);
 }
}

