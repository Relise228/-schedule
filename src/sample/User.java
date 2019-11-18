package sample;

public class User {

    private int idusers;
    private String name;
    private String login;
    private String pass;
    private String predmet;

    public User(String name, String login, String pass, String predmet) {
        this.name = name;
        this.login = login;
        this.pass = pass;
        this.predmet = predmet;
    }
    public User() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPredmet() {
        return predmet;
    }

    public void setPredmet(String predmet) {
        this.predmet = predmet;
    }

    public int getIdusers() {
        return idusers;
    }

    public void setIdusers(int idusers) {
        this.idusers = idusers;
    }
}
