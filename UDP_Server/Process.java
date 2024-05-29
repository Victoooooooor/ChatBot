import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

public class Process implements Runnable {
    private DatagramSocket ds;
    private DatagramPacket dp;
    Boolean flag = true;
    private ArrayList<Integer> sockets;
    
    public Process(DatagramSocket ds, DatagramPacket dp, ArrayList<Integer> sockets) {
        this.ds = ds;
        this.dp = dp;
        this.sockets = sockets;
    }

    public void run(){
        try {
            ds.receive(dp);
            String message= new String(dp.getData());
            if(!sockets.contains(dp.getPort())) {
                sockets.add(dp.getPort());
                System.out.println("message : " + dp.getPort() + dp.getAddress());
                System.out.println("connexion établie :" + message);
            }

            message = message.toUpperCase();
            byte[] buffer = message.getBytes();
            dp = new DatagramPacket(buffer, dp.getLength(), dp.getAddress(), dp.getPort());
            for (Integer socket : sockets) {
                
                DatagramPacket dps = new DatagramPacket(buffer, buffer.length, dp.getAddress(), socket);
                ds.send(dps);
                System.out.println("send : "+ message);
                System.out.println(socket);
            }
            if(message.trim().equals("EXIT")) {
                flag = false;
                ds.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
