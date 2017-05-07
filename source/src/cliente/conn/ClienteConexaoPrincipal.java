package cliente.conn;

import java.io.FileNotFoundException;
import java.io.IOException;

import base.conn.Conexao;

/** 
* Classe Responsável pela conexao com o servidor principal 
*/  
public class ClienteConexaoPrincipal extends Conexao {  
 
	public ClienteConexaoPrincipal(String configFileName) throws FileNotFoundException, IOException {  
		super(configFileName);  
	}  
 
	@Override  
	public void configure() {  
		this.serverAddress = properties.getProperty("servidorPrincipal.address");  
		this.serverPort = Integer.parseInt(properties.getProperty("servidorPrincipal.port"));  
	}  
 
}