package org.psp;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTCP {
    static final int PUERTO = 5000; // Puerto en el que el servidor va a escuchar

    public static void main(String[] args) {
        // ServerSocket nos permite aceptar conexiones TCP
        try (ServerSocket servidor = new ServerSocket(PUERTO)) {
            System.out.println("Servidor TCP iniciado en el puerto " + PUERTO);

            // EL servidor se queda escuchando indefinidamente
            while (true) {
                // accept se bloquea hasta que un cliente se conecta
                Socket cliente = servidor.accept();
                System.out.println("Cliente conectado");

                // Un hilo para atender al cliente
                new ClientHandler(cliente).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}