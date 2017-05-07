package servidorPrincipal;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import base.thread.PoolExecutorRunnable;

public class PrincipalServer implements Runnable {

	private ServerSocket serverSocket = null;
	private boolean canRun = true;
	private PoolExecutorRunnable poolExecutor = null;

	public PrincipalServer(int serverPort) throws IOException {
		this.serverSocket = new ServerSocket(serverPort);
	}

	public void run() {
		
		System.out.println("-> Servidor principal pronto para uso! "
				+ "\n  # Host: "+this.serverSocket.getInetAddress().getHostAddress()
				+ " Port: " + this.serverSocket.getLocalPort() + " (porta atual).");
		
		while (!Thread.interrupted() && canRun) {
			Socket socket = null;
			try {
				System.out.println("-> Servidor principal: Aguardando conexao...");
				socket = serverSocket.accept();

				System.out.println("-> Servidor principal: Cliente conectado: " + socket.getRemoteSocketAddress());
				Runnable runner = new PrincipalRunner(socket);
				poolExecutor = new PoolExecutorRunnable(runner);
				poolExecutor.start();
				
			} catch (Exception e) {
				poolExecutor.stop();
				e.printStackTrace();
				canRun = false;
			}
		}
	}
}