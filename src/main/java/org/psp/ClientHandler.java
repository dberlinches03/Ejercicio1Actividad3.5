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

    ClientHandler(Socket socket) {
        this.socket = socket;
        this.random = new Random();
    }

    // Funcion run con la logica para el adivinar el numero secreto y las pistas
    @Override
    public void run() {
        // Meto dentro de try-with resources tanto el Buffered Reader como el PrintWriter ya que tienen AutoCloseable
        try (
                BufferedReader entrada = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter salida = new PrintWriter(
                        socket.getOutputStream(), true)
        ) {
            boolean jugar = true;

            // Bucle del juego
            while (jugar) {
                int numeroSecreto = random.nextInt(100) + 1;
                salida.println("He pensado un número entre 1 y 100");

                boolean acertado = false;

                while (!acertado) {
                    String mensaje = entrada.readLine();

                    if (mensaje == null) {
                        return;
                    }

                    int numero = Integer.parseInt(mensaje);

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
