package utb.fai;

import java.net.*;
import java.io.*;

public class EmailSender {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    /*
     * Constructor opens Socket to host/port. If the Socket throws an exception
     * during opening,
     * the exception is not handled in the constructor.
     */
    public EmailSender(String host, int port) throws UnknownHostException, IOException {
        socket = new Socket(host, port);

        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        String response = reader.readLine();
        System.out.println("Server: " + response);

        sendCommand("EHLO localhost");
    }

    /*
     * Sends email from an email address to an email address with some subject and
     * text.
     * If the Socket throws an exception during sending, the exception is not
     * handled by this method.
     */
    public void send(String from, String to, String subject, String text) throws IOException {
        sendCommand("MAIL FROM:<" + from + ">");
        
        sendCommand("RCPT TO:<" + to + ">");
        
        sendCommand("DATA");
        
        writer.write("From: " + from + "\r\n");
        writer.write("To: " + to + "\r\n");
        writer.write("Subject: " + subject + "\r\n");
        writer.write("\r\n"); // Prázdný řádek mezi hlavičkami a tělem zprávy
        writer.write(text + "\r\n");
        writer.write(".\r\n"); // Označení konce zprávy
        writer.flush();
    
        String response = reader.readLine();
        System.out.println("Server: " + response);
    }

    /*
     * Sends QUIT and closes the socket
     */
    public void close() {
        try {
            sendCommand("QUIT");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendCommand(String command) throws IOException {
        writer.write(command + "\r\n");
        writer.flush();
    
        String response = reader.readLine();
        System.out.println("Server: " + response);
    }
}
