package enigma.cypher;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Clase Llave almacena las tablas con los valores aleatorios y el orden de los caracteres originales y cifrados
 * con el fin de que la Clase Cypher pueda emplear estos valores en el proceso de cifrado y descifrado de un texto.
 * 
 * También se encargará del proceso de impotar y exportar llaves garantizando siempre que, la llave a exportar
 * o a importar, cumpla con los requisitos para su correcto funcionamiento.
 * 
 * @author Francisco Javier González Sabariego
 *
 */
public class Llave {
  
  //Variables para generar llaves:
  private ArrayList<Caracteres> tablaOriginal = new ArrayList<Caracteres>();  //Tabla de caracteres con valores originales.
  private ArrayList<Integer> numerosAleatorios = new ArrayList<Integer>();    //Tabla de valores aleatorios con los que se genera la tabla original.
  private ArrayList<Caracteres> tablaCifrada = new ArrayList<Caracteres>();   //Tabla de caracteres con valores aleatorios.
  private ArrayList<Boolean> usados = new ArrayList<Boolean>();               //Tabla de valores booleanos del mismo tamaño que las tablas de caracteres
  private ArrayList<Integer> desplazamiento = new ArrayList<Integer>();       //Tabla con los desplazamientos de los caracteres del texto a cifrar.
  private ArrayList<Integer> indiceDesplazamiento = new ArrayList<Integer>(); //Tabla con los índices de los desplazamientos.
  private ArrayList<Integer> operacion = new ArrayList<Integer>();            //Tabla con los valores de las operaciones.
  private ArrayList<Integer> caseCaracter = new ArrayList<Integer>();         //Tabla con los índices de los desplazamientos.
  private String nombreLlave;
  
  //Variables para importar/exportar llaves:
  private static DataInputStream r;
  private static DataOutputStream w;
  private static String linea;
  private static ArrayList<String> informacion = new ArrayList<String>();
  private static final String VERSION_LLAVE = "1.0";
  
  
  
  //#################################     GENERAR LLAVE     #################################\\
  
  /**
   * Constructor
   */
  public Llave() {
    generaLlave();
  }

  /**
   * Método que es llamado por el constructor para generar una llave nueva
   */
  private void generaLlave() {
    generarUsados();
    crearTablas();
    //reiniciaUsados();
    creaTablasDesplazamiento();
    creaTablasBinarias();
    nombreLlave = generaNombre();
    limpiaTablas();
  }
  
  /**
   * Genera las tablas operacion y caseCaracter, ambas con 0 y 1.
   */
  private void creaTablasBinarias() {
    creaTablaOperacion();
    creaTablaCase();
  }
  
  /**
   * Genera aleatoriamente 0 y 1 en la tabla caseCaracter.
   */
  private void creaTablaCase() {
    for (int i=0; i<desplazamiento.size(); i++)
      caseCaracter.add((int)(Math.random()*2));
  }
  
  /**
   * Genera aleatoriamente 0 y 1 en la tabla operacion.
   */
  private void creaTablaOperacion() {
    for (int i=0; i<desplazamiento.size(); i++)
      operacion.add((int)(Math.random()*2));
  }

  /**
   * Genera la tabla desplazamiento y la tabla con el índice del desplazamiento.
   */
  private void creaTablasDesplazamiento() {
    generaDesplazamiento();
    generaIndiceDesplazamiento();
  }
  
  /**
   * Genera la tabla con el índice del desplazamiento.
   */
  private void generaIndiceDesplazamiento() {
    for (int i=0; i<desplazamiento.size(); i++)
      indiceDesplazamiento.add((int)(Math.random()*255));
  }

  /**
   * Limpia las tablas usados y numerosAleatorios.
   */
  private void limpiaTablas() {
    usados.clear();
    numerosAleatorios.clear();
  }
  
  /**
   * Genera la tabla de booleanos "usados".
   */
  private void generarUsados() {
    for (int i=0; i<Caracteres.getLongitud(); i++) {
      usados.add(false);
    }
  }
  
  /**
   * Resetea la tabla usados.
   */
  private void reiniciaUsados() {
    for (int i=0; i<Caracteres.getLongitud(); i++) {
      usados.set(i, false);
    }
  }  
  
  /**
   * Genera la tabla original alterando aleatoriamente los valores.
   */
  private void crearTablas() {
    int azar;
    for (int i=0; i<Caracteres.getLongitud(); i++) {
      do {
        azar = (int)(Math.random()*(usados.size()));
      } while(usados.get(azar));
      asignaValoresTablas(azar, i);
    }
    reiniciaUsados();
    cifraTabla();    
  }

  /**
   * Asigna valores a las tablas que permiten la creación de la llave.
   * 
   * @param azar  Número (int) generado aleatoriamente.
   * @param i     Posición dentro de la tabla a crear.
   */
  private void asignaValoresTablas(int azar, int i) {
    usados.set(azar, true);
    creaCaracteres(azar, i);
    numerosAleatorios.add(azar);
  }

  /**
   * Crea los caracteres con sus respectivos valores original y cifrado.
   * 
   * @param azar  Número (int) generado aleatoriamente.
   * @param i     Posición dentro de la tabla a crear.
   */
  private void creaCaracteres(int azar, int i) {
    tablaOriginal.add(new Caracteres(azar));
    tablaCifrada.add(new Caracteres(azar));
    (tablaOriginal.get(i)).setValor(i); 
    (tablaCifrada.get(i)).setValor(i);
  }  
  
  /**
   * Encripta la tabla de caracteres cifrados asignando valores aleatorios y únicos a cada uno de los caracteres.
   */
  private void cifraTabla() {
    int azar=0;
    for (int i=0; i<tablaCifrada.size(); i++) {
      do {
        azar = (int)(Math.random()*(usados.size()));
      } while(usados.get(azar));
      usados.set(azar, true);
      (tablaCifrada.get(i)).setValor(azar);
    }
  }  
  
  /**
   * Genera la tabla con los desplazamientos que será utilizados para cifrar/descifrar los mensajes.
   */
  private void generaDesplazamiento() {
    for (int i=0; i<1531; i++) 
      desplazamiento.add((int)(Math.random()*(tablaCifrada.size()-1)+1));
  }  
  
  /**
   * Genera el nombre de la llave.
   * 
   * @return  Nombre (String) de la llave.
   */
  private String generaNombre() {
    String nombre = "|***|";
    nombre = creaCuerpoA(nombre); 
    nombre = creaCuerpoB(nombre);   
    nombre = creaCuerpoC(nombre);
    nombre = creaCuerpoD(nombre); 
    nombre = creaCuerpoE(nombre); 
    nombre = creaCuerpoF(nombre); 
    return nombre;
  }
  
  /**
   * Al generar el nombre guarda la información de la tabla "tablaOriginal".
   * 
   * @param nombre  Valor inicial del nombre de la llave.
   * @return        Nombre de la llave con la primera sección del cuerpo, la tabla "tablaOriginal"
   */
  private String creaCuerpoA(String nombre) {
    for (int i=0; i<numerosAleatorios.size(); i++) {
      nombre += Matematicas.conversionHexa(numerosAleatorios.get(i));      
      nombre += (i!=numerosAleatorios.size()-1) ? "|" : "\n|*-*|";
    }
    return nombre;
  }
  
  /**
   * Al generar el nombre guarda la información de la tabla "tablaCifrada".
   * 
   * @param nombre  Valor inicial del nombre de la llave.
   * @return        Nombre de la llave con la segunda sección del cuerpo, la tabla "tablaCifrada"
   */
  private String creaCuerpoB(String nombre) {
    for (int i=0; i<tablaCifrada.size(); i++) {
      nombre += Matematicas.conversionHexa((tablaCifrada.get(i)).getValor());      
      nombre += (i!=tablaCifrada.size()-1) ? "|" : "\n|-*-|";
    }
    return nombre;
  }
  
  /**
   * Al generar el nombre guarda la información de la tabla "desplazamiento".
   * 
   * @param nombre  Valor inicial del nombre de la llave.
   * @return        Nombre de la llave con la tercera sección del cuerpo, la tabla "desplazamiento"
   */
  private String creaCuerpoC(String nombre) {
    for (int i=0; i<desplazamiento.size(); i++) {
      nombre += Matematicas.conversionHexa(desplazamiento.get(i));      
      nombre += (i!=desplazamiento.size()-1) ? ":" : "\n|-*_|";
    }
    return nombre;
  }
  
  /**
   * Al generar el nombre guarda la información de la tabla "indiceDesplazamiento".
   * 
   * @param nombre  Valor inicial del nombre de la llave.
   * @return        Nombre de la llave con la cuarta sección del cuerpo, la tabla "indiceDesplazamiento".
   */
  private String creaCuerpoD(String nombre) {
    for (int i=0; i<indiceDesplazamiento.size(); i++) {
      nombre += Matematicas.conversionHexa(indiceDesplazamiento.get(i));      
      nombre += (i!=indiceDesplazamiento.size()-1) ? ":" : "\n|_*-|";
    }
    return nombre;
  }
  
  /**
   * Al generar el nombre guarda la información de la tabla "operacion".
   * 
   * @param nombre  Valor inicial del nombre de la llave.
   * @return        Nombre de la llave con la quinta sección del cuerpo, la tabla "operacion".
   */
  private String creaCuerpoE(String nombre) {
    for (int i=0; i<operacion.size(); i++) {
      nombre += String.valueOf((operacion.get(i)));      
      nombre += (i!=indiceDesplazamiento.size()-1) ? "" : "\n|_*_|";
    }
    return nombre;
  }
  
  /**
   * Al generar el nombre guarda la información de la tabla "caseCaracter".
   * 
   * @param nombre  Valor inicial del nombre de la llave.
   * @return        Nombre de la llave con la quinta sección del cuerpo, la tabla "caseCaracter".
   */
  private String creaCuerpoF(String nombre) {
    for (int i=0; i<caseCaracter.size(); i++) {
      nombre += String.valueOf((caseCaracter.get(i)));
    }
    return nombre;
  }
  
  
  //#################################     IMPORTAR LLAVE     #################################\\
  
  
  /**
   * Constructor para importar una llave
   *  
   * @throws IOException            Se lanza esta excepción cuando no se pueda leer el fichero.
   * @throws versionLlaveIncorrecta Se lanza esta excepción cuando la versión de la llave a importa no coincide con la versión del programa.
   * @throws cabeceraInvalida       Se lanza esta excepción si al validar la cabecera no se corresponde con la forma correcta.
   */
  public Llave(String ruta) throws IOException, versionLlaveIncorrecta, cabeceraInvalida {
    importaLlave(ruta);
  }  
  
  /**
   * Importa la llave desde un fichero.
   * 
   * @throws IOException            Se lanza esta excepción cuando no se pueda leer el fichero.
   * @throws versionLlaveIncorrecta Se lanza esta excepción cuando la versión de la llave a importar no coincide con la versión del programa.
   * @throws cabeceraInvalida       Se lanza esta excepción si al validar la cabecera no se corresponde con la forma correcta.
   * 
   */
  private void importaLlave(String ruta) throws IOException, versionLlaveIncorrecta, cabeceraInvalida { 
    creaLector(ruta);
    leeFichero();
    creaLlave();
  }  
  
  /**
   * Lee el fichero con la información de la llave que se desea importar.
   * 
   * @throws IOException            Se lanza esta excepción cuando no se pueda leer el fichero.
   * @throws versionLlaveIncorrecta Se lanza esta excepción cuando la versión de la llave a importar no coincide con la versión del programa.
   * 
   */
  private void leeFichero() throws IOException, versionLlaveIncorrecta {
    validaVersionLlave();
    guardaDatos();
  }
  
  /**
   * Crea la llave que se desea importar.
   * 
   * @throws cabeceraInvalida Se lanza esta excepción si al validar la cabecera no se corresponde con la forma correcta.
   */
  private void creaLlave() throws cabeceraInvalida {
    importaCuerpoLlave();
    nombreLlave = generaNombreImportado();
    informacion.clear();
  }

  /**
   * Importa las 6 partes que componen el cuerpo de una llave.
   * 
   * @throws cabeceraInvalida Se lanza esta excepción si al validar la cabecera no se corresponde con la forma correcta.
   */
  private void importaCuerpoLlave() throws cabeceraInvalida {
    importaCuerpoA();
    importaCuerpoB();
    importaCuerpoC();
    importaCuerpoD();
    importaCuerpoE();
    importaCuerpoF();
  }
  
  /**
   * Genera el nombre de una llave importada.
   * 
   * @return  Nombre de la llave (String).
   */
  private String generaNombreImportado() {
    return informacion.get(0)+"\n"+informacion.get(1)+"\n"+informacion.get(2)+"\n"+informacion.get(3)+"\n"+informacion.get(4)+"\n"+informacion.get(5);
  }
  
  /**
   * Valida que la cabecera del cuerpo A de la llave a importar sea correcta
   * 
   * @return true si es correcto, false si no lo es.
   */
  private boolean validaCabeceraA() {
    if (informacion.get(0).substring(0, 5).equals("|***|") && 
        informacion.get(0).substring(5, informacion.get(0).length()).length()==informacion.get(1).substring(5, informacion.get(1).length()).length())
      return true;
    return false;
  }

  /**
   * Importa los valores de la primera parte de la llave.
   * 
   * @throws cabeceraInvalida Se lanza esta excepción si al validar la cabecera no se corresponde con la forma correcta.
   */
  private void importaCuerpoA() throws cabeceraInvalida {
    if (!validaCabeceraA())
      throw new cabeceraInvalida();
    int k = 0; 
    for (int i=5; i<informacion.get(0).length(); i+=3) {
      tablaOriginal.add(new Caracteres(Matematicas.hexaADecimal(informacion.get(0).substring(i, i+2))));
      tablaCifrada.add(new Caracteres(Matematicas.hexaADecimal(informacion.get(0).substring(i, i+2))));
      (tablaOriginal.get(k)).setValor(k); 
      (tablaCifrada.get(k)).setValor(k);
      k++;
    }
  }
  
  /**
   * Valida que la cabecera del cuerpo B de la llave a importar sea correcta
   * 
   * @return  true si es correcto, false si no lo es.
   */
  private boolean validacabeceraB() {
    if (informacion.get(1).substring(0, 5).equals("|*-*|"))
      return true;
    return false;
  }
  
  /**
   * Importa los valores de la segunda parte de la llave.
   * 
   * @throws cabeceraInvalida Se lanza esta excepción si al validar la cabecera no se corresponde con la forma correcta.
   */
  private void importaCuerpoB() throws cabeceraInvalida {
    if (!validacabeceraB())
      throw new cabeceraInvalida();
    int k = 0;
    for (int i=5; i<informacion.get(1).length(); i+=3) {
      (tablaCifrada.get(k)).setValor(Matematicas.hexaADecimal(informacion.get(1).substring(i, i+2)));
      k++;
    }
  }
  
  /**
   * Valida que la cabecera del cuerpo C de la llave a importar sea correcto
   * 
   * @return  true si es correcto, false si no lo es.
   */
  private boolean validaCabeceraC() {
    if (informacion.get(2).substring(0, 5).equals("|-*-|"))
      return true;
    return false;
  }
  
  /**
   * Importa los valores de la tercera parte de la llave.
   * 
   * @throws cabeceraInvalida Se lanza esta excepción si al validar la cabecera no se corresponde con la forma correcta.
   */
  private void importaCuerpoC() throws cabeceraInvalida {
    if (!validaCabeceraC())
      throw new cabeceraInvalida();
    for (int i=5; i<informacion.get(2).length(); i+=3) {
      desplazamiento.add(Matematicas.hexaADecimal(informacion.get(2).substring(i, i+2)));
    }
  }
  
  /**
   * Valida que la cabecera del cuerpo D de la llave a importar sea correcto
   * 
   * @return  true si es correcto, false si no lo es.
   */
  private boolean validaCabeceraD() {
    if (informacion.get(3).substring(0, 5).equals("|-*_|"))
      return true;
    return false;
  }
  
  /**
   * Importa los valores de la cuarta parte de la llave.
   * 
   * @throws cabeceraInvalida Se lanza esta excepción si al validar la cabecera no se corresponde con la forma correcta.
   */
  private void importaCuerpoD() throws cabeceraInvalida {
    if (!validaCabeceraD())
      throw new cabeceraInvalida();
    for (int i=5; i<informacion.get(3).length(); i+=3) {
      indiceDesplazamiento.add(Matematicas.hexaADecimal(informacion.get(3).substring(i, i+2)));
    }
  }
  
  /**
   * Valida que la cabecera del cuerpo E de la llave a importar sea correcto
   * 
   * @return  true si es correcto, false si no lo es.
   */
  private boolean validaCabeceraE() {
    if (informacion.get(4).substring(0, 5).equals("|_*-|"))
      return true;
    return false;
  }
  
  /**
   * Importa los valores de la quinta parte de la llave.
   * 
   * @throws cabeceraInvalida Se lanza esta excepción si al validar la cabecera no se corresponde con la forma correcta.
   */
  private void importaCuerpoE() throws cabeceraInvalida {
    if (!validaCabeceraE())
      throw new cabeceraInvalida();
    for (int i=5; i<informacion.get(4).length(); i++) {
      operacion.add(Integer.parseInt(informacion.get(4).substring(i, i+1)));
    }
  }
  
  /**
   * Valida que la cabecera del cuerpo de la llave a importar sea correcto
   * 
   * @return  true si es correcto, false si no lo es.
   */
  private boolean validaCabeceraF() {
    if (informacion.get(5).substring(0, 5).equals("|_*_|"))
      return true;
    return false;
  }
  
  /**
   * Importa los valores de la sexta parte de la llave.
   * 
   * @throws cabeceraInvalida Se lanza esta excepción si al validar la cabecera no se corresponde con la forma correcta.
   */
  private void importaCuerpoF() throws cabeceraInvalida {
    if (!validaCabeceraF())
      throw new cabeceraInvalida();
    for (int i=5; i<informacion.get(5).length(); i++) {
      caseCaracter.add(Integer.parseInt(informacion.get(5).substring(i, i+1)));
    }
  }
  
  /**
   * Almacena los datos que conforman las líneas del fichero con la información de la llave.
   * 
   * @throws IOException Se lanza esta excepción cuando no se pueda leer el fichero.
   */
  private void guardaDatos() throws IOException {
    for (int i=1; i<7; i++) {
      linea = r.readUTF();
      informacion.add(linea.replaceAll("\n", ""));
    }
    r.close();
  }

  /**
   * Valida que la versión de la llave que se desea importar sea la correcta.
   * 
   * @throws IOException            Se lanza esta excepción cuando no se pueda leer el fichero.
   * @throws versionLlaveIncorrecta Se lanza esta excepción cuando la versión de la llave a importar no coincide con la versión del programa.
   */
  private void validaVersionLlave() throws IOException, versionLlaveIncorrecta {
    linea = r.readUTF();
    if (!linea.replaceAll("\n", "").equals("///"+VERSION_LLAVE+"\\\\\\"))
      throw new versionLlaveIncorrecta();
  }

  /**
   * Crea el puntero que leerá las líneas del fichero de la llave.
   *  
   * @param ruta  Ruta absoluta del fichero de la llave que se desea importa.
   * 
   * @throws FileNotFoundException Se lanza cuando no se encuentra el fichero.
   */
  private void creaLector(String ruta) throws FileNotFoundException {
    r = new DataInputStream(new FileInputStream(ruta));
  }
  
  
  //#################################     EXPORTAR LLAVE     #################################\\
  
  /**
   * Exporta la llave a un fichero binario.
   * 
   * @throws IOException Se lanza esta excepción cuando no se pueda crear el fichero.
   * 
   */
  public void exportaLlave(String ruta) throws IOException {
    creaEscritor(ruta);
    almacenaDatos();
    guardaFichero();
    informacion.clear();
  }
  
  /**
   * Guarda los datos de la llave a exportar en el ArrayList informacion.
   */
  private void almacenaDatos() {
    almacenaVersion();
    almacenaCuerpoA();
    almacenaCuerpoB();
    almacenaCuerpoC();
    almacenaCuerpoD();
    almacenaCuerpoE();
    almacenaCuerpoF();
  }
  
  /**
   * Almacena la versión a la que pertenece la llave a expotar.
   */
  private void almacenaVersion() {
    informacion.add("///"+VERSION_LLAVE+"\\\\\\");
  }
  
  /**
   * Almacena el cuerpo A de la llave a exportar.
   */
  private void almacenaCuerpoA() {
    linea="|***|";
    for (int i=0; i<tablaOriginal.size(); i++)
      linea+=(i!=tablaOriginal.size()-1) ? (Matematicas.conversionHexa(tablaOriginal.get(i).getPosicionCaracter(tablaOriginal.get(i).getCaracter()))+"|") : 
        (Matematicas.conversionHexa(tablaOriginal.get(i).getPosicionCaracter(tablaOriginal.get(i).getCaracter())));
    informacion.add(linea);
  }
  
  /**
   * Almacena el cuerpo B de la llave a exportar.
   */
  private void almacenaCuerpoB() {
    linea="|*-*|";
    for (int i=0; i<tablaCifrada.size(); i++)
      linea+=(i!=tablaCifrada.size()-1) ? (Matematicas.conversionHexa(tablaCifrada.get(i).getValor())+"|") : 
        (Matematicas.conversionHexa(tablaCifrada.get(i).getValor()));
    informacion.add(linea);
  }
  
  /**
   * Almacena el cuerpo C de la llave a exportar.
   */
  private void almacenaCuerpoC() {
    linea="|-*-|";
    for (int i=0; i<desplazamiento.size(); i++)
      linea+=(i!=desplazamiento.size()-1) ? (Matematicas.conversionHexa(desplazamiento.get(i))+":") : 
        (Matematicas.conversionHexa(desplazamiento.get(i)));
    informacion.add(linea);
  }
  
  /**
   * Almacena el cuerpo D de la llave a exportar.
   */
  private void almacenaCuerpoD() {
    linea="|-*_|";
    for (int i=0; i<indiceDesplazamiento.size(); i++)
      linea+=(i!=indiceDesplazamiento.size()-1) ? (Matematicas.conversionHexa(indiceDesplazamiento.get(i))+":") : 
        (Matematicas.conversionHexa(indiceDesplazamiento.get(i)));
    informacion.add(linea);
  }
  
  /**
   * Almacena el cuerpo E de la llave a exportar.
   */
  private void almacenaCuerpoE() {
    linea="|_*-|";
    for (int i=0; i<operacion.size(); i++)
      linea+=String.valueOf(operacion.get(i));
    informacion.add(linea);
  }
  
  /**
   * Almacena el cuerpo F de la llave a exportar.
   */
  private void almacenaCuerpoF() {
    linea="|_*_|";
    for (int i=0; i<caseCaracter.size(); i++)
      linea+=String.valueOf(caseCaracter.get(i));
    informacion.add(linea);
  }
  
  /**
   * Escribe en el fichero la información de la llave.
   * 
   * @throws IOException Se lanza esta excepción cuando no se pueda crear el fichero.
   */
  private void guardaFichero() throws IOException {
    for (int i=0; i<7; i++) {
      if (i!=6)
        w.writeUTF(informacion.get(i)+"\n");
      else
        w.writeUTF(informacion.get(i));
    }
  }

  /**
   * Crea el puntero que escribe en el fichero binario donde se almacenará
   * la información de la llave a exportar.
   * 
   * @param ruta  Ruta y nombre del fichero que contendrá la información de la llave exportar.
   * 
   * @throws      FileNotFoundException 
   */
  private void creaEscritor(String ruta) throws FileNotFoundException {
    w = new DataOutputStream(new FileOutputStream(ruta+".kyph"));
  }
  
  
  //#######################################     GETTERS     #######################################\\  
  
  /**
   * Devuelve el nombre de la llave.
   * 
   * @return  Nombre de la llave (String)
   */
  public String getNombreLlave() {
    return this.nombreLlave;
  }  
  
  /**
   * Devuelve el desplazamiento (ajustado) según el índice pasado como parámetro.
   * 
   * @param indice  Índice de la posición dentro de la tabla desplazamiento.
   * @return        Devuelve el valor ajustado del desplazamiento (int).
   */
  public int getDesplazamiento(int indice) {
    if (indice>=this.desplazamiento.size()) {
      indice %= this.desplazamiento.size();
      return this.desplazamiento.get(indice);
    } else
      return this.desplazamiento.get(indice);
  }  
  
  /**
   * Devuelve el caracter cifrado cuyo valor ha sido pasado como parámetro.
   * 
   * @param valor   Valor del caracter cifrado.
   * @return        Caracter cifrado (String).
   */
  public String getCaracterCifrado(int valor) {
    for (int i=0; i<this.tablaCifrada.size();i++) {
      if (valor==(this.tablaCifrada.get(i)).getValor()) 
        return (this.tablaCifrada.get(i)).getCaracter();
    }
    return "";
  }
  
  /**
   * Devuelve el caracter original cuyo valor ha sido pasado como parámetro.
   * 
   * @param valor   Valor del caracter original.
   * @return        Caracter original (String).
   */
  public String getCaracterOriginal(int valor) {
    for (int i=0; i<this.tablaOriginal.size();i++) {
      if (valor==(this.tablaOriginal.get(i)).getValor()) 
        return (this.tablaOriginal.get(i)).getCaracter();
    }
    return "";
  }  
  
  /**
   * Devuelve el valor del caracter cifrado cuyo índice pasamos como parámetro.
   * 
   * @param indice  Índice del caracter a buscar
   * @return        Devuelve el valor cifrado del caracter encontrado (int).
   */
  public int getValorCifrado(int indice) {
    return (this.tablaCifrada.get(indice)).getValor();
  }
  
  /**
   * Devuelve el valor del caracter original cuyo índice pasamos como parámetro.
   * 
   * @param indice  Índice del caracter a buscar
   * @return        Devuelve el valor original del caracter encontrado (int).
   */
  public int getValorOriginal(int indice) {
    return (this.tablaOriginal.get(indice)).getValor();
  }  
  
  /**
   * Devuelve el tamaño de la tabla original
   * 
   * @return  Devuelve (int) tamaño tabla original
   */
  public int getTamanoTablaOriginal() {
    return (this.tablaOriginal).size();
  }
  
  /**
   * Devuelve la cantidad de filas que hay en la tabla desplazamiento.
   * 
   * @return  Total filas tabla desplazamiento (int).
   */
  public int getLongitudDesplazamiento() {
    return this.desplazamiento.size();
  }
  
  /**
   * Devuelve el índice del desplazamiento almacenado en la posición pasada
   * por parámetro.
   * 
   * @param i
   * @return
   */
  public int getIndiceDesplazamiento(int i) {
    return this.indiceDesplazamiento.get(i);
  }
  
  /**
   * Devuelve el valor en forma de (booleano) del contenido de la tabla
   * operacion en la posición pasada por parámetro.
   * 
   * @param i
   * @return
   */
  public boolean getOperacion(int i) {
    if (operacion.get(i%=operacion.size())==1) //i%=operacion.size())==1
      return true;
    return false;
  }
  
  /**
   * Devuelve el valor en forma de (booleano) del contenido de la tabla
   * duplicador en la posición pasada por parámetro.
   * 
   * @param i
   * @return
   */
  public boolean getCaseCaracter(int i) {
    if (caseCaracter.get(i%=caseCaracter.size())==1) //i%=caseCaracter.size())==1
      return true;
    return false;
  }
  
  /**
   * Compara el caracter insertado con la tabla de caracteres cifrados y devuelve su índice.
   * 
   * @param caracter  Caracter a comparar.
   * @return          Índice (int) del caracter en la tabla de caracteres cifrados.
   */
  int comparaCaracterCifrado(String caracter) {
    int indice = 0;
    for (int i=0; i<tablaCifrada.size();i++) {
      if (caracter.equals((tablaCifrada.get(i)).getCaracter())) {
        indice = i;
        break;
      }
    }
    return indice;
  }
  
  
  //#################################     TO STRING     #################################\\
  
  public String toString() {
    return this.nombreLlave;
  }
  
}
