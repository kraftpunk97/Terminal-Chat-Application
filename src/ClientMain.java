import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientMain {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        String message;
        String hostIP;
        int port;
        System.out.print("Enter host server's IP address (Use \"localhost\" for localhost) : ");
        hostIP = sc.nextLine();
        System.out.print("Enter host server's port for incoming messages: ");
        port = sc.nextInt();
        sc.nextLine();
        try ( Socket s = new Socket(hostIP, port);
              DataOutputStream dout = new DataOutputStream(s.getOutputStream()) ) {

            System.out.println("\nConnection Established.");

            System.out.print("\nEnter your name: ");
            String name = sc.nextLine();
            System.out.println("Enter \"KillTransmission\" to exit session.\n");
            dout.writeUTF(name);
            dout.flush();
            do {
                System.out.print("Enter message: ");
                message = sc.nextLine();
                dout.writeUTF(message);
                dout.flush();
            } while (!message.equals("KillTransmission"));
        } catch (IOException e) { System.err.println("***Unable to transmit message***"); }
    }
}