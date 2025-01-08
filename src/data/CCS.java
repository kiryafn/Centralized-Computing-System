package data;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CCS {
    public static void main(String[] args) throws IOException {
        Data data = new Data();
        new TCPServer(args[0], data);
        new UDPServer(args[0], data);
    }
}
