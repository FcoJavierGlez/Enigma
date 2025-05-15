package enigma.cypher;

import enigma.cypher.error.*;

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
  private static String entrada;
  private Llave llaveSeleccionada = null;
  
  
  //#################################     CONSTRUCTORES     #################################\\  
  
  /**
   * Constructor
   */
  public Cypher() {}
  
  
  //#################################     GETTERS     #################################\\
  
  /**
   * Muestra el número de llaves almacenadas en Cypher.
   * 
   * @return  Número (int) de llaves almacenadas.
   */
  public int getNumeroLlaves() {
    return llaves.size();
  }
  
  
  //#################################     GESTIÓN DE LLAVES     #################################\\
  
  /**
   * Crea una nueva llave.
   * 
   * @throws limiteLlavesException  Se lanza esta excepción cuando se pretenda generar o importar una llave teniendo almacenadas 10 llaves.
   */
  public void generaLlave() throws limiteLlavesException {
    limiteLlaves();
    llaves.add(new Llave());
  }
  
  /**
   * Importa una llave ya creada.
   * 
   * @param ruta  Ruta y nombre de donde se va a importar la llave.
   * 
   * @throws versionLlaveIncorrecta Se lanza esta excepción cuando la versión de la llave a importar no coincide con la versión del programa.
   * @throws IOException            Se lanza esta cuando se peuda leer el fichero a importar.
   * @throws cabeceraInvalida       Se lanza esta excepción si al validar la cabecera no se corresponde con la forma correcta.
   * @throws limiteLlavesException  Se lanza esta excepción cuando se pretenda generar o importar una llave teniendo almacenadas 10 llaves.
   * @throws llaveDuplicada         Se lanza esta excepción si encuentra que hay dos llaves iguales.
   */
  public void importaLlave(String ruta) throws IOException, versionLlaveIncorrecta, cabeceraInvalida, limiteLlavesException, llaveDuplicada {
    limiteLlaves();
    llaves.add(new Llave(ruta));
    eliminaLlaveDuplicada();
  }

  /**
   * Comprueba si la llave importada ya existe almacenada. En caso de existir elimina 
   * la llave importada y lanza la excepción como aviso de lo ocurrido.
   * 
   * @throws llaveDuplicada  Se lanza esta excepción si encuentra que hay dos llaves iguales.
   * 
   */
  private void eliminaLlaveDuplicada() throws llaveDuplicada {
    for (int i=0; i<llaves.size()-1; i++) {
      if ((llaves.get(llaves.size()-1)).getNombreLlave().equals((llaves.get(i)).getNombreLlave())) {
        llaves.remove(llaves.size()-1);
        throw new llaveDuplicada();
      }
    }
  }


  /**
   * Comprueba si Cypher tiene almacenadas 10 llaves, en caso de ser así
   * lanza la excepción limiteLlavesException().
   * 
   * @throws limiteLlavesException  Se lanza esta excepción cuando se pretenda generar o importar una llave teniendo almacenadas 10 llaves.
   */
  private void limiteLlaves() throws limiteLlavesException {
    if (llaves.size()==10)
      throw new limiteLlavesException();
  }
  
  /**
   * Exporta una llave ya creada.
   * 
   * @param ruta  Ruta y nombre donde se va a expotar la llave.
   * 
   * @throws ErrorSeleccionLlave  Se lanza esta excepción cuando la llave seleccionada no existe.
   * @throws IOException          Se lanza esta cuando se peuda leer el fichero a importar.
   */
  public void exportaLlave(String ruta) throws ErrorSeleccionLlave, IOException {
    compruebaLlave();
    llaveSeleccionada.exportaLlave(ruta);
  }
  
  /**
   * Selecciona una llave de la lista
   * 
   * @param numLlave  Posición actual de la llave en la lista de llaves almacenadas.
   * 
   * @throws ErrorSeleccionLlave Se lanza esta excepción cuando la llave seleccionada no existe.
   */
  public void seleccionaLlave(int numLlave) throws ErrorSeleccionLlave {
    if ( numLlave < 1 || numLlave > llaves.size() )
      throw new ErrorSeleccionLlave();
    llaveSeleccionada = llaves.get(numLlave-1);
  }
  
  /**
   * Elimina una llave de la lista de llaves almacenadas.
   * 
   * @param numLlave  Posición actual de la llave en la lista de llaves almacenadas.
   */
  public void eliminaLlave(int numLlave) {
    if (llaves.get(numLlave-1) == llaveSeleccionada)
      llaveSeleccionada = null;
    llaves.remove(numLlave - 1);
  }
  
  /**
   * Comprueba si hay alguna llave seleccionada.
   * 
   * @throws ErrorSeleccionLlave Se lanza esta excepción cuando la llave seleccionada no existe.
   */
  private void compruebaLlave() throws ErrorSeleccionLlave {
    if (llaveSeleccionada == null) throw new ErrorSeleccionLlave();
  }
  
  
  //#################################     ENCRIPTAR     #################################\\

  /**
   * 
   * Encripta el texto almacenado en la Clase Texto.
   * 
   * @throws ErrorSeleccionLlave Se lanza esta excepción cuando la llave seleccionada no existe.
   */
  public void encriptaTexto() throws ErrorSeleccionLlave {
    compruebaLlave();
    Texto.borraSalida();
    procesoEncriptado();
  }


  /**
   * Proceso mediante el cual se encripta el texto almacenado
   * en la tabla entradaTexto de la Clase Texto.
   */
  private void procesoEncriptado() {
    for (int i = 0; i < Texto.getTamannoEntrada(); i++) { //Selecciona una línea de entrada de Texto
      salida = "";
      for (int j = 0; j < Texto.getLineaEntrada(i).length(); j++)  //Encripta todos los caracteres de la línea
        salida = salidaCifrada(salida, asignaValorCifrado(Texto.getLineaEntrada(i), j), j);
      Texto.addSalida(transformaCadena(salida));
    }
  }

  /**
   * Concatena y devuelve el conjunto actual de los caracteres cifrados.
   * 
   * @param salida          Valor actual de la variable salida (String) antes de concatenarle otro caracter cifrado.
   * @param valorCifrado    Valor del caracter cifrado a concatenar a la variable salida.
   * @return                Devuelve la versión actualizada, con el nuevo caracter concatenado, de la variable salida (String).
   */
  private String salidaCifrada(String salida, int valorCifrado, int i) {
    return salida += invierteCaseCaracter( llaveSeleccionada.getCaracterCifrado( ajustaValorTabla(valorCifrado) ), i );
  }

  /**
   * Asigna un valor cifrado correspondiente al caracter de la posición "i".
   * 
   * Si "i" = "0" o el valor de operacion[i]!=operacion[i-1] se sigue el siguiente patrón:
   * <ul><li>En caso de que operacion[i]==true se suma el desplazamiento, pero en caso de ser false se resta.</li></ul>
   * Si operacion[i]==operacion[i-1] se invierte el patrón anterior:
   * <ul><li>En caso de que operacion[i]==true se resta el desplazamiento, pero en caso de ser false 0 se suma.</li></ul>
   * 
   * @param linea  Línea (String) que se desea cifrar
   * @param i      Variable de control que selecciona la posición del caracter dentro de la línea a cifrar.
   * @return       Devuelve el valor del caracter cifrado
   */
  private int asignaValorCifrado(String linea, int i) {   //PENDIENTE DE TESTEAR, AL FINAL DEL CÓDIGO ESTÁ EL MÉTODO ANTERIOR A ÉSTE
    return ( ajustaValorPosicion(i) == 0 || llaveSeleccionada.getOperacion( ajustaValorPosicion(i) ) != llaveSeleccionada.getOperacion(ajustaValorPosicion(i)-1) )
        ? (valorCifradoNormal(linea, i)) : (valorCifradoInverso(linea, i));
  }
  
  /**
   * Si "i" = "0" o el valor de operacion[i]!=operacion[i-1] se sigue el siguiente patrón:
   * <ul><li>En caso de que operacion[i]==true se suma el desplazamiento, pero en caso de ser false se resta.</li></ul>
   * 
   * @param linea Entrada de la línea a cifrar (necesaria para buscar el valor del caracter original que se desea cifrar).
   * @param i     Posición actual del caracter a buscar y de las tablas de cifrado/descifrado.
   * @return      Valor del caracter original.
   */
  private int valorCifradoNormal(String linea, int i) {
    return (llaveSeleccionada.getOperacion(ajustaValorPosicion(i))) ? (buscaValorOriginal(linea.substring(i, i+1)) + llaveSeleccionada.getDesplazamiento(indiceDesplazamiento(ajustaValorPosicion(i)))) 
        : (buscaValorOriginal(linea.substring(i, i+1)) - llaveSeleccionada.getDesplazamiento(indiceDesplazamiento(ajustaValorPosicion(i))));
  }
  
  /**
   * Si operacion[i]==operacion[i-1] se invierte el patrón anterior:
   * <ul><li>En caso de que operacion[i]==true se resta el desplazamiento, pero en caso de ser false 0 se suma.</li></ul>
   * 
   * @param linea Entrada de la línea a cifrar (necesaria para buscar el valor del caracter original que se desea cifrar).
   * @param i     Posición actual del caracter a buscar y de las tablas de cifrado/descifrado.
   * @return      Valor del caracter original.
   */
  private int valorCifradoInverso(String linea, int i) {
    return (llaveSeleccionada.getOperacion(ajustaValorPosicion(i))) ? (buscaValorOriginal(linea.substring(i, i + 1)) - llaveSeleccionada.getDesplazamiento(indiceDesplazamiento(ajustaValorPosicion(i))))
        : (buscaValorOriginal(linea.substring(i, i + 1)) + llaveSeleccionada.getDesplazamiento(indiceDesplazamiento(ajustaValorPosicion(i))));
  }
  
  
  //#################################     DESENCRIPTAR     #################################\\
  
  /**
   * Desencripta el texto almacenado en la Clase Texto.
   * 
   * @throws ErrorSeleccionLlave Se lanza esta excepción cuando la llave seleccionada no existe.
   */
  public void desencriptaTexto() throws ErrorSeleccionLlave {
    compruebaLlave();
    Texto.borraSalida();
    procesoDesencriptado();
  }


  /**
   * Proceso mediante el cual se desencripta el texto almacenado
   * en la tabla entradaTexto de la Clase Texto. 
   */
  private void procesoDesencriptado() {
    for (int i = 0; i < Texto.getTamannoEntrada(); i++) { //Selecciona una línea de entrada de Texto
      entrada = transformaCadena(Texto.getLineaEntrada(i));
      salida = "";
      for (int j = 0; j < Texto.getLineaEntrada(i).length(); j++) //Desencripta todos los caracteres de la línea
        salida = salidaDescifrada(salida, asignaValorDescifrado(entrada, j));
      Texto.addSalida(salida);
    }
  }

  /**
   * Concatena y devuelve el conjunto actual de los caracteres descifrados.
   * 
   * @param salida           Valor actual de la variable salida (String) antes de concatenarle otro caracter descifrado.
   * @param valorDescifrado  Valor del caracter descifrado a concatenar a la variable salida.
   * @return                 Devuelve la versión actualizada, con el nuevo caracter concatenado, de la variable salida (String).
   */
  private String salidaDescifrada(String salida, int valorDescifrado) {
    return salida += buscaCaracterOriginal(ajustaValorTabla(valorDescifrado));
  }

  /**
   * Asigna un valor descifrado correspondiente al caracter de la posición "i".
   * 
   * Si "i" = "0" o el valor de operacion[i]!=operacion[i-1] se sigue el siguiente patrón:
   * <ul><li>En caso de que operacion[i]==true se resta el desplazamiento, pero en caso de ser false 0 se suma.</li></ul>
   * Si operacion[i]==operacion[i-1] se invierte el patrón anterior:
   * <ul><li>En caso de que operacion[i]==true se suma el desplazamiento, pero en caso de ser false 0 se resta.</li></ul>
   * 
   * Lo que se pretende con esto es contrarrestar el patrón del cifrado con lo que poder descifrar el texto.
   * 
   * @param linea    Línea (String) que se desea descifrar
   * @param i        Variable de control que selecciona la posición del caracter dentro de la línea a descifrar y posición actual en las tablas de cifrado/descifrado.
   * @return         Devuelve el valor del caracter descifrado
   */
  private int asignaValorDescifrado(String linea, int i) {    //PENDIENTE DE TESTEAR, AL FINAL DEL CÓDIGO ESTÁ EL MÉTODO ANTERIOR A ÉSTE
    return (ajustaValorPosicion(i)==0 || llaveSeleccionada.getOperacion(ajustaValorPosicion(i))!=llaveSeleccionada.getOperacion(ajustaValorPosicion(i)-1)) 
        ? (valorDescifradoNormal(linea, i)) : (valorDescifradoInverso(linea, i));
  }
  /**
   * Si "i" = "0" o el valor de operacion[i]!=operacion[i-1] se sigue el siguiente patrón:
   * <ul><li>En caso de que operacion[i]==true se resta el desplazamiento, pero en caso de ser false 0 se suma.</li></ul>
   * 
   * Lo que se pretende con esto es contrarrestar el patrón del cifrado con lo que poder descifrar el texto.
   * 
   * @param linea Entrada de la línea a descifrar (necesaria para buscar el valor del caracter cifrado que se desea descifrar).
   * @param i     Posición actual del caracter a buscar y de las tablas de cifrado/descifrado.
   * @return      Valor del caracter descifrado.
   */
  private int valorDescifradoNormal(String linea, int i) {
    return (llaveSeleccionada.getOperacion(ajustaValorPosicion(i))) ? (buscaValorCifrado(invierteCaseCaracter(linea.substring(i, i+1), i)) - llaveSeleccionada.getDesplazamiento(indiceDesplazamiento(ajustaValorPosicion(i)))) 
        : (buscaValorCifrado(invierteCaseCaracter(linea.substring(i, i+1), i)) + llaveSeleccionada.getDesplazamiento(indiceDesplazamiento(ajustaValorPosicion(i))));
  }
  
  /**
   * Si operacion[i]==operacion[i-1] se invierte el patrón anterior:
   * <ul><li>En caso de que operacion[i]==true se suma el desplazamiento, pero en caso de ser false 0 se resta.</li></ul>
   * 
   * Lo que se pretende con esto es contrarrestar el patrón del cifrado con lo que poder descifrar el texto.
   * 
   * @param linea Entrada de la línea a descifrar (necesaria para buscar el valor del caracter cifrado que se desea descifrar).
   * @param i     Posición actual del caracter a buscar y de las tablas de cifrado/descifrado.
   * @return      Valor del caracter descifrado.
   */
  private int valorDescifradoInverso(String linea, int i) {
    return (llaveSeleccionada.getOperacion(ajustaValorPosicion(i))) ? (buscaValorCifrado(invierteCaseCaracter(linea.substring(i, i+1), i)) + llaveSeleccionada.getDesplazamiento(indiceDesplazamiento(ajustaValorPosicion(i)))) 
        : (buscaValorCifrado(invierteCaseCaracter(linea.substring(i, i+1), i)) - llaveSeleccionada.getDesplazamiento(indiceDesplazamiento(ajustaValorPosicion(i))));
  }
  
  
  //#################################     ÚTILES     #################################\\
  
  /**
   * Si el valor cifrado o descifrado del caracter rebasa el índice de la tabla
   * (tablaOriginal o tablaCifrada) se ajusta su valor a la posición correcta
   * para poder encontrar el caracter.
   * 
   * @param valorCaracter Resultado de establecer el valor del caracter cifrado/descifrado
   * @return              Valor del caracter cifrado
   */
  private int ajustaValorTabla(int valorCaracter) {
    if (valorCaracter>=(llaveSeleccionada.getTamanoTablaOriginal())) 
      return valorCaracter %= (llaveSeleccionada.getTamanoTablaOriginal());
    if (valorCaracter<0)
      return llaveSeleccionada.getTamanoTablaOriginal() - Math.abs(valorCaracter);
    return valorCaracter;
  }
  
  /**
   * En caso de que la variable de posición "i" sea igual o rebase el tamaño de la tabla desplazamiento
   * se rectifica y devuelve el módulo respecto a la longitud de la tabla desplazamiento.
   * 
   * @param i   Posición actual.
   * @return    Valor de la posición rectificada.
   */
  private int ajustaValorPosicion(int i) {
    if (i>=llaveSeleccionada.getLongitudDesplazamiento())
      return i %= llaveSeleccionada.getLongitudDesplazamiento();
    return i;
  }


  /**
   * Cambia las mayúsculas a minúsculas, y viceversa, de la línea que recibe por parámetro
   * 
   * @param linea Línea (String) a modificar
   * @return      Línea (String) modificada
   */
  private static String cambiaMayusMinus(String linea) {
    salida = "";
    for (int i=0; i<linea.length();i++) 
      salida += (linea.substring(i, i+1).equals(linea.substring(i, i+1).toUpperCase())) ? 
          (linea.substring(i, i+1).toLowerCase()) : (linea.substring(i, i+1).toUpperCase());
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
    for (int i=linea.length()-1; i>-1; i--)     //Leemos la cadena y almacenamos los caracteres en variable salida
      salida+=linea.charAt(i);
    return salida;
  }
  
  /**
   * Cambia las mayúsculas a minúsculas, y viceversa, e invierte la línea que recibe por parámetro.
   * 
   * @param linea   Línea (String) a modificar
   * @return        Línea modificada.
   */
  private static String transformaCadena(String linea) {
    return invertirCadena(cambiaMayusMinus(linea));
  }
  
  /**
   * Busca el valor del caracter de la tabla original comparándolo con el caracter que le pasamos.
   * 
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
   * @param valor  Valor (int) del caracter que estamos buscando 
   * @return       Devuelve el caracter (String) encontrado en la tabla original
   */
  private String buscaCaracterOriginal(int valor) {
    for (int i=0; i<llaveSeleccionada.getTamanoTablaOriginal();i++) {
      if (valor==llaveSeleccionada.getValorOriginal(i)) 
        return llaveSeleccionada.getCaracterOriginal(valor);
    }
    return "";
  }
  
  /**
   * Devuelve el valor del caracter cifrado buscado.
   * 
   * @param caracter Caracter (String) que deseamos comparar
   * @return         Devuelve el valor (int) del caracter encontrado en la tabla cifrada
   */
  private int buscaValorCifrado(String caracter) {
    return llaveSeleccionada.getValorCifrado(llaveSeleccionada.comparaCaracterCifrado(caracter));
  }
  
  
  //####################################     AMPLIACIÓN     ##############################################\\
  
  /**
   * Devuelve el índice del desplazamiento.
   * 
   * El índice de desplazamiento que se retorna por defecto es el 
   * valor que se encuentra en la posición i de la tabla indiceDesplazamiento.
   * 
   * Pero dicho valor puede verse alterado según estas condiciones:
   * <ul>
   * <li>Si la posición "i" de operacion y la posición "i" de caseCaracter son = "1" 
   * en caso de que el valor del índice de desplazamiento sea par se multiplica x4, si es impar x6.</li>
   * <li>Si la posición "i" de operacion es = "1" pero la posición "i" de caseCaracter es = "0" (o viceversa)
   * en caso de que el valor del índice de desplazamiento sea par se multiplica x2, si es impar x3.</li>
   * <li>Si la posición "i" de operacion es = "0" pero la posición "i" de caseCaracter es = "o" se devuelve el valor original del indiceDesplazamiento.</li>
   * </ul>
   * 
   * @param i Posición actual en la tabla operacion y caseCaracter.
   * @return  Índice (int) de la tabla desplazamiento.
   */
  private int indiceDesplazamiento(int i) {
    if (llaveSeleccionada.getOperacion(ajustaValorPosicion(i))) 
      return (llaveSeleccionada.getCaseCaracter(i)) ? indiceMultiplicadoX2(ajustaValorPosicion(i)) : indiceMultiplicado(i);
    return (llaveSeleccionada.getCaseCaracter(ajustaValorPosicion(i))) ? indiceMultiplicado(i) : llaveSeleccionada.getIndiceDesplazamiento(i);
  }
  
  /**
   * Devuelve el valor del índice multiplicado x2 si el número del índice en la posición "i" es par
   * o multiplicado x3 si el valor del índice en la posición "i" es impar.
   * 
   * @param i Posición en la tabla indiceDesplazamiento.
   * @return  Valor del índice x2 si el índice es un número par, x3 si es un número impar.
   */
  private int indiceMultiplicado(int i) {
    return (llaveSeleccionada.getIndiceDesplazamiento(i)%2==0) ? (llaveSeleccionada.getIndiceDesplazamiento(i)*2) : (llaveSeleccionada.getIndiceDesplazamiento(i)*3);
  }
  
  /**
   * Devuelve el valor del índice multiplicado x4 si el número del índice en la posición "i" es par
   * o multiplicado x6 si el valor del índice en la posición "i" es impar.
   * 
   * @param i Posición en la tabla indiceDesplazamiento.
   * @return  Valor del índice x4 si el índice es un número par, x6 si es un número impar.
   */
  private int indiceMultiplicadoX2(int i) {
    return (llaveSeleccionada.getIndiceDesplazamiento(i)%2==0) ? (llaveSeleccionada.getIndiceDesplazamiento(i)*4) : (llaveSeleccionada.getIndiceDesplazamiento(i)*6);
  }
  
  /**
   * Invierte de mayúscula a minúscula (y viceversa) el caracter de entrada
   * si la información de la llave determina que en la posición en la que se
   * encuentra dicho caracter coincide con que deba invertirse en esa posición.
   * 
   * La inversión se determina si en la posición i de la tabla caseCaracter
   * hay un 1, en caso de haber un 0 se retorna el caracter original de entrada.
   * 
   * Este proceso es un cambio individual, e independiente, al proceso de cambiar
   * toda la cadena, de forma general, de mayúsculas a minúsculas y viceversa
   * del proceso cambiaMayusMinus(String).
   * 
   * @param caracter  Caracter de entrada (String)
   * @param i         Posición actual, coincide con el caracter de entrada y con la tabla de caseCaracter.
   * @return          Caracter (String) modificado o sin modificar.
   */
  private String invierteCaseCaracter(String caracter, int i) {
    if (llaveSeleccionada.getCaseCaracter(ajustaValorPosicion(i)))
      return (caracter.equals(caracter.toUpperCase())) ? caracter.toLowerCase() : caracter.toUpperCase();
    return caracter;
  }
  
  
  //#################################     TO STRING     #################################\\  
  
  /**
   * Imprime el nombre de todas las llaves almacenadas en la tabla llaves.
   * 
   * @return  Cadena de caracteres con los nombres de todas las llaves almacenadas en la tabla llaves.
   */
  public String toString() {
    salida = "";
    for (int i=0; i<llaves.size(); i++) {
      salida += "Llave " + (i+1) + ":\n" + (llaves.get(i)).toString() + "\n\n"; 
    }
    return salida;
  }
  
  //####################################   TABLAS TEST     ##############################################\\
  
  /**
   * Clase para tests, imprime por pantalla los valores de las tablas originales, cifrada y desplazamiento.
   * 
   * @throws ErrorSeleccionLlave Se lanza esta excepción cuando la llave seleccionada no existe.
   */
  public void imprimeTablas() throws ErrorSeleccionLlave {
    compruebaLlave();
    for (int i=0; i<Caracteres.getLongitud();i++) {
      System.out.print("Posición " + i + ": " + llaveSeleccionada.getCaracterOriginal(i) + " valor original: " + llaveSeleccionada.getValorOriginal(i));
      System.out.print("     ||     " + llaveSeleccionada.getCaracterOriginal(i) + " valor cifrado: " + llaveSeleccionada.getValorCifrado(i) + "\n");
    }
    
    System.out.println("\nLongitud tabla original: " + llaveSeleccionada.getTamanoTablaOriginal() + " || total caracteres: " + Caracteres.getLongitud()+"\n");
    
    for (int i=0; i<llaveSeleccionada.getLongitudDesplazamiento();i++) {
      System.out.print("\nPosición " + i + ": " + llaveSeleccionada.getDesplazamiento(i));
    }
  }
  
  //####################################     PRUEBAS     ##############################################\\
  /*
   * Los dos métodos que vienen a continuación eran los métodos originales de Cypher, ahora solo están para realizar pruebas, 
   * serán sustituidos por los métodos nuevos que interactúan con la Clase Texto.
   */
  /**
   * 
   * Encripta la línea pasada por parámetro
   * 
   * @param linea    Línea (String) a cifrar.
   * @return         Devuelve (String) cifrado.
   * 
   * @throws ErrorSeleccionLlave Se lanza esta excepción cuando la llave seleccionada no existe.
   */
  @Deprecated public String encripta(String linea) throws ErrorSeleccionLlave {
    compruebaLlave();
    salida = "";
    for (int i=0; i<linea.length();i++) {
      salida = salidaCifrada(salida, asignaValorCifrado(linea, i), i);
    }
    return transformaCadena(salida);
  }
  
  /**
   * Desencripta la línea pasada por parámetro
   * 
   * @param linea    Línea (String) a descifrar.
   * @return         Devuelve (String) descifrado.
   * 
   * @throws ErrorSeleccionLlave Se lanza esta excepción cuando la llave seleccionada no existe.
   */
  @Deprecated public String desencripta(String linea) throws ErrorSeleccionLlave {
    compruebaLlave();
    salida = "";
    for (int i=0; i<linea.length(); i++) {
      salida = salidaDescifrada(salida, asignaValorDescifrado(transformaCadena(linea), i));
    }
    return salida;
  }
  
///**
//* Asigna un valor cifrado correspondiente al caracter de la posición "i".
//* 
//* @param linea  Línea (String) que se desea cifrar
//* @param i      Variable de control que selecciona la posición del caracter dentro de la línea a cifrar.
//* @return       Devuelve el valor del caracter cifrado
//*/
//private int asignaValorCifrado(String linea, int i) {
// return (llaveSeleccionada.getOperacion(ajustaValorPosicion(i))) ? (buscaValorOriginal(linea.substring(i, i+1)) + llaveSeleccionada.getDesplazamiento(indiceDesplazamiento(ajustaValorPosicion(i)))) 
//     : (buscaValorOriginal(linea.substring(i, i+1)) - llaveSeleccionada.getDesplazamiento(indiceDesplazamiento(ajustaValorPosicion(i))));
//}
  
  
///**
//* Asigna un valor descifrado correspondiente al caracter de la posición "i".
//* 
//* @param linea    Línea (String) que se desea descifrar
//* @param i        Variable de control que selecciona la posición del caracter dentro de la línea a descifrar.
//* @return         Devuelve el valor del caracter descifrado
//*/
//private int asignaValorDescifrado(String linea, int i) {
// return (llaveSeleccionada.getOperacion(ajustaValorPosicion(i))) ? (buscaValorCifrado(invierteCaseCaracter(linea.substring(i, i+1), i)) - llaveSeleccionada.getDesplazamiento(indiceDesplazamiento(ajustaValorPosicion(i)))) : 
//   (buscaValorCifrado(invierteCaseCaracter(linea.substring(i, i+1), i)) + llaveSeleccionada.getDesplazamiento(indiceDesplazamiento(ajustaValorPosicion(i))));
//}
  
}
