package udp;

import data.Data;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer implements Runnable{
    int port;
    Data data;
    private static final byte[] BYTE_BUFFER = new byte[1024];
    DatagramSocket udp_socket;

    public UDPServer(String port, Data data){
        this.data = data;
        this.port = Integer.parseInt(port);
        System.out.println("started udp");
    }

    @Override
    public void run() {
        try {
            this.udp_socket = new DatagramSocket(port);
        } catch (IllegalArgumentException | SocketException e) {
            e.printStackTrace();
            return;
        }
        try  {
            while (true) {
                DatagramPacket packet = new DatagramPacket(BYTE_BUFFER, BYTE_BUFFER.length);
                udp_socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                //data.incrementConnectedClients();

                if (received.startsWith("data.CCS DISCOVER")) {
                    String acceptMessage = "data.CCS FOUND";
                    InetAddress address = packet.getAddress();
                    int senderPort = packet.getPort();
                    DatagramPacket acceptPacket = new DatagramPacket(
                            acceptMessage.getBytes(),
                            acceptMessage.length(),
                            address,
                            senderPort
                    );
                    udp_socket.send(acceptPacket);
                    //data.incrementComputedRequests();
                }
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
