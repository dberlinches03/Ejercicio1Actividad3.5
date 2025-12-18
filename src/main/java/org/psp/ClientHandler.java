package org.psp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class ClientHandler extends Thread {
    private Socket socket;
    private Random random;

    // Constructor del hilo
    ClientHandler(Socket socket) {
        this.socket = socket;
        this.random = new Random();
    }

    // Metodo run con la logica para el adivinar el numero secreto y las pistas
    @Override
    public void run() {
        // Meto dentro de try-with resources tanto el Buffered Reader como el PrintWriter ya que tienen AutoCloseable
        try (
                // Flujo de entrada para recibir mensajes del cliente
                BufferedReader entrada = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                // Flujo de salida para enviar mensajes al cliente
                PrintWriter salida = new PrintWriter(
                        socket.getOutputStream(), true)
        ) {
            // Variable para controlar si el usuario quiere seguir jugando
            boolean jugar = true;

            // Bucle del juego
            while (jugar) {
                // Genera un número aleatorio entre 1 y 100
                int numeroSecreto = random.nextInt(100) + 1;

                // Mensaje inicial al cliente
                salida.println("He pensado un número entre 1 y 100");

                boolean acertado = false;

                // Bucle hasta que el usuario acierte el numero
                while (!acertado) {
                    String mensaje = entrada.readLine();

                    // Si el cliente se desconecta se termina el hilo
                    if (mensaje == null) {
                        return;
                    }

                    // Se convierte el mensaje a entero
                    int numero = Integer.parseInt(mensaje);

                    // Comparación con el número secreto
                    if (numero < numeroSecreto) {
                        salida.println("El número es mayor");
                    } else if (numero > numeroSecreto) {
                        salida.println("El número es menor");
                    } else {
                        salida.println("Número correcto");
                        acertado = true;
                    }
                }

                // Pregunta al usuario si quiere volver a jugar
                salida.println("¿Quieres jugar otra vez? (si/no)");
                String respuesta = entrada.readLine();

                // Si no responde "si", se sale del juego
                if (!respuesta.equalsIgnoreCase("si")) {
                    jugar = false;
                }
            }

            // Cierro el socket cuando haya terminado de usarlo
            socket.close();
            System.out.println("Cliente desconectado");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
