import java.io.*;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Client {
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private Scanner scanner;

    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("localhost", 8080);
            new Client(socket);
        } catch (IOException e) {
            e.printStackTrace();
            try {
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.exit(0);
        }
    }

    public Client(Socket socket) {
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Welcome\n1. Create Account\n2. Login\n3. Exit");
                switch (getChoice(1, 3)) {
                    case -1:
                        System.out.println("Cannot return from the first menu.");
                        break;
                    case 1:
                        System.out.println("-------------------");
                        createAccount();

                        break;
                    case 2:
                        System.out.println("-------------------");
                        login();
                        break;
                    case 3:
                        System.exit(0);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int getChoice(int min, int max) {
        int choice;
        while (true) {
            try {
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    private void createAccount() {
        try {
            String email;
            while (true) {
                System.out.println("Enter your Email (Enter B to return):");
                email = scanner.nextLine();
                if (email.equalsIgnoreCase("b")) {
                    return;
                } else if (isValidEmail(email)) {
                    break;
                } else {
                    System.out.println("Invalid email. Please try again.");
                }
            }

            String password;
            while (true) {
                System.out.println("Enter your Password (including at least a letter and a number) (Enter B to return):");
                password = scanner.nextLine();
                if (password.equalsIgnoreCase("b")) {
                    return;
                } else if (isValidPassword(password)) {
                    break;
                } else {
                    System.out.println("Invalid password. Please try again.");
                }
            }

            String username;
            while (true) {
                System.out.println("Enter your Username:");
                username = scanner.nextLine();
                if (username.length() > 0) {
                    break;
                } else {
                    System.out.println("Invalid username. Please try again.");
                }
            }

            String phoneNumber;
            while (true) {
                System.out.println("Enter your Phone Number:");
                phoneNumber = scanner.nextLine();
                if (phoneNumber.length() > 0) {
                    break;
                } else {
                    System.out.println("Invalid phone number. Please try again.");
                }
            }

            String bio;
            while (true) {
                System.out.println("Enter your Bio:");
                bio = scanner.nextLine();
                if (bio.length() > 0) {
                    break;
                } else {
                    System.out.println("Invalid bio. Please try again.");
                }
            }

            Date birthDate;
            while (true) {
                System.out.println("Enter your Birth Date (DD/MM/YYYY):");
                String birthDateStr = scanner.nextLine();
                try {
                    birthDate = new SimpleDateFormat("dd/MM/yyyy").parse(birthDateStr);
                    break;
                } catch (ParseException e) {
                    System.out.println("Invalid birth date. Please try again.");
                }
            }

            User user = new User(email, password, username, phoneNumber, bio, birthDate);
            objectOutputStream.writeObject("createAccount");
            objectOutputStream.writeObject(user);
            System.out.println("Account created successfully. Please login to continue.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void login() {
        try {
            String email;
            while (true) {
                System.out.println("Enter your Email:");
                email = scanner.nextLine();
                if (isValidEmail(email)) {
                    break;
                } else {
                    System.out.println("Invalid email. Please try again.");
                }
            }

            String password;
            while (true) {
                System.out.println("Enter your Password:");
                password = scanner.nextLine();
                if (isValidPassword(password)) {
                    break;
                } else {
                    System.out.println("Invalid password. Please try again.");
                }
            }

            // Send login request to the server
            objectOutputStream.writeObject("login");
            objectOutputStream.writeObject(email);
            objectOutputStream.writeObject(password);

            // Receive response from the server
            boolean loginSuccessful = (boolean) objectInputStream.readObject();
            System.out.println(loginSuccessful);
            String message;
            if (loginSuccessful) {
                message = (String) objectInputStream.readObject();
            } else {
                message = (String) objectInputStream.readObject();
            }
            System.out.println(message);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
        return Pattern.matches(emailPattern, email);
    }

    private boolean isValidPassword(String password) {
        String passwordPattern = ".*[a-zA-Z].*[0-9].*";
        return Pattern.matches(passwordPattern, password);
    }
}
