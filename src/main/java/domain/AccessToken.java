package domain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.util.JSON;

/**
 * Created by freeemahn on 19.06.15.
 */
public class AccessToken {
    private String id_token;
    private String access_token;
    private String expires_in;
    private String state;
    private String token_type;
    private String refresh_token;

    public AccessToken(String id_token, String access_token, String expires_in, String state, String token_type, String refresh_token) {
        this.id_token = id_token;
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.state = state;
        this.token_type = token_type;
        this.refresh_token = refresh_token;
    }

    public AccessToken(String id_token, String expires_in, String state, String token_type, String refresh_token) {
        this.id_token = id_token;
        this.expires_in = expires_in;
        this.state = state;
        this.token_type = token_type;
        this.refresh_token = refresh_token;
    }


    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getId_token() {
        return id_token;
    }

    public void setId_token(String id_token) {
        this.id_token = id_token;
    }

    public String getUsersIdentifier() {
        Gson gson = new GsonBuilder().create();
        ID_Token idt = gson.fromJson(id_token, ID_Token.class);
        return idt.message.sub;
    }

    class Header {
        //OtherStuff
    }

    class Message {
        String sub;
        String exp;
        //OtherStuff
    }

    class Signature {
        //OtherStuff
    }

    class ID_Token {
        Header header;
        Message message;
        Signature signature;
    }
}
