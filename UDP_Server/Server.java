import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) throws IOException {
    var pool = Executors.newFixedThreadPool(10);
    ArrayList<Integer> sockets = new ArrayList<Integer>();
    
    DatagramSocket ds = new DatagramSocket(5000);
    DatagramPacket dp = new DatagramPacket(new byte[1024], 1024);
    Boolean flag = true;
    while (flag){
        Process p = new Process(ds, dp, sockets);
        pool.execute(p);
    }
        

}
}