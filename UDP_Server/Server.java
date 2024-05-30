import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {
        int port_serveur = 3500;

        try {
            var pool = Executors.newFixedThreadPool(10);
            Map<Integer,String> portPseudo = new HashMap<>();

            DatagramSocket ds = new DatagramSocket(port_serveur);
            System.out.println("Serveur démarré sur le port : " + port_serveur);

            while (true) {
                DatagramPacket dp = new DatagramPacket(new byte[1024], 1024);
                ds.receive(dp);
                Process p = new Process(ds, dp, portPseudo);
                pool.execute(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}