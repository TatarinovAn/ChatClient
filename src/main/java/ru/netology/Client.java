package ru.netology;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Logger logger = Logger.getInstance();
        String host = "127.0.0.1";
        StringBuffer sb = new StringBuffer();

        try (FileReader reader = new FileReader("D://Chat/Setting/setting.txt")) {
            int c;
            while ((c = reader.read()) != -1) {
                sb.append((char) c);
            }
        }

        int port = Integer.parseInt(sb.toString());

        System.out.println("Please, write your name:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        System.out.println("Hi, " + name + " welcome chat");



        while (true) {
            try (Socket clientSocker = new Socket(host, port);
                 PrintWriter out = new PrintWriter(clientSocker.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocker.getInputStream())))
            {
                String text = scanner.nextLine();
                out.println("[" + name + "]" + " " + text);
                logger.log(in.readLine());
            }
        }
    }
}
