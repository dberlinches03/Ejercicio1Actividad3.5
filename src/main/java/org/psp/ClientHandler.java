package org.psp;

import java.net.Socket;
import java.util.Random;

public class ClientHandler extends Thread {
    private Socket socket;
    private Random random;

    ClientHandler(Socket socket) {
        this.socket = socket;
        this.random = new Random();
    }
}
