import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;


public class ClientClass implements Runnable {
    private Socket s;
    private String clientName = "UnknownClient";
    private Screen sc;
    private ServerClass servClass;


    ClientClass(Socket s, Screen sc, ServerClass servClass) {
        this.s = s;
        this.sc = sc;
        this.servClass = servClass;
        new Thread(this).start();
    }

    @Override
    public void run() {
        String str;
        try {
            DataInputStream din = new DataInputStream(s.getInputStream());
            str = din.readUTF();
            clientName = str;
            sc.cout("***" + clientName + " is now online***");
            Thread.currentThread().setName(clientName);
            while (true) {
                str = din.readUTF();
                if (str.equals("KillTransmission"))
                    break;

                /*else if (str.equals("SitRep")) {                            //
                    sc.cout(servClass.retClients());                         //For Debugging purposes only.
                    sc.cout(((Boolean)servClass.newServerFlag).toString()); //
                }*/

                else
                    sc.cout(clientName + " : " + str);
            }

            //Disconnect
            do {
                din.close();
                s.close();
            } while (!s.isClosed());
            sc.cout("***" + clientName + " left the session***");
            servClass.decClients();
        } catch (IOException e) { e.printStackTrace(); }
    }
}