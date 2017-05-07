package servidorPrincipal.conn;

import java.io.FileNotFoundException;
import java.io.IOException;

import base.conn.Conexao;

/**
 * Responsavel pela conexao com os servidores de operacao
 */
public class PrincipalConexaoZumbi extends Conexao {

	private String server;

	public PrincipalConexaoZumbi(String server) throws FileNotFoundException, IOException {
		this.server = server;
	}

	@Override
	public void configure() {
		String host[] = server.split(":");
		this.serverAddress = host[0];
		this.serverPort = Integer.parseInt(host[1]);
		System.out.println("OperatorServerConnection -> serverAddress: " 
				+ this.serverAddress + " serverPort: " + this.serverPort);
	}
}