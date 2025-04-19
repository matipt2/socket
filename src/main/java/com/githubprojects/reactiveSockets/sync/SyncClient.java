package com.githubprojects.reactiveSockets.sync;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SyncClient {
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    public void startClient(int port) {
        for (int i = 0; i < 5; i++) {
            executorService.submit(() -> {
                Thread currentThread = Thread.currentThread();
                try (Socket clientSocket = new Socket("127.0.0.1", port);
                     PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true)) {
                    int messageCount = 0;
                    while (messageCount < 20) {
                        pw.println("Message from client thread: " + currentThread.getName());
                        messageCount++;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            System.err.println("Client thread interrupted: " + e.getMessage());
                            break;
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Client error: " + e.getMessage());
                }
                finally {
                    stop();
                }
            });
        }
    }

    public void stop() {
        executorService.shutdown();
    }
}