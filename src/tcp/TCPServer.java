package tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer implements Runnable{
    Data data;
    int port;
    ServerSocket tcp_socket;
    ExecutorService executor = Executors.newCachedThreadPool();

    public TCPServer(String port, Data data) throws IOException{
        this.data = data;
        this.port = Integer.parseInt(port);
        System.out.println("started tcp");
    }

    @Override
    public void run()
    {
        try{
            tcp_socket = new ServerSocket(port);
            while (true){
                Socket socket = tcp_socket.accept();
                data.incrementConnectedClients();
                executor.execute(new ClientManager(socket, data));
            }


        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
