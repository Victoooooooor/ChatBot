package UDP_Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ClientReceiveProcess implements Runnable {
    private DatagramSocket ds;

    public ClientReceiveProcess(DatagramSocket ds) {
        this.ds = ds;
    }

    public void run() {
        while (true) {
            try {
                DatagramPacket dp = new DatagramPacket(new byte[1024], 1024);
                ds.receive(dp);
                System.out.println("Message recu: " + new String(dp.getData(), 0, dp.getLength()));
                System.out.print("Enter a message:\n ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
