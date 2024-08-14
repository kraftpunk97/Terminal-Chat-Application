import java.util.Scanner;

public class ServerMain {
    public static void main(String args[]) {
        Screen s = new Screen();
        Scanner sc = new Scanner(System.in);
        System.out.println("This system has been chosen as the designated server host.");
        System.out.print("Select a free port for communication: ");
        int port = sc.nextInt();
        sc.nextLine();
        System.out.println("\nYou have selected port number " + port + " as the port for all communications. Please share " +
            "this system's IP Address and port number with the clients.");
        System.out.print("\nDo you want the server to automatically shutdown after all participants leave the session (Yy/Nn)?");
        char EnableAutoShutDown = sc.nextLine().charAt(0);
        new ServerClass(s, port, EnableAutoShutDown);
    }
}