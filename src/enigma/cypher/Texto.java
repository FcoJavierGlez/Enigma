package enigma.cypher;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Clase encargada de almacenar el texto de entrada procedente o bien desde una variable o desde un fichero,
 * con la finalidad de que la Clase Cypher pueda leer las líneas almacenadas y poder cifrar/descifrar cada
 * una de ellas de forma individual.
 * 
 * @author Francisco Javier González Sabariego
 *
 */
public class Texto {
  
  private static BufferedReader r;  //Puntero lectura de ficheros.
  private static BufferedWriter w;  //Puntero escritura de ficheros.
  
  private static ArrayList<String> entradaTexto = new ArrayList<String>();  //Entrada de texto (variable o fichero)
  private static ArrayList<String> salidaTexto = new ArrayList<String>();   //Salida de texto que se desea mostrar/exportar
  
  private static String salida;
  private static String linea;
  
  /**
   * Lee la entrada de una cadena de caracteres y la almacena.
   * 
   * @param entrada Cadena de caracteres.
   */
  public static void introduceTexto(String entrada) {
    entradaTexto.clear();
    eliminaSaltoLinea(entrada);
  }

  /**
   * Recoge una cadena de caracteres de entrada y por cada salto de línea que encuentre
   * inserta en una nueva fila el contenido anterior a dicho salto (omitiendo el propio
   * salto de línea).
   * 
   * @param entrada Cadena de caracteres.
   */
  private static void eliminaSaltoLinea(String entrada) {
    for (int i=0; i<entrada.length(); i++) {
      if (entrada.substring(i, i+1).equals("\n")) {
        if (i==0) 
          entradaTexto.add("");
        else
          entradaTexto.add(entrada.substring(0, i));
        entrada=entrada.substring(i+1, entrada.length());
        i=-1;
      }
      if (i==entrada.length()-1)
        entradaTexto.add(entrada);
    }
  }
  
  /**
   * Almacena el contenido de un fichero de texto plano.
   * 
   * @param entrada Ruta del fichero que se desea importar.
   * @throws IOException Se lanza esta excepción en caso de no encontrar el fichero de entrada.
   */
  public static void importaFichero(String ruta) throws IOException {
    entradaTexto.clear();
    creaLector(ruta);
    leeLineas();
  }
  
  /**
   * Devuelve en forma de cadena decaracteres el contenido de salidaTexto (el ArrayList
   * con el texto que previamente haya sido cifrado/descifrado por la Clase Cypher).
   * 
   * @return  Texto en formato cadena de caracteres.
   */
  public static String imprimeTexto() {
    salida="";
    for (int i=0; i<salidaTexto.size();i++) {
      salida+=salidaTexto.get(i)+"\n";
    }
    return salida;
  }
  
  /**
   * Devuelve el tamaño del ArrayList entradaTexto.
   * 
   * @return Tamaño ArrayList entradaTexto.
   */
  public static int getTamannoEntrada() {
    return entradaTexto.size();
  }
  
  /**
   * Devuelve el tamaño del ArrayList salidaTexto.
   * 
   * @return Tamaño ArrayList salidaTexto.
   */
  public static int getTamannoSalida() {
    return salidaTexto.size();
  }
  
  /**
   * Devuelve la línea almacenada en la posición pasada por parámetro
   * del ArrayList entradaTexto.
   * 
   * @param Posicion (int) a buscar en el ArrayList
   * @return  Línea almacenada en la posicion
   */
  public static String getLineaEntrada(int posicion) {
    return entradaTexto.get(posicion);
  }
  
  /**
   * Devuelve la línea almacenada en la posición pasada por parámetro
   * del ArrayList salidaTexto.
   * 
   * @param Posicion (int) a buscar en el ArrayList
   * @return  Línea almacenada en la posicion
   */
  public static String getLineaSalida(int posicion) {
    return salidaTexto.get(posicion);
  }
  
  /**
   * Añade una nueva entrada al ArrayList entradaTexto.
   * 
   * @param entrada Cadena de caracteres que se desea almacenar.
   */
  public static void addEntrada(String entrada) {
    entradaTexto.add(entrada);
  }
  
  /**
   * Añade una nueva entrada al ArrayList salidaTexto.
   * 
   * @param entrada Cadena de caracteres que se desea almacenar.
   */
  public static void addSalida(String entrada) {
    salidaTexto.add(entrada);
  }
  
  /**
   * Borra todas las filas de entradaTexto.
   */
  public static void borraEntrada() {
    entradaTexto.clear();
  }
  
  /**
   * Borra todas las filas de salidaTexto.
   */
  public static void borraSalida() {
    salidaTexto.clear();
  }
  
  /**
   * Crea el puntero que lee de cada línea del fichero cuya ruta absoluta se le pasa como parámetro.
   * 
   * @param rutaEntrada Ruta absoluta del fichero a leer.
   * 
   * @throws FileNotFoundException  Se lanza esta excepción si no se encuentra el fichero.
   */
  private static void creaLector(String rutaEntrada) throws FileNotFoundException {
    r = new BufferedReader(new FileReader(rutaEntrada));
  }
  
  /**
   * Crea el puntero que escribe cada línea en el nuevo fichero.
   * 
   * @param rutaSalida  Ruta absoluta donde se guardará o sobreescribirá el fichero.
   * 
   * @throws IOException Se lanza esta excepción si hubiese algún conflicto al crear el fichero.
   */
  private static void creaEscritor(String rutaSalida) throws IOException {
    w = new BufferedWriter(new FileWriter(rutaSalida));
  }
  
  /**
   * Lee las lineas del fichero y las devuelve modificadas tal como se
   * especifica en el método modificaLineas().
   * 
   * @return  Línea de salida
   * 
   * @throws IOException 
   */
  private static void leeLineas() throws IOException {
    while (true) {
      linea = r.readLine();
      if (linea==null) 
        break;
      entradaTexto.add(linea.replaceAll("\n",""));
    }
    r.close();
  }
  
  /**
   * Escribe en un fichero el resultado de haber modificado las líneas
   * del fichero de entrada.
   * 
   * @throws IOException  Excepción si no se encuentra el fichero.
   */
  public static void exportaFichero(String rutaSalida) throws IOException {
    creaEscritor(rutaSalida);
    for (int i=0; i<salidaTexto.size(); i++) {
      w.write(salidaTexto.get(i)+"\n");
    }
    w.close();
  }

//  /**
//   * 
//   */
//  private static void reseteaValores() {
//    salida="";
//    entradaTexto.clear();
//  }
  
//public static void deSalidaAEntrada() {
//entradaTexto.clear();
//for (int i=0; i<salidaTexto.size(); i++) 
//  entradaTexto.add(salidaTexto.get(i));
//}
  
  
}
