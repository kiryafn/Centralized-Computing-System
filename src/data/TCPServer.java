package data;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer{
    Data data;
    int port;
    ServerSocket tcp_socket;
    ExecutorService executor = Executors.newCachedThreadPool();

    public TCPServer(String port, Data data) throws IOException{
        this.data = data;
        this.port = Integer.parseInt(port);
        start();
    }

    public void start() throws IOException{
        try{
            tcp_socket = new ServerSocket(port);
            Socket socket = tcp_socket.accept();
            executor.execute(new ClientManager(socket, data));

        }catch (IOException e){

        }
    }
}
