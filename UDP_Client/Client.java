package UDP_Client;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class Client {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("\nEntrez votre nom :");
            var name=sc.nextLine();
            byte[] buf = name.getBytes();

            DatagramSocket ds = new DatagramSocket();
            DatagramPacket dp;
            ExecutorService pool = Executors.newFixedThreadPool(10);
            ClientReceiveProcess proc = new ClientReceiveProcess(ds);

            dp = new DatagramPacket(buf, buf.length, InetAddress.getByName("192.168.240.216"), 3500);
            System.out.println("Connexion au serveur...");
            ds.send(dp);
            System.out.println("Connnecté au serveur ! ");
            pool.execute(proc);

            while (true) {
                System.out.print("Enter a message:\n");
                String message = sc.nextLine();
                byte[] buffer = message.getBytes();

                dp = new DatagramPacket(buffer, buffer.length, InetAddress.getByName("192.168.240.216"), 3500);

                ds.send(dp);
            }

        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
