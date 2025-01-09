import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CCS {
    public static void main(String[] args) throws IOException {
        Data data = new Data();
        ExecutorService executor = Executors.newCachedThreadPool();

        String port = "9090";

        // Запуск TCP и UDP серверов
        executor.execute(new TCPServer(port, data));
        executor.execute(new UDPServer(port, data));
        executor.execute(data);
    }
}