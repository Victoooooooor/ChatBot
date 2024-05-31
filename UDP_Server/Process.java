import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Map;

public class Process implements Runnable {
    private DatagramSocket ds;
    private Map<Integer, String> sockets;
    private DatagramPacket dp;

    public Process(DatagramSocket ds, DatagramPacket dp, Map<Integer, String> sockets) {
        this.ds = ds;
        this.sockets = sockets;
        this.dp = dp;
    }

    public void run() {
        // Récupérer le texte du message envoyé
        String message = new String(dp.getData(), 0, dp.getLength());
        System.out.println("[Server] Message reçu : " + message + " par " + dp.getAddress() + " "
                + sockets.get(dp.getPort()) + " " + dp.getPort());

        // Ajouter le client à la liste s'il n'y est pas encore
        if (!sockets.containsKey(dp.getPort())) {
            sockets.put(dp.getPort(), message);
            System.out.println("[Server] Connexion établie : " + dp.getAddress() + " " + dp.getPort());
        } else {
            if (message.contains("/")) {
                messagePrivé(dp);
            } else {
                messageCommun(dp);
            }
        }

    }

    public void messagePrivé(DatagramPacket dp) {
        Boolean pseudoExiste = false;
        String message = new String(dp.getData(), 0, dp.getLength());
        String[] parts = message.split("/");
        String messagePort = "MP : [" + sockets.get(dp.getPort()) + "]: " + parts[1];
        byte[] buffer = messagePort.getBytes();
        String pseudo = parts[0];
        for (int socket : sockets.keySet()) {
            try {
                if (pseudo.equals(sockets.get(socket))) {
                    pseudoExiste = true;
                    DatagramPacket send_dp = new DatagramPacket(buffer, buffer.length, dp.getAddress(), socket);
                    ds.send(send_dp);
                    System.out.println("[Server] Envoi : " + parts[1] + " à " + dp.getAddress() + " "
                            + sockets.get(socket) + " " + socket);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!pseudoExiste) {
            System.out.println("[Server] Pseudo inexistant");
        }
    }

    public void messageCommun(DatagramPacket dp) {
        String message = new String(dp.getData(), 0, dp.getLength());
        String messagePort = "[" + sockets.get(dp.getPort()) + "]: " + message;
        byte[] buffer = messagePort.getBytes();

        for (int socket : sockets.keySet()) {
            // On renvoie le message à tous les clients sauf à celui qui l'a envoyé
            try {
                if (socket == dp.getPort()) {
                    continue;
                }
                DatagramPacket send_dp = new DatagramPacket(buffer, buffer.length, dp.getAddress(), socket);
                ds.send(send_dp);
                System.out.println("[Server] Envoi : " + message + " à " + dp.getAddress() + " " + sockets.get(socket)
                        + " " + socket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
