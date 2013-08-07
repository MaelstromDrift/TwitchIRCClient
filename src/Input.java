import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements ActionListener, KeyListener, Runnable {
    Thread inputThread = null;
    Object source = null;
    ActionEvent event = null;

    public Input() {
        Window.login.addActionListener(this);
        Window.connect.addActionListener(this);
        Window.send.addActionListener(this);
        Window.chatBox.addKeyListener(this);
    }

    public void run() {
        source = event.getSource();
        if (source == Window.send) {
            System.out.println(Window.chatBox.getText());
            IRC.sendMessage(Window.chatBox.getText());
            Window.chatBox.setText("");
            source = null;
        }
        inputThread = null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (event != e || event == null) {
            event = e;
            source = event.getSource();
        }
        if (inputThread == null) {
            inputThread = new Thread(this);
            inputThread.start();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 10) {
            IRC.sendMessage(Window.chatBox.getText());
            Window.chatBox.setText("");
            Window.chatBox.setCaretPosition(Window.chatBox.getDocument().getLength() - 1);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
