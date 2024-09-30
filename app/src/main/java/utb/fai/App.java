package utb.fai;

public class App {

    public static void main(String[] args) {
        // Kontrola počtu argumentů
        if (args.length != 6) {
            System.out.println("Použití: java App <host> <port> <from> <to> <subject> <text>");
            return;
        }

        // Zpracování argumentů
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        String from = args[2];
        String to = args[3];
        String subject = args[4];
        String text = args[5];

        try {
            EmailSender sender = new EmailSender(host, port);
            sender.send(from, to, subject, text);
            sender.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
