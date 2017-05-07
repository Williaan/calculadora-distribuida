package servidorZumbi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import org.json.JSONObject;

import base.conn.Conexao;
import base.servidor.TipoServidor;
import base.thread.PoolExecutorRunnable;
import servidor.FabricaServidor;
import servidor.Servidor;
import servidorZumbi.conn.ZumbiConexaoPrincipal;

/**
 * Servidor de operacoes
 * 
 */
public class ZumbiServer implements Runnable {

	private ServerSocket serverSocket = null;
	private Socket socket = null;
	private int serverPort = 0;
	private String serverIp = null;
	private TipoServidor tipoServidor = null;
	private Servidor servidor = null;
	private boolean canRun = true;
	private PoolExecutorRunnable poolExecutor = null;

	public ZumbiServer(TipoServidor tipoServidor)
			throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		this.tipoServidor = tipoServidor;
		this.servidor = FabricaServidor.getServidor(tipoServidor);
		loadProperties();
		cadastro();
	}

	public void run() {
		
		System.out.println("Servidor zumbi " + " online!" + "\n  #Host: " + this.serverIp + " Port: " + this.serverPort);

		while (!Thread.interrupted() && canRun) {
			
			socket = null;

			try {
				
				System.out.println("-> Servidor zumbi: Aguardando conexao...");
				socket = this.serverSocket.accept();

				System.out.println("-> Servidor zumbi: cliente conectado: " + socket.getRemoteSocketAddress());

				ZumbiRunner runner = new ZumbiRunner(socket, servidor);
				poolExecutor = new PoolExecutorRunnable(runner);
				poolExecutor.start();
				
			} catch (Exception e) {
				canRun = false;
				poolExecutor.stop();
				e.printStackTrace();
			}
		}

	}

	private void cadastro() {

		System.out.println("-> Servidor zumbi: cadastrando servidor...");
		JSONObject jsonCadastro = new JSONObject();

		try {
			Conexao conexao = new ZumbiConexaoPrincipal(".\\zumbi.properties");
			conexao.openByProperties();

			jsonCadastro.put("server", tipoServidor);
			jsonCadastro.put("operacao", "CAD");
			jsonCadastro.put("port", this.serverSocket.getLocalPort());

			conexao.sendJsonToServer(jsonCadastro);
			JSONObject clienteJson = conexao.getJsonFromServer();
			System.out.println("-> Servidor zumbi: servidor cadastrado? " + clienteJson.getBoolean("cadastro"));
			conexao.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Carrega as configuracoes do arquivo de propriedades. Se nao existir, cria um novo.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void loadProperties() throws FileNotFoundException, IOException {
		
		String propFileName = ".\\servidorZumbi.properties";
		File file = new File(propFileName);
		InputStream input = null;
		Properties properties = null;		

		if (file.exists()) {

			input = new FileInputStream(file);
			properties = new Properties();
			properties.load(input);

			this.serverPort = Integer.parseInt(properties.getProperty(this.tipoServidor + ".port"));
			this.serverSocket = new ServerSocket(this.serverPort);
			this.serverIp = serverSocket.getInetAddress().getHostAddress();
			
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		} else {
			writeProperties();
		}
	}

	/**
	 * Escreve as configuracoes no arquivo de propriedades
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void writeProperties() throws FileNotFoundException, IOException {

		// gera uma porta automaticamente
		this.serverSocket = new ServerSocket(0);
		this.serverIp = serverSocket.getInetAddress().getHostAddress();

		String propFileName = ".\\servidorZumbi.properties";
		Properties properties = new Properties();
		OutputStream output = new FileOutputStream(propFileName);

		// set the properties value
		properties.setProperty("ESPECIAL.port", this.serverSocket.getLocalPort() + 1 +"");
		properties.setProperty("BASICO.port", this.serverSocket.getLocalPort() + "");

		// save properties to project root folder
		properties.store(output, null);
		
		if (output != null) {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}