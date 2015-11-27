import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ServerHTTP {

    private final static int PORT = 1414;
    private final ExecutorService service = Executors.newCachedThreadPool();

    public ServerHTTP() {
    }


    public void run() throws IOException {
        //  serverExecutor = Executors.newSingleThreadExecutor();
        ServerSocket ss = new ServerSocket(PORT);

        while (true) {
            Socket s = ss.accept();
            SocketProcessor processor = new SocketProcessor(s);
            service.submit(processor);
        }
    }
}

