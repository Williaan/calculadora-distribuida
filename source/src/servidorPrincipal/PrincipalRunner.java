package servidorPrincipal;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import base.conn.Conexao;
import base.json.JsonObjectIO;
import base.servidor.TipoServidor;
import servidorPrincipal.conn.PrincipalConexaoZumbi;

public class PrincipalRunner implements Runnable {

	private JsonObjectIO jsonObjectIO = null;
	private Socket socket = null;
	private QueueServer queue = null;

	public PrincipalRunner(Socket socket) throws IOException {
		jsonObjectIO = new JsonObjectIO(socket);
		this.queue = new QueueServer(jsonObjectIO);
		this.socket = socket;
	}

	public void run() {
		try {

			JSONObject clientJson = jsonObjectIO.getJsonObject();
			selecionaOperacao(clientJson);

		} catch (Exception e) {
//			e.printStackTrace();
		}
	}

	/**
	 * Descobre qual o tipo de operacao solicitada pelo cliente
	 * @param obj JSONObject vindo do cliente
	 * @return
	 */
	@SuppressWarnings("unused")
	private TipoServidor getTipoServidor(JSONObject obj) {
		System.out.println("Descobrindo o tipo da operacao...");
		String json = obj.getString("operador");
		return TipoServidor.valueOf(json);

	}

	private void selecionaOperacao(JSONObject obj) throws JSONException, ClassNotFoundException, IOException {
		
		System.out.println("MainRunner -> Descobrindo o tipo da operacao... " + obj);
		String json = obj.getString("operacao");

		switch (json) {
		case "CAD":
			System.out.println("MainRunner -> tipo da operacao: Cadastro");
			cadastrar(obj);
			break;
		default:
			System.out.println("MainRunner -> tipo da operacao: Calculo");
			calcular(obj);
			break;
		}
	}

	/**
	 * Cadastra um norvo servidor
	 * @param obj JSONObject
	 * */
	private void cadastrar(JSONObject obj) {

		int serverPort = obj.getInt("port");
		obj.remove("port");
		String serverAddress = "";
		
		try {			
			serverAddress = (((InetSocketAddress) socket.getRemoteSocketAddress()).getAddress()).toString().replace("/","");
			obj.put("host", serverAddress + ":" + serverPort);
			boolean rt = jsonObjectIO.addNewSlaveJson(obj);
			JSONObject resultJson = new JSONObject();
			resultJson.put("cadastro", rt);
			jsonObjectIO.sendJson(resultJson);
			
//			queue.update();
			
		} catch (JSONException | ParseException e) {
			e.printStackTrace();
		}

		System.out.println("MainRunner -> addrss: " + serverAddress + " port: " + serverPort);
	}

	/**
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws JSONException 
	 * 
	 */
	private void calcular(JSONObject serverJson) throws JSONException, ClassNotFoundException, IOException {

		JSONObject resultJson;

		try {
			
			Conexao con = null;
			String server = serverJson.getString("operacao");
			
			if (!server.equals("CAD")) {				
				String host = null;
				boolean condition = true;
				
				do {
					host = queue.getServer(server);
					System.out.println(host);
					con = new PrincipalConexaoZumbi(server);
					condition = !con.openByJson("");
				} while (host != null && condition);
				
				con.sendJsonToServer(serverJson);
				resultJson = con.getJsonFromServer();
				queue.setServer(server, host);
				jsonObjectIO.sendJson(resultJson);
			}
			
		} catch (JSONException | ClassNotFoundException | IOException e) {
			resultJson = new JSONObject();
			resultJson.put("valor", 0);
			jsonObjectIO.sendJson(resultJson);
			System.out.println("-> Nenhum servidor ativo!");
			e.printStackTrace();
		}

	}

}