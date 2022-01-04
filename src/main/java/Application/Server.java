package Application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable
{
	private ServerSocket server;
	private ExecutorService executor;
	public Server()
	{
		executor = Executors.newCachedThreadPool();
	}
	public void startServer() {
		try {
			server = new ServerSocket(8000);
			Thread t = new Thread(this);
			t.start();
		} catch (IOException e) {e.printStackTrace();}
	}
	@Override
	public void run() 
	{
		while (!Thread.currentThread().isInterrupted()) 
		{			
			executor.submit(DbConnection.getInstance());
		}
	}

}
