package data;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CCS {
    public static void main(String[] args) throws IOException {
        Data data = new Data();
        ExecutorService executor = Executors.newCachedThreadPool();

        String port = "9090";

        executor.execute(data);
        executor.execute(new UDPServer(port, data));
        executor.execute(new TCPServer(port, data));
    }
}