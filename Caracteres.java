package cifrado;

public class Caracteres {
  //Variable
  private static String caracteres = " ªº\\!1|\"2@·3#$4%5&6¬/7(8)9=0?'¿¡^`[*+]¨´{Ç}<>;,:._-qwertyuiopasdfghjklñzxcvbnmQWERTYUIOPASDFGHJKLÑZXCVBNMáéíóúÁÉÍÓÚàèìòùÀÈÌÒÙäëïöüÄËÏÖÜâêîôûÂÊÎÔÛ";
  private String caracter;
  private int valor;
  
  /**
   * Constructor
   * @param orden Número de posición
   */
  public Caracteres(int orden) {
    this.caracter = caracteres.substring(orden, orden+1);
    this.valor = orden;
  }
  
  /**
   * 
   * 
   * @param nuevoValor
   */
  void setValor(int nuevoValor) {
    this.valor = nuevoValor;
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
  
  
  
  
  static int getLongitud() {
    return caracteres.length();
  }
  
  
  public String toString() {
    return this.caracter + " número: " + Integer.toString(this.valor);
  }
  
  
}
