package ua.kiev.prog;

import java.util.Date;
import java.util.Scanner;

    public class Chat implements Runnable {

        private User user;
        private int i = 0;
        Scanner scanner = new Scanner(System.in);
        private boolean isRunning;

        public void setIsRunning(boolean isRunning) {
            this.isRunning = isRunning;
        }

        public boolean getIsRunning() {
            return isRunning;
        }

        public int getI() {
            return i;
        }

        public User getUser() {
            return user;
        }

        public void explanMethod() {
            System.out
                    .println("<< To test this chat uou must use login 'user' and password 'user'.>>");
            System.out.println("<< Hello! You are in CHAT>>");
            System.out.println("<< To see participants enter the command: -userlist>>");
            System.out
                    .println("<< To change your room enter the command: -room#X, where X is the number of room.>>");
            System.out
                    .println("<< To send private message enter #username before text of your message, where username is the name of receiver.>>");
        }

        public boolean setUser() {

            System.out.println("<< Enter the login:");
            String login = scanner.nextLine();
            System.out.println("<< Enter the password:");
            String password = scanner.nextLine();
            user = new User(login, password);
            if (user.getAccess()) {
                System.out.println("<< You entered the chat.");
                return true;
            } else {
                System.out.println("<< Wrong login or password.");
                return false;
            }
        }

        @Override
        public void run() {

            while (this.getIsRunning()) {

                this.i = this.i
                        + Message
                        .getMessageList(user.getLogin(), user.getRoom(), i);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }

        public boolean enterMessage() {
            Message m;
            String newMessage = scanner.nextLine();
            if (newMessage.equals("-userlist")) {
                user.getUserList();
                return true;
            }
            else if (newMessage.startsWith("#")) {
                String[] s = newMessage.split(" ");
                String to = s[0].substring(1, s[0].length());
                StringBuilder text = new StringBuilder();
                for (int i = 1; i < s.length; i++)
                    text.append(s[i]);
                m = new Message(user.getLogin(), to, text.toString(), new Date(),
                        user.getRoom());
                m.send();
                return true;
            } else if (newMessage.startsWith("-room#")) {
                String r = newMessage.substring(6, 7);
                int newRoom = Integer.parseInt(r);
                boolean flag = user.changeRoom(newRoom);
                if (flag) {
                    System.out.println("<< You entered room #" + newRoom);
                    user.setRoom(newRoom);
                } else
                    System.out.println("<< Error in changing room.");
                return !flag;
            }

            else {
                m = new Message(user.getLogin(), "-", newMessage, new Date(),
                        user.getRoom());
                m.send();
                return true;
            }
        }

    }

