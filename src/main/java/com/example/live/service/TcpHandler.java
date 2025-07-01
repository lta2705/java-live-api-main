package com.example.live.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


@Service
public class TcpHandler {
    static Logger logger = LoggerFactory.getLogger(TcpHandler.class);

    @Value("${tcp.server.port}")
    private int port;

    private ServerSocket serverSocket;

    public TcpHandler(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;

    }

    @PostConstruct
    public void startServer() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port);) {
            logger.info("Server started on port: {}" , port);

            new Thread(() -> {
                try {
                    while (true) {
                        Socket clientSocket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(clientSocket.getInputStream()));

                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            logger.info("Received: {}" , inputLine);
                        }

                        in.close();
                        clientSocket.close();
                        logger.warn("Client disconnected");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }catch (Exception e) {
            logger.error("Error starting TCP server: {}", e.getMessage());
        }
    }
}
