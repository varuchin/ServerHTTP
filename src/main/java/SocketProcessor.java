import java.io.*;
import java.net.Socket;


public class SocketProcessor implements Runnable {

    private Socket s;
    private InputStream is;
    private OutputStream os;

    @Override
    public void run() {

        try {
            //Thread.sleep(1000);
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
    }



    public SocketProcessor(Socket s) throws IOException {
        this.s = s;
        this.is = s.getInputStream();
        this.os = s.getOutputStream();
    }


    private void writeResponse(String s) throws Throwable {
        StringBuilder ResponseBuilder = new StringBuilder();

        ResponseBuilder.append("HTTP/1.1 200 OK\n");
        ResponseBuilder.append("Server: varuchinServer/2015-11-16\n");
        ResponseBuilder.append("Content-Type: text/html\n");
        ResponseBuilder.append("Content-Length: " + s.length() + "\n");
        ResponseBuilder.append("Connection: close\n\n");
//camel case

        String response = ResponseBuilder.toString();

        StringBuilder ResultBuilder = new StringBuilder();
        ResultBuilder.append(response);
        ResultBuilder.append(s);
        String result = ResultBuilder.toString(); // переписать на StrBuilder


        os.write(result.getBytes());
        os.flush();
    }


    private void readInputHeaders() throws Throwable {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while (true) {
            String s = br.readLine();
            if (s == null || s.trim().length() == 0) {
                break;
            }
        }
    }
}
