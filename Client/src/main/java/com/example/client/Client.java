package com.example.client;

import java.io.*;
import java.net.*;

public class Client {
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream out = null;

    public Client(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected");

            input = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            String domainOptions = input.readUTF();
            System.out.println("Alege un domeniu:\n" + domainOptions);
            System.out.print("Alegerea ta: ");
            String domainChoice = new BufferedReader(new InputStreamReader(System.in)).readLine();
            out.writeUTF(domainChoice);

            System.out.print("Câte dintre ultimele știri dorești să vezi? Scrie un număr sau 'all' pentru toate: ");
            String nrOfNewsChoice = new BufferedReader(new InputStreamReader(System.in)).readLine();
            out.writeUTF(nrOfNewsChoice);

            String line = "";
            while (!line.equals("Over")) {
                line = input.readUTF();
                if (!line.isEmpty()) {
                    System.out.println(line);
                }
            }
        } catch (UnknownHostException u) {
            System.out.println(u);
        } catch (IOException i) {
            System.out.println(i);
        } finally {
            try {
                input.close();
                out.close();
                socket.close();
            } catch (IOException i) {
                System.out.println(i);
            }
        }
    }

    public static void main(String args[]) {
        Client client = new Client("127.0.0.1", 8001);
    }
}
