import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by varuchin on 23.11.2015.
 */
public class ServerHTTP
{

    private final static int PORT = 1414;
    private final static ExecutorService service = Executors.newFixedThreadPool(10);

    public ServerHTTP() {};

    public void run() throws IOException
    {
        //  serverExecutor = Executors.newSingleThreadExecutor();
        ServerSocket ss = new ServerSocket(PORT);

        while (true) {
            Socket s = ss.accept();
            try {
                final SocketProcessor processor = new SocketProcessor(s);
                service.submit(processor.createResponse);
            } catch (Throwable e) {
            }
        }
    }
}

