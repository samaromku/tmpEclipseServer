package network;
import org.apache.log4j.Logger;

import entities.ClientEntity;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;



public class Server {
    public static final int PORT = 60123;
    private ServerSocket serverSocket;
    private volatile ArrayList<ClientEntity> clients;
    private ClientEntity clientEntity;
    public ArrayList<ClientEntity> getClients() {
        return clients;
    }
    private Logger log = Logger.getLogger(Server.class.getName());

    public ClientEntity getClientEntity() {
        return clientEntity;
    }

    public void start() {
        clients = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(PORT);
            log.info("сервер запущен");
            while (true) {
                Socket client = serverSocket.accept();
                clientEntity = new ClientEntity(client, this);
                clients.add(clientEntity);
                log.info("Клиент присоединился");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void remove(ClientEntity client) {
        clients.remove(client);
        log.info(client.getClientName() + " отсоединился");
    }
}
