package ua.kiev.prog;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class User {

    private String login;
    private String password;
    private boolean isOnline;
    private int room;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.isOnline = true;

    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getAccess() {
        boolean isLogin = false;
        try {
            URL url = new URL("http://127.0.0.1:8080/access?login="
                    + this.getLogin() + "&password=" + this.getPassword());
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            int c = http.getResponseCode();
            System.out.println(http.getResponseMessage());
            if (c == 200)
                isLogin = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isLogin;
    }

    public void getUserList() {
        try {
            URL url = new URL("http://127.0.0.1:8080/userlist?room="
                    + this.getRoom());
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            InputStream in = http.getInputStream();
            byte[] b = new byte[in.available()];
            in.read(b);
            StringBuilder sb = new StringBuilder();
            for (byte bb : b) {

                char c = (char) bb;
                sb.append(c);
            }
            String x = sb.toString();
            Gson gson2 = new GsonBuilder().create();
            HashMap<String, String> hm = gson2.fromJson(x, HashMap.class);
            System.out.println("------------------");
            System.out.println("<< Userlist of room#" + this.getRoom() + ":");

            for (Entry<String, String> entry : hm.entrySet()) {

                System.out.println("<< " + entry.getKey() + " is "
                        + entry.getValue() + ";");
            }

            System.out.println("------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean changeRoom(int newRoom) {
        boolean flag = false;
        try {
            URL url = new URL("http://127.0.0.1:8080/changeroom?login="
                    + this.login + "&room=" + newRoom);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            int x = http.getResponseCode();
            if (x == 200)
                flag = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return flag;
    }

    @Override
    public String toString() {
        return "User [login=" + login + ", isOnline=" + isOnline + "]";
    }

}

