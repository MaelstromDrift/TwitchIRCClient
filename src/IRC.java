import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class IRC implements Runnable {
    Thread ircThread = new Thread(this);

    private static String address = "maelstromdrift.jtvirc.com";

    private static String channel;

    private static boolean connected = false;

    private static int port = 6667;

    protected static Socket echoSocket = null;
    private static BufferedReader in = null;
    private static PrintWriter out = null;
    static String fromServer;

    public IRC() throws IOException {
        echoSocket = new Socket(address, port);

        in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        out = new PrintWriter(echoSocket.getOutputStream());

        ircThread.start();
    }

    public void run() {
        getChat();
    }

    public static void sendMessage(String message) {
        String[] temp;
        temp = message.split(" ");

        if (temp[0].equals("JOIN")) {
            out.println(message);
            out.flush();
            connected = true;
        } else if (temp[0].equals("PART") || temp[0].equals("WHO")) {
            out.println(message);
            out.flush();
        } else {
            out.println("PRIVMSG #" + channel + " :" + message);
            out.flush();
            Window.chat.setText(Window.chat.getText() + "\n" + "You: " + message);
        }
    }

    public static void connect(String newChannel) {
        channel = newChannel;
        out.println("JOIN " + "#" + channel);
        out.flush();
        connected = true;
    }

    public static void getChat() {
        try {
            while ((fromServer = in.readLine()) != null) {
                if (fromServer.equals("PING tmi.twitch.tv")) {
                    System.out.println("ping pong");
                    out.println("PONG");
                    out.flush();
                }
                int a = fromServer.indexOf("!");
                int b = fromServer.indexOf(":", 1);

                if (connected && b >= 0 && a >= 0) {
                    Window.chat.setText(Window.chat.getText() + "\n" + fromServer.substring(1, a) + ": " + fromServer.substring(b + 1));
                    System.out.println(fromServer);
                } else if (!connected) {
                    Window.chat.setText(Window.chat.getText() + "\n" + fromServer);
                }
            }
        } catch (UnknownHostException e) {
            //do stuff

        } catch (IOException e) {
            //do stuff
        }
    }

    public static boolean login(String user, String pass) throws IOException {
        out.println("PASS " + pass);
        out.flush();

        out.println("NICK " + user);
        out.flush();

        if (in.readLine() != null) {
            return true;
        } else {
            return false;
        }
    }
}