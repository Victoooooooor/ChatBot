import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

public class Process implements Runnable {
    private DatagramSocket ds;
    Boolean flag = true;
    private ArrayList<Integer> sockets;
    private DatagramPacket dp;

    public Process(DatagramSocket ds, DatagramPacket dp, ArrayList<Integer> sockets) {
        this.ds = ds;
        this.sockets = sockets;
        this.dp = dp;
    }

    public void run() {
        try {
            //Récupérer le texte du message envoyé
            String message = new String(dp.getData(), 0, dp.getLength());
            System.out.println("[Server] Messagre reçu :"
                    + message + " par " +
                    dp.getAddress() + " " + dp.getPort());

            if (!sockets.contains(dp.getPort())) {
                //Ajouter le client à la liste s'il n'y est pas encore
                sockets.add(dp.getPort());
                System.out.println("[Server] Connexion établie :" + dp.getAddress() + " " + dp.getPort());
            }

            message = message.toUpperCase();
            byte[] buffer = message.getBytes();

            for (Integer socket : sockets) {
                //On renvoie le message à tous les clients
                DatagramPacket send_dp = new DatagramPacket(buffer, buffer.length, dp.getAddress(), socket);
                ds.send(send_dp);
                System.out.println("[Server] Envoi : " + message + " à " + dp.getAddress() + " " + socket);
            }
            if (message.trim().equals("EXIT")) {
                flag = false;
                ds.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
