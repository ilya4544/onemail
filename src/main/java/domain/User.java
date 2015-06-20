package domain;

/**
 * Created by Ilya on 20.03.2015.
 */
public class User {
    private String uid;
    private String id_token;
    private AccessToken a_token;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String name;
    private String email;

    public User(String uid, AccessToken a_token, String id_token, String name, String email) {
        this.uid = uid;
        this.id_token = id_token;
        this.a_token = a_token;
        this.name = name;
        this.email = email;

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getId_token() {
        return id_token;
    }

    public void setId_token(String id_token) {
        this.id_token = id_token;
    }

    public AccessToken getA_token() {
        return a_token;
    }

    public void setA_token(AccessToken a_token) {
        this.a_token = a_token;
    }
}
