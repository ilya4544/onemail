package domain;

/**
 * Created by Ilya on 20.03.2015.
 */
public class User {
    private String login;
    private String name;
    private String currentLocation;
    private String nextLocation;
    private String date;

    public User(String login, String name, String currentLocation, String nextLocation, String date) {
        this.login = login;
        this.name = name;
        this.currentLocation = currentLocation;
        this.nextLocation = nextLocation;
        this.date = date;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getNextLocation() {
        return nextLocation;
    }

    public void setNextLocation(String nextLocation) {
        this.nextLocation = nextLocation;
    }
}
