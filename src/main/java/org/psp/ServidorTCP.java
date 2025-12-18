package org.psp;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCP {
    static final int PUERTO = 5000;

    public static void main(String[] args) {
        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            System.out.println("Servidor TCP iniciado en el puerto " + PUERTO);

            while (true) {
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado");

                // Un hilo por cliente
                new ClientHandler(cliente).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}