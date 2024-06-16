import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket serverSocket;
    private List<ClientHandler> clients;

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        try {
            serverSocket = new ServerSocket(8080);
            clients = new ArrayList<>();

            while (true) {
                System.out.println("Waiting for clients...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket);

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class ClientHandler extends Thread {
        private Socket clientSocket;
        private ObjectOutputStream objectOutputStream;
        private ObjectInputStream objectInputStream;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String request = (String) objectInputStream.readObject();
//                    System.out.println(request);
                    if (request.equals("createAccount")) {
                        User user = (User) objectInputStream.readObject();
                        User.users.add(user);
                        // Process create account request
                        // Add your implementation here
                    } else if (request.equals("login")) {
                        String email = (String) objectInputStream.readObject();
                        String password = (String) objectInputStream.readObject();
                        System.out.println(email + " " + password);
//                        objectOutputStream.writeObject(true);
                        User user = User.login(email, password);
                        if (user == null) {
                            objectOutputStream.writeObject(false);
                            objectOutputStream.writeObject("invalid email or password");
                        } else {
                            objectOutputStream.writeObject(true);
                            objectOutputStream.writeObject("login successful");
                        }
                        // Process login request
                        // Add your implementation here
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    objectOutputStream.close();
                    objectInputStream.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}