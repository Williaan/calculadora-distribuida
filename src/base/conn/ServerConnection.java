package base.conn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;

import org.jdom.Document;
import org.jdom.JDOMException;

import base.xml.XMLDocumentIO;

/** 
 * Responsavel pela conexao com os servidores  
 */  
public abstract class ServerConnection {  
  
   protected Socket socket = null;  
   protected int serverPort = 0;  
   protected String serverAddress = null;  
   protected Properties properties = null;  
   protected String configFileName = null;  
   protected XMLDocumentIO xmlDocumentIO = null;  
     
   /** 
    * Cria uma conexao baseada no arquivo de configuracao 
    * @param configFileName Arquivo de configuracao para a conexao 
    */  
   protected ServerConnection(String configFileName) throws FileNotFoundException, IOException {  
      this.configFileName = configFileName;  
      loadProperties();  
   }  
     
   /** 
    * Carrega as propriedades do arquivo de configuracao 
    */  
   private void loadProperties() throws FileNotFoundException, IOException {  
      File        file         = null;  
      FileReader  reader       = null;  
      file         = new File(this.configFileName);   
      reader       = new FileReader(file);  
      properties   = new Properties();  
      properties.load(reader);  
   }     
     
   /** 
    * Método que configura a conexão 
    */  
   public abstract void configure();  
     
   /** 
    * Abre a conexao com o servidor   
    */  
   public final void open() throws UnknownHostException, IOException {  
      configure();  
      System.out.println(this.serverAddress + " - " + this.serverPort);  
      this.socket = new Socket(this.serverAddress, this.serverPort);  
      this.xmlDocumentIO = new XMLDocumentIO(this.socket);  
   }  
     
   /** 
    * Retorna o Stream de entrada da conexao 
    * @return InputStream da conexao  
    */  
   public InputStream getInputStream() throws IOException {  
      return this.socket.getInputStream();  
   }  
     
   /** 
    * Retorna o Stream de saida da conexao 
    * @return OutputStream da conexao 
    */  
   public OutputStream getOutputStream() throws IOException {  
      return this.socket.getOutputStream();  
   }  
     
   public void sendDocumentToServer(Document doc) throws IOException {  
      this.xmlDocumentIO.sendXMLDocument(doc);  
   }  
     
   public Document getDocumentFromServer() throws IOException, JDOMException {  
      return this.xmlDocumentIO.getXMLDocument();  
   }  
}  
