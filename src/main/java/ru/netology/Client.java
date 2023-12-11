package ru.netology;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class Client {
    private static final String SERVER_HOST = "localhost";
    private Socket clientSocket;
    private static Scanner inMessage;
    // исходящее сообщение
    private PrintWriter outMessage;
    private static int port;
    private String clientName = "";
    public static Thread outThread;
    public static Thread inThread;
    private Logger logger = Logger.getInstance();


    // получаем имя клиента
    public Client() throws InterruptedException {

        StringBuffer sb = new StringBuffer();
        try (FileReader reader = new FileReader("D://Chat/Setting/setting.txt")) {
            int c;
            while ((c = reader.read()) != -1) {
                sb.append((char) c);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Ошибка в чтении");
        }

        port = Integer.parseInt(sb.toString());

        try {
            // подключаемся к серверу
            clientSocket = new Socket(SERVER_HOST, port);
            inMessage = new Scanner(clientSocket.getInputStream());
            outMessage = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Ошибка в подключении");
        }
        // в отдельном потоке начинаем работу с сервером
        System.out.println("Please, write your name:");
        Scanner scanner = new Scanner(System.in);
        String clientName = scanner.nextLine();

        outThread = new Thread(() -> {
            try {
                // бесконечный цикл
                System.out.println("Hi, " + clientName + " write massages");
                while (true) {
                    // если есть входящее сообщение
                    if (inMessage.hasNext()) {
                        // считываем его
                        String inMes = inMessage.nextLine();
                        File fileChat = new File("D://Chat/Mes_Client"); // Создание файла сообщений
                        fileChat.mkdirs();
                        try (FileWriter writer = new FileWriter("D://Chat/Mes_Client/mes.txt", true)) {
                            writer.write(inMes);
                            writer.append('\n');
                            writer.flush();
                        } catch (IOException ex) {
                            System.out.println(ex.getMessage());
                        }
                        System.out.println(inMes);
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Ошибка в передачи");
            }
        });

        outThread.start();


        inThread = new Thread(() -> {
            Scanner scanner2 = new Scanner(System.in);
            while (true) {
                String mess = scanner2.nextLine();
                sendMsg(logger.log(clientName + ": " + mess));
            }
        });

        inThread.start();


        outThread.join();
        inThread.join();

        Logger logger = Logger.getInstance();
    }

    public String getClientName() {
        return this.clientName;
    }


    public void sendMsg(String mes) {
        // формируем сообщение для отправки на сервер
        String messageStr = mes;
        // отправляем сообщение
        outMessage.println(messageStr);
        outMessage.flush();

    }
}
