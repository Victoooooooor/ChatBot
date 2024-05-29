package UDP_Client;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;


public class Client {
    public static void main(String[] args) throws IOException {
        try {
            var mes="connexion";
            byte[] buf = mes.getBytes();


            DatagramSocket ds = new DatagramSocket();
            DatagramPacket dp = new DatagramPacket(new byte[1024], 1024);
            var pool = Executors.newFixedThreadPool(10);
            Scanner sc = new Scanner(System.in);
            Boolean flag = true;
            Dialog diag= new Dialog(ds, dp);
            dp = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), 5000);
            ds.send(dp);
            

            while (flag) {
                pool.execute(diag);
                System.out.print("Enter a message:\n ");
                String message = sc.nextLine();
                byte[] buffer = message.getBytes();
                System.out.println(new String(buffer));
                

                dp = new DatagramPacket(buffer, buffer.length, InetAddress.getLocalHost(), 5000);
                
                // if(message=="") {
                //     ds.receive(dp);
                // }
                // else{
                //     ds.send(dp);
                //     ds.receive(dp);
                // }
                ds.send(dp);
        
                System.out.print("\n Server: " + new String(dp.getData()));
                if (message.trim().equals("EXIT")) {
                    flag = false;
                    ds.close();
                }
            } 
            
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
    }
}

