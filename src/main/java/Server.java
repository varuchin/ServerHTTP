public class Server
{

    public static void main(String[] args)
    {
        try {
            ServerHTTP server = new ServerHTTP();
            server.run();
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
}



