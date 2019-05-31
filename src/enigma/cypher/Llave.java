package enigma.cypher;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
/**
 * Clase Llave almacena las tablas con los valores aleatorios y el orden de los caracteres originales y cifrados
 * con el fin de que la Clase Cypher pueda emplear estos valores en el proceso de cifrado y descifrado de un texto.
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
  private String nombreLlave;
  
  //Variables para importar llaves:
  private static DataInputStream r;
  private static String linea;
  private static ArrayList<String> importar = new ArrayList<String>();
  private static final String VERSION_LLAVE = "1.0";
  
  
  
  //#################################     CONSTRUCTOR LLAVE NUEVA     #################################\\
  
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
    reiniciaUsados();
    usados.clear();
    generaDesplazamiento();    
    nombreLlave = generaNombre();
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
   * @param i Posición
   */
  private void asignaValoresTablas(int azar, int i) {
    usados.set(azar, true);
    creaCaracteres(azar, i);
    numerosAleatorios.add(azar);
  }

  /**
   * Crea los caracteres con sus respectivos valores original y cifrado.
   * 
   * @param azar Número (int) generado aleatoriamente.
   * @param i Posición
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
    int azar = (int)(Math.random()*(int)(Caracteres.getLongitud()/2)+1986);
    for (int i=0; i<azar; i++) {
      desplazamiento.add((int)(Math.random()*(tablaCifrada.size()-1)+1));
    }
  }  
  
  /**
   * Genera el nombre de la llave.
   * 
   * @return
   */
  private String generaNombre() {
    String nombre = "|***|";
    nombre = creaCabeceraA(nombre); 
    nombre = creaCabeceraB(nombre);   
    nombre = creaCuerpo(nombre);    
    return nombre;
  }
  
  /**
   * Al generar el nombre guarda la primera parte de la información de la llave.
   * 
   * @param nombre
   * @return
   */
  private String creaCabeceraA(String nombre) {
    for (int i=0; i<numerosAleatorios.size(); i++) {                    //Cabecera A 
      nombre += Matematicas.conversionHexa(numerosAleatorios.get(i));      
      nombre += (i!=numerosAleatorios.size()-1) ? "|" : "\n|*-*|";
    }
    return nombre;
  }
  
  /**
   * Al generar el nombre guarda la segunda parte de la información de la llave.
   * 
   * @param nombre
   * @return
   */
  private String creaCabeceraB(String nombre) {
    for (int i=0; i<tablaCifrada.size(); i++) {                         //Cabecera B 
      nombre += Matematicas.conversionHexa((tablaCifrada.get(i)).getValor());      
      nombre += (i!=tablaCifrada.size()-1) ? "|" : "\n|-*-|";
    }
    return nombre;
  }
  
  /**
   * Al generar el nombre guarda la tercera parte de la información de la llave.
   * 
   * @param nombre
   * @return
   */
  private String creaCuerpo(String nombre) {
    for (int i=0; i<desplazamiento.size(); i++) {                       //Cuerpo
      nombre += Matematicas.conversionHexa(desplazamiento.get(i));      
      nombre += (i!=desplazamiento.size()-1) ? ":" : "";
    }
    return nombre;
  }
  
  //#################################     CONSTRUCTOR LLAVE IMPORTADA     #################################\\
  
  
  /**
   * Constructor para importar una llave
   *  
   * @throws IOException 
   * @throws versionLlaveIncorrecta 
   */
  public Llave(String ruta) throws IOException, versionLlaveIncorrecta {
    importaLlave(ruta);
  }  
  
  /**
   * Importa la llave desde un fichero.
   * 
   * @throws IOException 
   * @throws versionLlaveIncorrecta 
   * 
   */
  private void importaLlave(String ruta) throws IOException, versionLlaveIncorrecta {    //PENDIENTE DE REALIZAR!!!
    creaLector(ruta);
    leeFichero();
    creaLlave();
  }  
  
  /**
   * Lee el fichero con la información de la llave que se desea importar.
   * 
   * @throws IOException 
   * @throws versionLlaveIncorrecta 
   * 
   */
  private void leeFichero() throws IOException, versionLlaveIncorrecta {
    validaVersionLlave();
    guardaDatos();
  }
  
  /**
   * Crea la llave que se desea importar.
   */
  private void creaLlave() {
    importaCabeceraA();
    importaCabeceraB();
    importaCuerpo();
    nombreLlave = generaNombreImportado();
    importar.clear();
  }
  
  /**
   * Genera el nombre de una llave importada.
   * 
   * @return
   */
  private String generaNombreImportado() {
    return importar.get(0)+"\n"+importar.get(1)+"\n"+importar.get(2);
  }

  /**
   * Importa los valores de la tercera parte de la llave.
   */
  private void importaCuerpo() {
    linea=importar.get(2).substring(5, importar.get(0).length());
    for (int i=0; i<linea.length(); i+=3) {
      desplazamiento.add(Matematicas.hexaADecimal(linea.substring(i, i+2)));
    }
  }

  /**
   * Importa los valores de la segunda parte de la llave.
   */
  private void importaCabeceraB() {
    int k = 0;
    linea=importar.get(1).substring(5, importar.get(1).length());
    for (int i=0; i<linea.length(); i+=3) {
      (tablaCifrada.get(k)).setValor(Matematicas.hexaADecimal(linea.substring(i, i+2)));
      k++;
    }
  }

  /**
   * Importa los valores de la primera parte de la llave.
   */
  private void importaCabeceraA() {
    int k = 0;
    linea=importar.get(0).substring(5, importar.get(0).length());
    for (int i=0; i<linea.length(); i+=3) {
      tablaOriginal.add(new Caracteres(Matematicas.hexaADecimal(linea.substring(i, i+2))));
      tablaCifrada.add(new Caracteres(Matematicas.hexaADecimal(linea.substring(i, i+2))));
      (tablaOriginal.get(k)).setValor(k); 
      (tablaCifrada.get(k)).setValor(k);
      k++;
    }
  }

  /**
   * Almacena los datos que conforman las líneas del fichero con la información de la llave.
   * 
   * @throws IOException
   */
  private void guardaDatos() throws IOException {
    for (int i=1; i<4; i++) {
      linea = r.readUTF();
      importar.add(linea.replaceAll("\n", ""));
    }
    r.close();
  }

  /**
   * Valida que la versión de la llave que se desea importar sea la correcta.
   * 
   * @throws IOException
   * @throws versionLlaveIncorrecta
   */
  private void validaVersionLlave() throws IOException, versionLlaveIncorrecta {
    linea = r.readUTF();
    if (!linea.replaceAll("\n", "").equals("///"+VERSION_LLAVE+"\\\\\\"))
      throw new versionLlaveIncorrecta();
  }

  /**
   * Crea el puntero que leerá las líneas del fichero de la llave.
   *  
   * @param ruta
   * @throws FileNotFoundException 
   */
  private void creaLector(String ruta) throws FileNotFoundException {
    r = new DataInputStream(new FileInputStream(ruta));
  }
  
  
  //#################################     EXPORTAR LLAVE     #################################\\
  
  /**
   * 
   */
  private void exportaLlave(String ruta) {    //PENDIENTE DE REALIZAR!!!
    
  }
  
  
  //#######################################     GETTERS     #######################################\\  
  
  /**
   * 
   * @return
   */
  public String getNombreLlave() {
    return this.nombreLlave;
  }  
  
  /**
   * 
   * 
   * 
   * @param indice
   * @return
   */
  public int getDesplazamiento(int indice) {
    if (indice>=this.desplazamiento.size()) {
      indice %= this.desplazamiento.size();
      return this.desplazamiento.get(indice);
    } else {
      return this.desplazamiento.get(indice);
    }    
  }  
  
  /**
   * 
   * 
   * 
   * @param valor
   * @return
   */
  public String getCaracterCifrado(int valor) {
    String caracterCifrado = "";
    for (int i=0; i<this.tablaCifrada.size();i++) {
      if (valor==(this.tablaCifrada.get(i)).getValor()) {
        caracterCifrado = (this.tablaCifrada.get(i)).getCaracter();
        break;
      }
    }
    return caracterCifrado;
  }  
  
  
  public String getCaracterPosCifrado(int indice) {
    return (this.tablaCifrada.get(indice)).getCaracter();
  }
  
  public String getCaracterOriginal(int valor) {
    String caracterOriginal = "";
    for (int i=0; i<this.tablaOriginal.size();i++) {
      if (valor==(this.tablaOriginal.get(i)).getValor()) {
        caracterOriginal = (this.tablaOriginal.get(i)).getCaracter();
        break;
      }
    }
    return caracterOriginal;
  }  
  
  /**
   * 
   * @param valor
   * @return
   */
  public int getValorCifrado(int indice) {
    return (this.tablaCifrada.get(indice)).getValor();
  }
  
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
  
  
  public int getLongitudDesplazamiento() {
    return this.desplazamiento.size();
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
