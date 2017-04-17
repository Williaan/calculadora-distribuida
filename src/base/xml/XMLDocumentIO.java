package base.xml;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Socket;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

/** 
 * Responsavel pelo recebimento e envio de documentos XML 
 */  
public class XMLDocumentIO {  
  
    private Socket   socket  = null;        
      
    public XMLDocumentIO(Socket socket) throws IOException {  
        this.socket = socket;  
    }  
      
    /** 
     * Envia o documento XML atraves do socket 
     * @param doc Documento XML a ser enviado 
     */  
    public void sendXMLDocument(Document doc) throws IOException {  
        System.out.println("Enviando documento XML...");  
        XMLOutputter out = new XMLOutputter();  
        BufferedOutputStream bufferOut = new BufferedOutputStream(this.socket.getOutputStream());  
        out.output(doc, bufferOut);  
        bufferOut.flush();  
    }  
      
      
    /** 
     * Le o documento XML enviado pelo socket 
     * @return Documento XML lido do servidor principal 
     */  
    public Document getXMLDocument() throws IOException, JDOMException {  
        System.out.println("Lendo documento XML...");  
        byte[] bytes = new byte[1024];  
        BufferedInputStream    buffer = new BufferedInputStream(this.socket.getInputStream());  
          
        int total =  buffer.read(bytes);  
          
        byte[] bytes1 = new byte[total];  
          
        for (int i = 0; i < total; i++) {  
            bytes1[i] = bytes[i];  
        }  
         
        ByteArrayInputStream arrayInput = new ByteArrayInputStream(bytes1);  
        SAXBuilder sax = new SAXBuilder();  
        Document doc = sax.build(arrayInput);  
        return doc;  
    }  
}  
