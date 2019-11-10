package ua.kiev.prog;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Message {
    String from;
    String to;
    String text;
    Date date = new Date();
    boolean isPrivate;
    int room;

    public Message() {
    }

    public Message(String from, String to, String text, Date date, int room) {
        this.from = from;
        this.to = to;
        this.text = text;
        this.date = date;
        this.room = room;
        if (to.equals("-"))
            this.isPrivate = false;
        else
            this.isPrivate = true;
    }

    public int send() {
        try {
            URL url = new URL("http://127.0.0.1:8080/message");
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            OutputStream out = http.getOutputStream();
            Gson gson = new GsonBuilder().create();
            String x = gson.toJson(this);
            byte[] b = x.getBytes();
            out.write(b);
            out.close();
            out.flush();
            return http.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 404;
    }

    public static int getMessageList(String login, int room, int i) {
        try {
            URL url = new URL("http://127.0.0.1:8080/mlist?login=" + login
                    + "&room=" + room + "&from=" + i);
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
            Gson gson = new GsonBuilder().create();
            Message[] chat = (Message[]) gson.fromJson(x, Message[].class);
            for (Message m : chat)
                System.out.println(m);
            return chat.length;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String toString() {
        return new StringBuilder().append("[").append(date.toString())
                .append(", From: ").append(from).append(", To: ").append(to)
                .append(", room: ").append(room).append("] ").append(text).toString();
    }

}
