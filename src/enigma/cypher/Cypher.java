package enigma.cypher;

import java.util.ArrayList;

/**
 * Clase Cypher está encargada de los procesos de cifrado/descifrado del texto de entrada
 * bien desde un archivo importado o de un cuadro de texto.
 * 
 * @author Francisco Javier González Sabariego
 *
 */
public class Cypher {
  
  private static ArrayList<Llave> llaves = new ArrayList<Llave>();                   //Tabla de caracteres con valores aleatorios.
  private static String salida;
  
  
  //#################################     CONSTRUCTORES   #################################\\  
  
  /**
   * Constructor
   */
  public Cypher() {}
  
  
  //#################################     GETTERS   #################################\\
  
  /**
   * Muestra el número de llaves almacenadas en Cypher.
   * 
   * @return  Número (int) de llaves almacenadas.
   */
  public int getNumeroLlaves() {
    return llaves.size();
  }
  
  
  //#################################     MÉTODOS   #################################\\
  
  /**
   * Crea una nueva llave.
   */
  public void generaLlave() {
    llaves.add(new Llave());
  }
  
  
  /**
   * 
   * Encripta la línea pasada por parámetro
   * 
   * @param numLlave Número de llave (int) seleccionada
   * @param linea    Línea (String) a cifrar.
   * @return         Devuelve (String) cifrado.
   */
  public String encripta(int numLlave, String linea) {
    salida = "";
    for (int i=0; i<linea.length();i++) {
      salida = salidaCifrada(numLlave, salida, asignaValorCifrado(numLlave, linea, i));
    }
    return transformaCadena(salida);
  }

  /**
   * Concatena y devuelve el conjunto actual de los caracteres cifrados.
   * 
   * @param numLlave        Número de llave (int) seleccionada
   * @param salida          Valor actual de la variable salida (String) antes de concatenarle otro caracter cifrado.
   * @param valorCifrado    Valor del caracter cifrado a concatenar a la variable salida.
   * @return                Devuelve la versión actualizada, con el nuevo caracter concatenado, de la variable salida (String).
   */
  private String salidaCifrada(int numLlave, String salida, int valorCifrado) {
    if (valorCifrado>=(llaves.get(numLlave-1)).getTamanoTablaOriginal()) 
      valorCifrado %= (llaves.get(numLlave-1)).getTamanoTablaOriginal();
    if (valorCifrado<0)
      valorCifrado = (llaves.get(numLlave-1)).getTamanoTablaOriginal() - Math.abs(valorCifrado);
    salida += (llaves.get(numLlave-1)).getCaracterCifrado(valorCifrado);
    return salida;
  }

  /**
   * Asigna un valor cifrado correspondiente al caracter de la posición "i".
   * 
   * @param        numLlave Número de llave (int) seleccionada
   * @param linea  Línea (String) que se desea cifrar
   * @param i      Variable de control que selecciona la posición del caracter dentro de la línea a cifrar.
   * @return       Devuelve el valor del caracter cifrado
   */
  private int asignaValorCifrado(int numLlave, String linea, int i) {
    int valor1 = buscaValorOriginal(numLlave, linea.substring(i, i+1));
    int valor2 = (llaves.get(numLlave-1)).getDesplazamiento(i);
    if (i%2==0)
      return valor1 + valor2;        
    else
      return valor1 - valor2;
  }
  
  
  /**
   * Desencripta la línea pasada por parámetro
   * 
   * @param numLlave Número de llave (int) seleccionada
   * @param linea
   * @return
   */
  public String desencripta(int numLlave, String linea) {
    salida = "";
    for (int i=0; i<linea.length();i++) {
      salida = salidaDescifrada(numLlave, salida, asignaValorDescifrado(numLlave, transformaCadena(linea), i));
    }
    return salida;
  }

  /**
   * Concatena y devuelve el conjunto actual de los caracteres descifrados.
   * 
   * @param numLlave         Número de llave (int) seleccionada
   * @param salida           Valor actual de la variable salida (String) antes de concatenarle otro caracter descifrado.
   * @param valorDescifrado  Valor del caracter descifrado a concatenar a la variable salida.
   * @return                 Devuelve la versión actualizada, con el nuevo caracter concatenado, de la variable salida (String).
   */
  private String salidaDescifrada(int numLlave, String salida, int valorDescifrado) {
    if (valorDescifrado>=(llaves.get(numLlave-1)).getTamanoTablaOriginal())
      valorDescifrado %= (llaves.get(numLlave-1)).getTamanoTablaOriginal();
    if (valorDescifrado<0)
      valorDescifrado = (llaves.get(numLlave-1)).getTamanoTablaOriginal() - Math.abs(valorDescifrado);
    salida += buscaCaracterOriginal(numLlave, valorDescifrado);
    return salida;
  }

  /**
   * Asigna un valor descifrado correspondiente al caracter de la posición "i".
   * 
   * @param numLlave Número de llave (int) seleccionada
   * @param linea    Línea (String) que se desea descifrar
   * @param i        Variable de control que selecciona la posición del caracter dentro de la línea a descifrar.
   * @return         Devuelve el valor del caracter descifrado
   */
  private int asignaValorDescifrado(int numLlave, String linea, int i) {
    int valor1 = buscaValorCifrado(numLlave, linea.substring(i, i+1));
    int valor2 = (llaves.get(numLlave-1)).getDesplazamiento(i);
    if (i%2==0) 
      return valor1 - valor2;        
    else 
      return valor1 + valor2;
  }
  
  
  
  /**
   * Cambia las mayúsculas a minúsculas, y viceversa, de la línea que recibe por parámetro
   * 
   * @param linea Línea (String) a modificar
   * @return      Línea (String) modificada
   */
  private static String cambiaMayusMinus(String linea) {
    salida = "";
    for (int i=0; i<linea.length();i++) {
      if (linea.substring(i, i+1).equals(linea.substring(i, i+1).toUpperCase())) 
        salida += linea.substring(i, i+1).toLowerCase();
      else 
        salida += linea.substring(i, i+1).toUpperCase();
    }
    return salida;
  }
  
  
  /** 
   * Invierte la línea que recibe por parámetro
   * 
   * @param linea  Línea (String) a invertir
   * @return       Línea (String) invertida
   */
  private static String invertirCadena(String linea) {     //Invertimos el orden de la cadena
    salida="";
    for (int i=linea.length()-1; i>-1; i--) {     //Leemos la cadena y almacenamos los caracteres en variable salida
      salida+=linea.charAt(i);
    }
    return salida;
  }
  
  
  /**
   * Cambia las mayúsculas a minúsculas, y viceversa, e invierte la línea que recibe por parámetro.
   * 
   * @param linea   Línea (String) a modificar
   * @return        Línea modificada
   */
  private static String transformaCadena(String linea) {
    return invertirCadena(cambiaMayusMinus(linea));
  }
  
  
  /**
   * Busca el valor del caracter de la tabla original comparándolo con el caracter que le pasamos.
   * 
   * @param numLlave Número de llave (int) seleccionada
   * @param caracter Caracter (String) que deseamos comparar
   * @return         Devuelve el valor (int) del caracter encontrado en la tabla original
   */
  private int buscaValorOriginal(int numLlave, String caracter) {
    for (int i=0; i<(llaves.get(numLlave-1)).getTamanoTablaOriginal();i++) {
      if (caracter.equals((llaves.get(numLlave-1)).getCaracterOriginal(i)))
        return (llaves.get(numLlave-1)).getValorOriginal(i);
    }
    return 0;
  }
  
  
  /**
   * Busca el caracter de la tabla original a partir del valor del caracter.
   * 
   * @param        numLlave Número de llave (int) seleccionada
   * @param valor  Valor (int) del caracter que estamos buscando 
   * @return       Devuelve el caracter (String) encontrado en la tabla original
   */
  private String buscaCaracterOriginal(int numLlave, int valor) {
    String salida = "";
    for (int i=0; i<(llaves.get(numLlave-1)).getTamanoTablaOriginal();i++) {
      if (valor==(llaves.get(numLlave-1)).getValorOriginal(i)) {
        salida = (llaves.get(numLlave-1)).getCaracterOriginal(valor);        
        break;
      }
    }
    return salida;
  }
  
  
  /**
   * Devuelve el valor del caracter cifrado buscado.
   * 
   * @param numLlave Número de llave (int) seleccionada
   * @param caracter Caracter (String) que deseamos comparar
   * @return         Devuelve el valor (int) del caracter encontrado en la tabla cifrada
   */
  private int buscaValorCifrado(int numLlave, String caracter) {
    int indice = (llaves.get(numLlave-1)).comparaCaracterCifrado(caracter);
    return (llaves.get(numLlave-1)).getValorCifrado(indice);
  }
  
  
  //#################################     TO STRING   #################################\\  
  
  /**
   * 
   * 
   * @return
   */
  public String toString() {
    salida = "";
    for (int i=0; i<llaves.size(); i++) {
      salida += "Llave " + (i+1) + ":\n" + (llaves.get(i)).toString() + "\n\n"; 
    }
    return salida;
  }
  
  //####################################   AUX   ##############################################\\
  
  
  public void imprimeTablas(int numLlave) {
    for (int i=0; i<Caracteres.getLongitud();i++) {
      System.out.print("Posición " + i + ": " + (llaves.get(numLlave-1)).getCaracterOriginal(i) + " valor original: " + (llaves.get(numLlave-1)).getValorOriginal(i));
      System.out.print("     ||     " + (llaves.get(numLlave-1)).getCaracterPosCifrado(i) + " valor cifrado: " + (llaves.get(numLlave-1)).getValorCifrado(i) + "\n");
    }
    
    System.out.println("Longitud tabla original: " + (llaves.get(numLlave-1)).getTamanoTablaOriginal() + "total caracteres: " + Caracteres.getLongitud());
    
    for (int i=0; i<(llaves.get(numLlave-1)).getLongitudDesplazamiento();i++) {
      System.out.print("\nPosición " + i + ": " + (llaves.get(numLlave-1)).getDesplazamiento(i));
    }
  }
  
  
}
