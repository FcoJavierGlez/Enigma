package enigma.cypher;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Clase Cypher está encargada de los procesos de cifrado/descifrado del texto de entrada
 * bien desde un archivo importado o de un cuadro de texto.
 * 
 * @author Francisco Javier González Sabariego
 *
 */
public class Cypher {
  
  private static ArrayList<Llave> llaves = new ArrayList<Llave>();   //Tabla de caracteres con valores aleatorios.
  private static String salida;
  private Llave llaveSeleccionada;
  
  
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
   * Importa una llave ya creada.
   * 
   * @throws versionLlaveIncorrecta 
   * @throws IOException 
   */
  public void importaLlave(String ruta) throws IOException, versionLlaveIncorrecta {
    llaves.add(new Llave(ruta));
  }
  
  /**
   * Selecciona una llave de la lista
   * 
   * @param numLlave  Posición actual de la llave en la lista de llaves almacenadas.
   * @throws ErrorSeleccionLlave 
   */
  public void seleccionaLlave(int numLlave) throws ErrorSeleccionLlave {
    if (numLlave<1 || numLlave>llaves.size())
      throw new ErrorSeleccionLlave();
    llaveSeleccionada=llaves.get(numLlave-1);
  }
  
  /**
   * Elimina una llave de la lista de llaves almacenadas.
   * 
   * @param numLlave  Posición actual de la llave en la lista de llaves almacenadas.
   */
  public void eliminaLlave(int numLlave) {
    if (llaves.get(numLlave-1)==llaveSeleccionada)
      llaveSeleccionada=null;
    llaves.remove(numLlave-1);
  }
  
  /**
   * @throws ErrorSeleccionLlave 
   * 
   */
  private void compruebaLlave() throws ErrorSeleccionLlave{
    if (llaveSeleccionada==null)
      throw new ErrorSeleccionLlave();
  }
  
  /**
   * 
   * Encripta la línea pasada por parámetro
   * 
   * @param numLlave Número de llave (int) seleccionada
   * @param linea    Línea (String) a cifrar.
   * @return         Devuelve (String) cifrado.
   * 
   * @throws ErrorSeleccionLlave 
   */
  public String encripta(String linea) throws ErrorSeleccionLlave{
    compruebaLlave();
    salida = "";
    for (int i=0; i<linea.length();i++) {
      salida = salidaCifrada(salida, asignaValorCifrado(linea, i));
    }
    return transformaCadena(salida);
  }

  /**
   * 
   * Encripta el texto almacenado en la Clase Texto.
   * 
   * @param numLlave Número de llave (int) seleccionada
   * 
   * @throws ErrorSeleccionLlave 
   */
  public void encriptaTexto() throws ErrorSeleccionLlave{
    compruebaLlave();
    Texto.borraSalida();
    for (int i=0; i<Texto.getTamannoEntrada(); i++) { //Selecciona una línea de entrada de Texto
      salida = "";
      for (int j=0; j<Texto.getLineaEntrada(i).length();j++)  //Encripta todos los caracteres de la línea
        salida = salidaCifrada(salida, asignaValorCifrado(Texto.getLineaEntrada(i), j));
      Texto.addSalida(transformaCadena(salida));
    }
  }

  /**
   * Concatena y devuelve el conjunto actual de los caracteres cifrados.
   * 
   * @param numLlave        Número de llave (int) seleccionada
   * @param salida          Valor actual de la variable salida (String) antes de concatenarle otro caracter cifrado.
   * @param valorCifrado    Valor del caracter cifrado a concatenar a la variable salida.
   * @return                Devuelve la versión actualizada, con el nuevo caracter concatenado, de la variable salida (String).
   */
  private String salidaCifrada(String salida, int valorCifrado) {
    return salida += llaveSeleccionada.getCaracterCifrado(ajustaValorCifrado(valorCifrado));
  }


  /**
   * Si el valor cifrado del caracter rebasa el índice de la tabla
   * se ajusta su valor a la posición correcta para poder encontrar
   * el caracter cifrado.
   * 
   * @param valorCifrado Resultado de establecer el valor del caracter cifrado
   * @return  Valor del caracter cifrado
   */
  private int ajustaValorCifrado(int valorCifrado) {
    if (valorCifrado>=(llaveSeleccionada.getTamanoTablaOriginal())) 
      return valorCifrado %= (llaveSeleccionada.getTamanoTablaOriginal());
    if (valorCifrado<0)
      return llaveSeleccionada.getTamanoTablaOriginal() - Math.abs(valorCifrado);
    return valorCifrado;
  }

  /**
   * Asigna un valor cifrado correspondiente al caracter de la posición "i".
   * 
   * @param        numLlave Número de llave (int) seleccionada
   * @param linea  Línea (String) que se desea cifrar
   * @param i      Variable de control que selecciona la posición del caracter dentro de la línea a cifrar.
   * @return       Devuelve el valor del caracter cifrado
   */
  private int asignaValorCifrado(String linea, int i) {
    return (i%2==0) ? (buscaValorOriginal(linea.substring(i, i+1)) + llaveSeleccionada.getDesplazamiento(i)) : (buscaValorOriginal(linea.substring(i, i+1)) - llaveSeleccionada.getDesplazamiento(i));
  }
  
  
  /**
   * Desencripta la línea pasada por parámetro
   * 
   * @param numLlave Número de llave (int) seleccionada
   * @param linea
   * @return
   * 
   * @throws ErrorSeleccionLlave 
   * 
   */
  public String desencripta(String linea) throws ErrorSeleccionLlave {
    compruebaLlave();
    salida = "";
    for (int i=0; i<linea.length();i++) {
      salida = salidaDescifrada(salida, asignaValorDescifrado(transformaCadena(linea), i));
    }
    return salida;
  }
  
  /**
   * Desencripta el texto almacenado en la Clase Texto.
   * 
   * @param numLlave Número de llave (int) seleccionada
   * 
   * @throws ErrorSeleccionLlave 
   */
  public void desencriptaTexto() throws ErrorSeleccionLlave {
    compruebaLlave();
    Texto.borraSalida();
    for (int i=0; i<Texto.getTamannoEntrada(); i++) { //Selecciona una línea de entrada de Texto
      salida = "";
      for (int j=0; j<Texto.getLineaEntrada(i).length();j++) //Desencripta todos los caracteres de la línea
        salida = salidaDescifrada(salida, asignaValorDescifrado(transformaCadena(Texto.getLineaEntrada(i)), j));
      Texto.addSalida(salida);
    }
  }

  /**
   * Concatena y devuelve el conjunto actual de los caracteres descifrados.
   * 
   * @param numLlave         Número de llave (int) seleccionada
   * @param salida           Valor actual de la variable salida (String) antes de concatenarle otro caracter descifrado.
   * @param valorDescifrado  Valor del caracter descifrado a concatenar a la variable salida.
   * @return                 Devuelve la versión actualizada, con el nuevo caracter concatenado, de la variable salida (String).
   */
  private String salidaDescifrada(String salida, int valorDescifrado) {
    return salida += buscaCaracterOriginal(ajustaValorDescifrado(valorDescifrado));
  }


  /**
   * Si el valor descifrado del caracter rebasa el índice de la tabla
   * se ajusta su valor a la posición correcta para poder encontrar
   * el caracter descifrado.
   * 
   * @param valorDescifrado Resultado de establecer el valor del caracter original
   * @return  Valor del caracter original
   */
  private int ajustaValorDescifrado(int valorDescifrado) {
    if (valorDescifrado>=llaveSeleccionada.getTamanoTablaOriginal())
      return valorDescifrado %= llaveSeleccionada.getTamanoTablaOriginal();
    if (valorDescifrado<0)
      return llaveSeleccionada.getTamanoTablaOriginal() - Math.abs(valorDescifrado);
    return valorDescifrado;
  }

  /**
   * Asigna un valor descifrado correspondiente al caracter de la posición "i".
   * 
   * @param numLlave Número de llave (int) seleccionada
   * @param linea    Línea (String) que se desea descifrar
   * @param i        Variable de control que selecciona la posición del caracter dentro de la línea a descifrar.
   * @return         Devuelve el valor del caracter descifrado
   */
  private int asignaValorDescifrado(String linea, int i) {
    return (i%2==0) ? (buscaValorCifrado(linea.substring(i, i+1)) - llaveSeleccionada.getDesplazamiento(i)) : 
      (buscaValorCifrado(linea.substring(i, i+1)) + llaveSeleccionada.getDesplazamiento(i));
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
  private static String invertirCadena(String linea) { 
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
  private int buscaValorOriginal(String caracter) {
    for (int i=0; i<llaveSeleccionada.getTamanoTablaOriginal();i++) {
      if (caracter.equals(llaveSeleccionada.getCaracterOriginal(i)))
        return llaveSeleccionada.getValorOriginal(i);
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
  private String buscaCaracterOriginal(int valor) {
    String salida = "";
    for (int i=0; i<llaveSeleccionada.getTamanoTablaOriginal();i++) {
      if (valor==llaveSeleccionada.getValorOriginal(i)) {
        salida = llaveSeleccionada.getCaracterOriginal(valor);        
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
  private int buscaValorCifrado(String caracter) {
    int indice = llaveSeleccionada.comparaCaracterCifrado(caracter);
    return llaveSeleccionada.getValorCifrado(indice);
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
