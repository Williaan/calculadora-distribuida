package servidor;

import base.servidor.TipoServidor;

/** 
 * Fabrica de servidores 
 */  
public class FabricaServidor {  
  
    /** 
     * Fabrica a classe que vai realizar o calculo de acordo com o tipo da operacao 
     * @param tipoServidor Tipo de servidor de operacao solicitado (BASICO/ESPECIAL) 
     * @return rt servidor
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */  
    @SuppressWarnings("unchecked")  
    public static Servidor getServidor(TipoServidor tipoServidor) throws ClassNotFoundException, InstantiationException, IllegalAccessException {  
        
    	Servidor rt = null;  
          
        Class<Servidor> clazz = (Class<Servidor>) Class.forName(tipoServidor.getClassName());  
          
        rt =  clazz.newInstance();  
        
        return rt;  
    }  
}  