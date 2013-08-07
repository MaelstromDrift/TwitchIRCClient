import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class Window extends JFrame implements Runnable {
    Thread guiThread = new Thread(this);
    public static JPanel panel1 = new JPanel();
    public static JPanel panel2 = new JPanel();

    public static JButton login = new JButton("Login");
    public static JButton connect = new JButton("Connect");
    public static JButton send = new JButton("Send");

    Font font = new Font("Arial", Font.BOLD, 15);

    public static JTextArea chatBox = new JTextArea(1, 50);
    public static JTextArea chat = new JTextArea(10, 40);

    //creating a scroll pane to allow the chat to scroll
    JScrollPane scroll = new JScrollPane(chat);

    //creating a caret object so we can modify it later to allow autoscrolling
    DefaultCaret caret = (DefaultCaret) chat.getCaret();

    public Window() {
        super("IRC Client");
        setSize(600, 300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //telling the caret to update to the end
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        chat.setFont(font);
        chat.setBackground(Color.BLACK);
        chat.setForeground(Color.WHITE);

        //create the layout
        constructLayout();
        //allow us to scroll using our scroll wheel
        scroll.setWheelScrollingEnabled(true);

        //Allows the chat to not be modified
        chat.setEditable(false);
        chatBox.setLineWrap(true);
        chat.setLineWrap(true);

        //show everything
        guiThread.start();
        setLocationByPlatform(true);
        setVisible(true);
    }

    public void run() {
        while (true) {
            panel1.updateUI();
        }
    }

    public void constructLayout() {
        //set the default layout of the window
        BorderLayout layout = new BorderLayout();
        setLayout(layout);

        //set the panels layout to be a grid
        GridLayout grid = new GridLayout(1, 1);
        panel1.setLayout(grid);

        //add the buttons to the layout
        panel1.add(send);

        //set the layout of the panel to FlowLayout
        GridLayout chatGrid = new GridLayout(2, 1);
        panel2.setLayout(chatGrid);
        panel2.add(scroll);
        panel2.add(chatBox);

        //add the panels to the window using the BorderLayout
        add(panel1, BorderLayout.SOUTH);
        add(panel2, BorderLayout.NORTH);
    }
}