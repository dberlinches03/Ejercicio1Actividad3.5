package org.psp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClienteTCP {
    public static void main(String[] args) {
        String servidor = "localhost";
        int puerto = 5080; // El puerto del servidor

        // El cliente manda la respuesta al servidor
        try (
                Socket socket = new Socket(servidor, puerto);
                BufferedReader entrada = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter salida = new PrintWriter(
                        socket.getOutputStream(), true);
                Scanner teclado = new Scanner(System.in)
        ) {
            String mensajeServidor;

            while ((mensajeServidor = entrada.readLine()) != null) {
                System.out.println("Servidor: " + mensajeServidor);

                if (mensajeServidor.contains("¿Quieres jugar")) {
                    String respuesta = teclado.nextLine();
                    salida.println(respuesta);

                    if (!respuesta.equalsIgnoreCase("si")) {
                        break;
                    }
                } else {
                    String numero = teclado.nextLine();
                    salida.println(numero);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
