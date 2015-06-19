package domain;

/**
 * Created by Ilya on 19.06.2015.
 */
public class Company {
    private String login;
    private String pass;
    private String description;

    public Company(String login, String pass, String description) {
        this.login = login;
        this.pass = pass;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
