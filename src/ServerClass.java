import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

class ServerShutdownHandler implements Runnable{
    private volatile ServerClass servClass;

    ServerShutdownHandler(ServerClass servClass) {
        this.servClass = servClass;
        new Thread(this).start();
    }

    @Override
    public void run() {
        while(!servClass.ss.isClosed() &&
                (servClass.newServerFlag || servClass.retClients() != 0));
        try {
            servClass.ss.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}

public class ServerClass implements Runnable {
    private Screen sc;
    private int port = 6666;
    private volatile int noOfClients = 0;
    ServerSocket ss;
    boolean newServerFlag = true;
    private char EnableAutoShutDown;

    ServerClass (Screen sc, int port, char EnableAutoShutDown) {
        this.sc = sc;
        this.port = port;
        this.EnableAutoShutDown = EnableAutoShutDown;
        new Thread(this, "Server").start();
    }

    ServerClass (Screen sc) {
        this.sc = sc;
        new Thread(this, "Server").start();
    }

    private void incClients() {
        noOfClients++;
    }

    void decClients() {
        noOfClients--;
    }

    int retClients() {
        return noOfClients;
    }

    @Override
    public void run() {
        try {
            ss = new ServerSocket(port);
        } catch(IOException e) { sc.cerr("Can't create server."); return; }
        if (EnableAutoShutDown == 'Y')
            new ServerShutdownHandler(this);
        sc.cout("Server online.\n");
        while(!ss.isClosed()) {
            try {
                Socket s = ss.accept();
                new ClientClass(s, sc, this);
            } catch (SocketException e) {
                sc.cout("***All participants left. Shutting down the server***");
                break;
            }
            catch (IOException e) {
                sc.cerr("Cannot establish connection.");
            }
            incClients();
            newServerFlag = false;
        }
    }
}