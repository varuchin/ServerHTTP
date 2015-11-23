import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by varuchin on 23.11.2015.
 */
public class Server {

    public static void main(String[] args) throws Throwable
    {

        ServerSocket ss = new ServerSocket(1313);
        System.out.println("asf");
/**
 while(true)
 {
 Socket s = ss.accept();
 System.err.println("Client accepted.");
 new Thread(new SocketProcessor(s).run).run();
 }
 **/
    }
    /*
    * переписать с импользование thread pool*/
    private static class SocketProcessor implements Runnable
    {

        private Socket s;
        private InputStream is;
        private OutputStream os;

        private SocketProcessor(Socket s) throws Throwable
        {
            this.s = s;
            this.is = s.getInputStream();
            this.os = s.getOutputStream();
        }


        @Override
       public void run()
        {
            try
            {
                Thread.sleep(10000);

                //readInputHeaders();
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
            }
            catch (Throwable t)
            {
                t.printStackTrace();
            }
            finally {
                try
                {
                    s.close();
                }
                catch (Throwable t)
                {
                    t.printStackTrace();
                }
            }
            System.err.println("Client processing finished.");
        };

        private void writeResponse(String s) throws Throwable
        {
            String response = "HTTP/1.1 200 OK\r\n" +
                    "Server: varuchinServer/2015-11-16\r\n" +
                    "Content-Type: text/html\n" +
                    "Content-Length: " + s.length() + "\r\n" +
                    "Connection: close\r\n\r\n";
            String result = response + s; // переписать на StrBuilder
            os.write(result.getBytes());
            os.flush();
        }


/*
        private void readInputHeaders() throws Throwable {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while (true) {
                String s = br.readLine();
                if (s == null || s.trim().length() == 0) {
                    break;
                }
            }
        }
        */
    }
}