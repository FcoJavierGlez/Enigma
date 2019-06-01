package enigma.cypher;

public class Caracteres {
  //Variable
  private static final String CARACTERES = " ªº°\\!1|\"2@·3#$4%5&6¬/7(8)9=0?'¿¡^`[*+]¨´{çÇ}±<>;,:._-«»qwe€rtyuiopaæsdfghjklñzxc¢vbnmQWER®TY¥UIOPAÆS§DÐFGHJKL£ÑZXC©VBNMáéíóúÁÉÍÓÚàèìòùÀÈÌÒÙäëïöüÄËÏÖÜâêîôûÂÊÎÔÛåÅ";
  private String caracter;
  private int valor;
  
  
  //#################################     CONSTRUCTOR     #################################\\
  
  /**
   * Constructor
   * 
   * @param orden Número de posición
   */
  public Caracteres(int orden) {
    this.caracter = CARACTERES.substring(orden, orden+1);
    this.valor = orden;
  }
  
  //#################################     SETTERS   #################################\\
  
  
  /**
   * Asigna un nuevo valor al caracter.
   * 
   * @param nuevoValor  Nuevo valor (int).
   */
  void setValor(int nuevoValor) {
    this.valor = nuevoValor;
  }
  
  
  //#################################     GETTERS     #################################\\
  
  /**
   * Devuelve la posición del caracter pasado como parámetro dentro de la
   * constante CARACTERES.
   * 
   * @param caracter
   * @return
   */
  public int getPosicionCaracter(String caracter) {
    return CARACTERES.indexOf(caracter);
  }
  
  /**
   * Devuelve el caracter.
   * 
   * @return Devuelve (String) caracter.
   */
  public String getCaracter() {
    return this.caracter;
  }
  
  /**
   * Devuelve el valor del caracter.
   * 
   * @return Valor (int) del caracter.
   */
  public int getValor() {
    return this.valor;
  }
  
  
  /**
   * Devuelve la longitud de la variable de clase caracteres.
   * 
   * @return Devuelve la longitud (int) de la variable caracteres
   */
  static int getLongitud() {
    return CARACTERES.length();
  }
  
  
  //#################################     TO STRING     #################################\\
  
  //Para realizar pruebas:  
  /**
   * Método toString() 
   */
  /*
  public String toString() {
    return this.caracter + " número: " + Integer.toString(this.valor);
  }
  */
  
}
