import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.*;

public class Nithin extends Frame implements ActionListener, Runnable{

    JTextField textField;
    JTextArea textArea;
    JButton send;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    Socket socket;

    Thread chat;
    Nithin(){
        textField = new JTextField();
        textArea = new JTextArea();
        send = new JButton("Send");
        send.addActionListener(this);

        textArea.setBounds(20, 40, 800, 500);
        textField.setBounds(20, 530, 600, 30);
        send.setBounds(640, 530, 80, 30);

        try{

            socket = new Socket("localhost", 9000);

            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());

        }catch(Exception E){

        }

        add(textArea);
        add(textField);
        add(send);
        chat = new Thread(this);
        chat.setDaemon(true);
        chat.start();

        setTitle("Nithin");
        setLayout(null);
        setSize(800,600);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Nithin();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = textField.getText();
        textArea.append("Nithin: "+msg+"\n");
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
        while(true){
            try{
                String msg = dataInputStream.readUTF();
                textArea.append("Joel: "+msg+"\n");
            }catch(Exception E){

            }
        }
    }
}
