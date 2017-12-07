import java.net.*;
import java.io.*;

public class STSServer 
{

    protected ServerSocket STSSocket = null;
    public StockMarket mySM;

	//below is the implementation of the singleton for this class
    private static STSServer instance;
    private STSServer(){}   
    public static STSServer getInstance(){
        if(instance == null){
            instance = new STSServer();
        }
        return instance;
    }
	//end of implementation of singleton

    public void initSTS()
    {
        System.out.println("StockMarket thread started.");
        Thread t1 = new Thread(mySM.getStockMarket());
        t1.start();
    }


    public void listenForClients()
    {
        try
        {
            STSSocket = new ServerSocket(5000);
            
            while(true)
            {
                System.out.println("Listening for connections from Client.\n");
                new ClientConnect(STSSocket.accept(), mySM.getStockMarket());
            }
        }
        catch(IOException e)
        {
            System.out.println("Error in setting up socket " + e);
            System.exit(1);
        }
    }

    public static void main(String [] args)
    {
        STSServer mySTS = STSServer.getInstance();
        mySTS.initSTS();
        mySTS.listenForClients();
    }
}