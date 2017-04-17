package client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;

import base.conn.ServerConnection;
import base.operator.TipoOperador;
import client.conn.Connection;

public class OperadorCliente {  
  
   private List<Double> params = null;  
   private TipoOperador tipoOperador = null;  
     
   private ServerConnection serverConnection = null;  
     
   public OperadorCliente(List<Double> params, TipoOperador tipoOperador) {  
      this.params = params;  
      this.tipoOperador = tipoOperador;  
   }  
     
   /** 
    * La o resultado da operacao 
    * @return Valor da operacao  
    */  
   public Double getValue() throws FileNotFoundException, IOException, JDOMException {  
        
      if (!isParamsValid()) {  
         throw new IllegalArgumentException("Parâmetros inválidos!");  
      }  
        
      serverConnection = new Connection(".\\client.properties");  
      serverConnection.open();  
      Document serverDoc = params2XMLDocument();  
        
      serverConnection.sendDocumentToServer(serverDoc);  
      Document clientDoc = serverConnection.getDocumentFromServer();  
      Double returnValue = getValueFromDoc(clientDoc);  
      return returnValue;  
   }  
     
   /** 
    * La os parametros e gera o documento XML que sera enviado ao servidor  
    * principal 
    * @return Documento XML, baseado nos parametros e no tipo da operacao 
    */  
   private Document params2XMLDocument() {  
        
      Document doc = new Document();  
      Element root = new Element("operation");  
        
      for (Double paramValue: params) {  
         Element param = new Element("param");  
         param.addContent(paramValue.toString());  
         root.addContent(param);  
      }  
      Element type = new Element("type");  
      type.addContent(tipoOperador.toString());  
      root.addContent(type);  
      doc.setRootElement(root);  
        
        
      return doc;  
   }  
     
   /** 
    * Verifica se e possível realizar o calculo com os argumentos 
    * @return verdadeiro se for possível realizar o cálculo, falso caso contrário  
    */  
   private boolean isParamsValid() {  
      return this.params != null && this.params.size() > 0   
          && this.tipoOperador != null;  
   }  
     
     
   /** 
    * Le o valor (resultado) que veio do documento XML do servidor principal 
    * @param doc Documento XML que veio do servidor principal 
    * @return 
    */  
   private Double getValueFromDoc(Document doc) {  
      Element root = doc.getRootElement();  
      Element value = root.getChild("value");  
      Double returnValue = Double.parseDouble(value.getValue());  
      return returnValue;  
   }  
} 