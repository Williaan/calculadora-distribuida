package base.operator;

/** 
 * Define os tipos de operacao existentes 
 */  
public enum TipoOperador {  
    ADD("operador.AddOperator"),  
    SUB("operador.SubOperator"),  
    MUL("operador.MulOperator"),  
    DIV("operador.DivOperator"),
	SQRT("operador.SqrtOperator"),
	POR("operador.PorOperator");
      
    private String className = null;  
      
      
    private TipoOperador(String className) {  
        this.className = className;      
    }  
      
      
    public String getClassName() {  
        return this.className;  
    }  
}  