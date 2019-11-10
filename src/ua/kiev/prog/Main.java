package ua.kiev.prog;

public class Main {

    public static void main(String[] args) {

        Chat chat = new Chat();
        chat.explanMethod();
        if (chat.setUser()) {
            while (true) {
                chat.setIsRunning(true);
                Thread th = new Thread(chat);
                th.start();
                while (chat.getIsRunning())
                    chat.setIsRunning(chat.enterMessage());
            }

        }
    }

}
