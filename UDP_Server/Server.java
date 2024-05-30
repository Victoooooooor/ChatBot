import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {
        int port_serveur = 3500;

        try {

            var pool = Executors.newFixedThreadPool(10);
            ArrayList<Integer> sockets = new ArrayList<Integer>();

            DatagramSocket ds = new DatagramSocket(port_serveur);
            System.out.println("Serveur démarré sur le port : " + port_serveur);

            Boolean flag = true;
            while (flag) {
                DatagramPacket dp = new DatagramPacket(new byte[1024], 1024);
                ds.receive(dp);
                Process p = new Process(ds, dp, sockets);
                pool.execute(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}