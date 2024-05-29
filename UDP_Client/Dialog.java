package UDP_Client;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class Dialog implements Runnable {
    private DatagramSocket ds;
    private DatagramPacket dp;

    public Dialog(DatagramSocket ds, DatagramPacket dp) {
        this.ds = ds;
        this.dp = dp;
    }
    public void run(){
        while(true){
            try{
                ds.receive(dp);
                System.out.println("Message recu: " + new String(dp.getData()));
            }catch(IOException e){
                e.printStackTrace();
            }
    }
    }
        
}
