import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server {

    public static File createNewFile(String name) {
        //Метод для создания файла настроек и файла логирования
        File file = new File(name);
        try {
            if (file.createNewFile())
                System.out.printf("Создан файл %s\n", name);
        } catch (IOException ex) {
            System.out.println("Файл настроек не был создан");
        }
        return file;
    }

//    public static void createNewFile(String name, String text) {
//        //Метод для одновременного создания файла и записи в него строки второй способ
//        //Но используем метод createNewFile и write, для того, чтобы с помощью метода write потом логировать
//        try (FileOutputStream fos = new FileOutputStream(name, false)) {
//            byte[] bytes = text.getBytes();
//            fos.write(bytes, 0, bytes.length);
//        } catch (IOException ex){
//            System.out.println(ex.getMessage());
//        }
//    }

    public static void write(String name, String text) {
//        метод для записи текста в файл настроек
        try (FileWriter fileWriter = new FileWriter(name, true)) {
            fileWriter.write(text + '\n');
            System.out.printf("Файл %s дополнен\n", name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String read(String name) {
//        метод для чтения текста из файла
        String s = null;
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(name), StandardCharsets.UTF_8))) {
            String s1;
            while ((s1 = br.readLine()) != null) {
                s = s1;
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return s;
    }

    public static int readPort(String portWithText) {
//        метод для чтения числа из файла настроек
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(portWithText);
        int start = 0;
        int port = 0;
        while (matcher.find(start)) {
            String value = portWithText.substring(matcher.start(), matcher.end());
            int result = Integer.parseInt(value);
            port = result;
            start = matcher.end();
        }
        return port;
    }

    public static void main(String[] args) throws IOException {

        File settings = createNewFile("settings.txt");
        File log = createNewFile("file.log");

//        создание файла и записи в него настроек вторым способом
//        createNewFile("settings.txt", "Привет вонючки");
//        createNewFile("file.log", null);

        write("settings.txt", "Номер порта - 16162");
        String s = read("settings.txt");
        int port = readPort(s);

        ServerSocket serverSocket = new ServerSocket(port);
        System.out.printf("Server started, port %s\n", port);

        while (true) {

            try (Socket clientSocket = serverSocket.accept();
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); // от сервера к клиенту - пишем
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) // от клиента к сервера - читаем
            {
                while (true) {
                    String inputMessage = in.readLine();
                    write("file.log", inputMessage);
                    System.out.println("Новое сообщение на сервере " + inputMessage);
                }
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }
}