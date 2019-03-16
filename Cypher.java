package cifrado;

import java.util.ArrayList;

/**
 * 
 * 
 * @author Francisco Javier González Sabariego
 *
 */
public class Cypher {
  
  private static ArrayList<Llave> llaves = new ArrayList<Llave>();                   //Tabla de caracteres con valores aleatorios.
  
  
  /**
   * Constructor
   */
  public Cypher() {}
  
  
  /**
   * Crea una nueva llave.
   */
  void generaLlave() {
    llaves.add(new Llave());
  }
  
  
  /**
   * Muestra el número de llaves almacenadas en Cypher.
   * 
   * @return  Número (int) de llaves almacenadas.
   */
  public int getNumeroLlaves() {
    return llaves.size();
  }
  
  
  /**
   * 
   * Encripta la línea pasada por parámetro
   * 
   * @param linea   Línea (String) a cifrar.
   * @return        Devuelve (String) cifrado.
   */
  String encripta(String linea) {
    String salida = "";
    int valorCifrado;
    
    for (int i=0; i<linea.length();i++) {
      valorCifrado = asignaValorCifrado(linea, i);
      salida = salidaCifrada(salida, valorCifrado);
    }
    
    salida = transformaCadena(salida);
    
    return salida;
  }

  /**
   * Concatena y devuelve el conjunto actual de los caracteres cifrados.
   * 
   * @param salida          Valor actual de la variable salida (String) antes de concatenarle otro caracter cifrado.
   * @param valorCifrado    Valor del caracter cifrado a concatenar a la variable salida.
   * @return                Devuelve la versión actualizada, con el nuevo caracter concatenado, de la variable salida (String).
   */
  private String salidaCifrada(String salida, int valorCifrado) {
    if (valorCifrado>=(llaves.get(0)).getTamanoTablaOriginal()) {
      valorCifrado %= (llaves.get(0)).getTamanoTablaOriginal();
      salida += (llaves.get(0)).getCaracterCifrado(valorCifrado);
    } else if (valorCifrado<0) {
      valorCifrado = (llaves.get(0)).getTamanoTablaOriginal() - Math.abs(valorCifrado);
      salida += (llaves.get(0)).getCaracterCifrado(valorCifrado);
    } else {
      salida += (llaves.get(0)).getCaracterCifrado(valorCifrado);
    }
    return salida;
  }

  /**
   * Asigna un valor cifrado correspondiente al caracter de la posición "i".
   * 
   * @param linea  Línea (String) que se desea cifrar
   * @param i      Variable de control que selecciona la posición del caracter dentro de la línea a cifrar.
   * @return       Devuelve el valor del caracter cifrado
   */
  private int asignaValorCifrado(String linea, int i) {
    int valorCifrado;
    int valor1;
    int valor2;
    valor1 = buscaValorOriginal(linea.substring(i, i+1));
    valor2 = (llaves.get(0)).getDesplazamiento(i);
    if (i%2==0) {
      valorCifrado = valor1 + valor2;        
    } else {
      valorCifrado = valor1 - valor2;
    }
    return valorCifrado;
  }
  
  
  /**
   * 
   * Desencripta la línea pasada por parámetro
   * 
   * @param linea
   * @return
   */
  String desencripta(String linea) {
    String salida = "";
    int valorDescifrado = 0;
    
    linea = transformaCadena(linea);
    
    for (int i=0; i<linea.length();i++) {
      valorDescifrado = asignaValorDescifrado(linea, i);
      salida = salidaDescifrada(salida, valorDescifrado);
    }
    
    return salida;
  }

  /**
   * Concatena y devuelve el conjunto actual de los caracteres descifrados.
   * 
   * @param salida           Valor actual de la variable salida (String) antes de concatenarle otro caracter descifrado.
   * @param valorDescifrado  Valor del caracter descifrado a concatenar a la variable salida.
   * @return                 Devuelve la versión actualizada, con el nuevo caracter concatenado, de la variable salida (String).
   */
  private String salidaDescifrada(String salida, int valorDescifrado) {
    if (valorDescifrado>=(llaves.get(0)).getTamanoTablaOriginal()) {
      valorDescifrado %= (llaves.get(0)).getTamanoTablaOriginal();
      salida += buscaCaracterOriginal(valorDescifrado);
    } else if (valorDescifrado<0) {
      valorDescifrado = (llaves.get(0)).getTamanoTablaOriginal() - Math.abs(valorDescifrado);
      salida += buscaCaracterOriginal(valorDescifrado);
    } else {
      salida += buscaCaracterOriginal(valorDescifrado);
    }
    return salida;
  }

  /**
   * Asigna un valor descifrado correspondiente al caracter de la posición "i".
   * 
   * @param linea  Línea (String) que se desea descifrar
   * @param i      Variable de control que selecciona la posición del caracter dentro de la línea a descifrar.
   * @return       Devuelve el valor del caracter descifrado
   */
  private int asignaValorDescifrado(String linea, int i) {
    int valorDescifrado;
    int valor1;
    int valor2;
    valor1 = buscaValorCifrado(linea.substring(i, i+1));
    valor2 = (llaves.get(0)).getDesplazamiento(i);
    if (i%2==0) {
      valorDescifrado = valor1 - valor2;        
    } else {
      valorDescifrado = valor1 + valor2;
    }
    return valorDescifrado;
  }
  
  
  
  /**
   * Cambia las mayúsculas a minúsculas, y viceversa, de la línea que recibe por parámetro
   * 
   * @param linea Línea (String) a modificar
   * @return      Línea (String) modificada
   */
  private static String cambiaMayusMinus(String linea) {
    String salida = "";
    
    for (int i=0; i<linea.length();i++) {
      if (linea.substring(i, i+1).equals(linea.substring(i, i+1).toUpperCase())) {
        salida += linea.substring(i, i+1).toLowerCase();
      } else {
        salida += linea.substring(i, i+1).toUpperCase();
      }
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
    String salida="";
    
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
    String salida = "";
    
    salida = cambiaMayusMinus(linea);
    salida = invertirCadena(salida);
    return salida;
  }
  
  
  /**
   * 
   * Busca el valor del caracter de la tabla original comparándolo con el caracter que le pasamos.
   * 
   * @param caracter
   * @return
   */
  private int buscaValorOriginal(String caracter) {
    int resultado = 0;
    for (int i=0; i<(llaves.get(0)).getTamanoTablaOriginal();i++) {
      if (caracter.equals((llaves.get(0)).getCaracterOriginal(i))) {
        resultado = (llaves.get(0)).getValorOriginal(i);
        break;
      }
    }
    return resultado;
  }
  
  
  /**
   * Busca el caracter de la tabla original a partir del valor del caracter.
   * 
   * @param valor
   * @return
   */
  private String buscaCaracterOriginal(int valor) {
    String salida = "";
    for (int i=0; i<(llaves.get(0)).getTamanoTablaOriginal();i++) {
      if (valor==(llaves.get(0)).getValorOriginal(i)) {
        salida = (llaves.get(0)).getCaracterOriginal(valor);        
        break;
      }
    }
    return salida;
  }
  
  
  /**
   * Devuelve el valor del caracter cifrado buscado.
   * 
   * @param caracter
   * @return
   */
  private int buscaValorCifrado(String caracter) {
    int indice = 0;
//    for (int i=0; i<tablaOriginal.size();i++) {
//      if (caracter.equals((llaves.get(0)).getCaracterCifrado(i))) {
//        resultado = (llaves.get(0)).getValorCifrado(i);
//        //resultado = i;
//        break;
//      }
//    }
    indice = (llaves.get(0)).comparaCaracterCifrado(caracter);
    return (llaves.get(0)).getValorCifrado(indice);
  }
  
  
  /**
   * 
   * 
   * @return
   */
  public String toString() {
    String salida = "";
    for (int i=0; i<llaves.size(); i++) {
      salida += (llaves.get(i)).toString() + "\n"; 
    }
    return salida;
  }
}
