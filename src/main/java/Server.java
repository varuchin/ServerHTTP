import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by varuchin on 23.11.2015.
 */
public class Server
{

    private final static int PORT = 1414;
    private Executor serverExecutor;

    void run() throws IOException
    {
        serverExecutor = Executors.newSingleThreadExecutor();
        ServerSocket ss = new ServerSocket(PORT);

        while (true)
        {
            Socket s = ss.accept();
            try {
                final SocketProcessor processor = new SocketProcessor(s);
                final ExecutorService executor = Executors.newFixedThreadPool(10);
                executor.submit(processor.createResponse);
            } catch (Throwable e) {
            }
        }
    }

    public static void main(String[] args) throws Throwable
    {
        new Server().run();
/**
 ExecutorService executor = Executors.newCachedThreadPool();


 while(true) {

 try
 {

 ServerSocket ss = new ServerSocket(PORT);
 Socket s = ss.accept();

 Executor executor1 = Executors.newSingleThreadExecutor();
 System.err.println("Client accepted.");
 executor1.execute(new SocketProcessor(s).createResponse);

 }
 catch (Exception e)
 {
 e.printStackTrace();
 }

 //executor.shutdown();
 /**
 Socket s = ss.accept();
 System.err.println("Client accepted.");
 executor.submit(new SocketProcessor(s));
 **/
        // new Thread(new SocketProcessor(s)).run();
    }

}

/*
* ���������� � ������������� thread pool*/
class SocketProcessor
{

    private Socket s;
    private InputStream is;
    private OutputStream os;

    public SocketProcessor(Socket s) throws Throwable {
        this.s = s;
        this.is = s.getInputStream();
        this.os = s.getOutputStream();
    }


    Runnable createResponse = () ->
    {

        try {
            Thread.sleep(1000);
            readInputHeaders();
            writeResponse("<html>\n" +
                    " <head>\n" +
                    "  <title></title>\n" +
                    "<style type=\"text/css\">\n" +
                    ".styletest {\n" +
                    "\tcolor: red; \n" +
                    "\tfont-size:12px; \n" +
                    "\tfont-family:Arial\n" +
                    "}\n" +
                    "</style>\n" +
                    " </head>\n" +
                    "<body>\n" +
                    "<font class=\"styletest\">Hello World from Valery! </font>\n" +
                    "</body>\n" +
                    "</html>");
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                s.close();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        System.err.println("Client processing finished.");
    };


    private void writeResponse(String s) throws Throwable
    {
        StringBuilder Responsebuilder = new StringBuilder();

        Responsebuilder.append("HTTP/1.1 200 OK\r\n");
        Responsebuilder.append("Server: varuchinServer/2015-11-16\r\n");
        Responsebuilder.append("Content-Type: text/html\n");
        Responsebuilder.append("Content-Length: " + s.length() + "\r\n");
        Responsebuilder.append("Connection: close\r\n\r\n");

        String response = Responsebuilder.toString();

        StringBuilder builder1 = new StringBuilder();
        builder1.append(response);
        builder1.append(s);
        String result = builder1.toString(); // ���������� �� StrBuilder


        os.write(result.getBytes());
        os.flush();
    }


    private void readInputHeaders() throws Throwable
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while (true) {
            String s = br.readLine();
            if (s == null || s.trim().length() == 0) {
                break;
            }
        }
    }
}
