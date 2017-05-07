package servidor;

import org.json.JSONObject;

/** 
 * Interface que sera implementada pelos servidores de operacao (BASICO e ESPECIAL) 
 */  
public interface Servidor {  
      
    /** 
     * Seta o Json que sera usado no calculo 
     * @param json JSONObject com os parametros 
     */  
    void setJsonObject(JSONObject json);  
      
    /** 
     * Efetua o calculo e retorna o Json com os parametros 
     * @return JSONObject produzido com o resultado da operacao 
     */ 
    JSONObject getTargetJson();
      
    /** 
     * Retorna o nome do servidor que esta sendo usado 
     * @return Uma String contendo o nome do servidor que esta sendo usado 
     */  
    String getNomeServidor();  
}  