import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.*;

public class Joel extends Frame implements ActionListener, Runnable {

    JTextField textField;
    JTextArea textArea;
    JButton send;

    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    ServerSocket serverSocket;
    Socket socket;

    Thread chat;

    Joel() {
        textField = new JTextField();
        textArea = new JTextArea();
        send = new JButton("Send");
        send.addActionListener(this);

        try {
            serverSocket = new ServerSocket(9000);
            socket = serverSocket.accept();

            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }

        textArea.setBounds(20, 40, 800, 500);
        textField.setBounds(20, 530, 600, 30);
        send.setBounds(640, 530, 80, 30);

        add(textArea);
        add(textField);
        add(send);
        chat = new Thread(this);
        chat.setDaemon(true);
        chat.start();

        setTitle("Joel");
        setLayout(null);
        setSize(800, 600);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Joel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = textField.getText();
        textArea.append("Joel: " + msg + "\n");
        textField.setText("");

        try {
            dataOutputStream.writeUTF(msg);
            dataOutputStream.flush();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                String msg = dataInputStream.readUTF();
                textArea.append("Nithin: " + msg + "\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
