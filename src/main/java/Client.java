import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {


        String host = "netology.homework";
        String portWithText = Server.read("settings.txt");
        int port = Server.readPort(portWithText);

        Socket clientSocket = new Socket(host, port);
        System.out.printf("Client started, port - %s, host - %s\n", port, host);

        try (
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                Scanner scan = new Scanner(System.in)) {

            while (true) {

                System.out.println("Сколько участников будет в чате?");
                int clientsAmount = scan.nextInt();
                String clientName;
                System.out.println("Введите имена участников");
                for (int i = 1; i < clientsAmount; i++) {
                    clientName = scan.nextLine();
                    Thread client = new Thread();
                    client.setName("Клиент " + clientName);
                    if (i == clientsAmount){
                        System.out.println("Чат заполнен");
                        break;
                    }
                }
//                while (true) {
//                    System.out.println("Добро пожаловать в чат! Как вас зовут?");
//                    Thread client = new Thread();
//                    client.setName(in.readLine());
//                }
//                //System.out.println("Чат начинается!\n");
//                String message = scan.nextLine();
//                if (message.equals("exit")) {
//                    break;
//                }
            }
//                else if (message.equals("Новый клиент")) {
//                    //Thread client = new Thread();
//                    System.out.println("Введите имя нового пользователя");
//                    client.setName(in.readLine());
//                    continue;
//                }

            //out.println(message);
        }
    }
}

