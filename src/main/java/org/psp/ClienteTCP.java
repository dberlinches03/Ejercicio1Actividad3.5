package org.psp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTCP {
    public static void main(String[] args) {
        String servidor = "localhost"; // Direccion del servidor
        int puerto = 5000; // El puerto del servidor

        // El cliente manda la respuesta al servidor
        try (
                // Socket que conecta con el servidor
                Socket socket = new Socket(servidor, puerto);

                // Flujo de entrada para recibir mensajes del servidor
                BufferedReader entrada = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));

                // Flujo de salida para enviar mensajes al servidor
                PrintWriter salida = new PrintWriter(
                        socket.getOutputStream(), true);

                // Scanner para leer datos desde teclado
                Scanner teclado = new Scanner(System.in)
        ) {
            String mensajeServidor;

            // Bucle que recibe mensajes del servidor
            while ((mensajeServidor = entrada.readLine()) != null) {

                // Muestra el mensaje del servidor
                System.out.println("Servidor: " + mensajeServidor);

                // Si el servidor pregunta si quiere volver a jugar
                if (mensajeServidor.contains("¿Quieres jugar")) {
                    String respuesta = teclado.nextLine();
                    salida.println(respuesta);

                    // Si el usuario no quiere seguir, se sale
                    if (!respuesta.equalsIgnoreCase("si")) {
                        break;
                    }
                } else {
                    // El servidor espera un número
                    String numero = teclado.nextLine();
                    salida.println(numero);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
