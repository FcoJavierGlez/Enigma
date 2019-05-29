package enigma.cypher;

import java.util.ArrayList;
/**
 * Clase Llave almacena las tablas con los valores aleatorios y el orden de los caracteres originales y cifrados
 * con el fin de que la Clase Cypher pueda emplear estos valores en el proceso de cifrado y descifrado de un texto.
 * 
 * @author Francisco Javier González Sabariego
 *
 */
public class Llave {
  
  private ArrayList<Caracteres> tablaOriginal = new ArrayList<Caracteres>();  //Tabla de caracteres con valores originales.
  private ArrayList<Integer> numerosAleatorios = new ArrayList<Integer>();    //Tabla de valores aleatorios con los que se genera la tabla original.
  private ArrayList<Caracteres> tablaCifrada = new ArrayList<Caracteres>();   //Tabla de caracteres con valores aleatorios.
  private ArrayList<Boolean> usados = new ArrayList<Boolean>();               //Tabla de valores booleanos del mismo tamaño que las tablas de caracteres
  private ArrayList<Integer> desplazamiento = new ArrayList<Integer>();       //Tabla con los desplazamientos de los caracteres del texto a cifrar.
  private String nombreLlave;
  
  
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
  
  //#################################     CONSTRUCTOR LLAVE IMPORTADA     #################################\\
  
  
  /**
   * Constructor cargando una llave importada 
   */
  public Llave(String llave) {
    importaLlave(llave);
  }  
  
  /**
   * 
   */
  private void importaLlave(String llave) {    //PENDIENTE DE REALIZAR!!!
    
  }  
  
  /**
   * 
   */
  private void exportaLlave() {    //PENDIENTE DE REALIZAR!!!
    
  }  
  
  /**
   * Genera la tabla de booleanos "usados".
   */
  private void generarUsados() {
    for (int i=0; i<Caracteres.getLongitud(); i++) {
      usados.add(false);
    }
  }
  
  
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
      
      usados.set(azar, true);
      tablaOriginal.add(new Caracteres(azar));    //FUSIONAR TABLAS EN UNA Y AÑADIR VALORCIFRADO A CADA CARACTER COMO ATRIBUTO!!!
      tablaCifrada.add(new Caracteres(azar));
      (tablaOriginal.get(i)).setValor(i);         //FUSIONAR TABLAS EN UNA Y AÑADIR VALORCIFRADO A CADA CARACTER COMO ATRIBUTO!!!
      (tablaCifrada.get(i)).setValor(i);
      numerosAleatorios.add(azar);
    }
    reiniciaUsados();
    cifraTabla();    
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
    //int azar = (int)(Math.random()*((int)(Caracteres.getLongitud()/2)-1)+(int)(Caracteres.getLongitud()/2));
    int azar = (int)(Math.random()*(int)(Caracteres.getLongitud()/2)+1986);
    int desplaza;
    
    for (int i=0; i<azar; i++) {
      desplaza = (int)(Math.random()*(tablaCifrada.size()-1)+1);
      desplazamiento.add(desplaza);
    }
  }  
  
  /**
   * 
   * @return
   */
  private String generaNombre() {   //REFACTORIZAR CUANDO TERMINE
    String nombre = "|***|";
    for (int i=0; i<numerosAleatorios.size(); i++) {                    //Cabecera A 
      nombre += Matematicas.conversionHexa(numerosAleatorios.get(i));      
      if (i!=numerosAleatorios.size()-1) {
        nombre += "|";
      } else {
        nombre += "\n|*-*|";
      }
    } 
    for (int i=0; i<tablaCifrada.size(); i++) {                         //Cabecera B 
      nombre += Matematicas.conversionHexa((tablaCifrada.get(i)).getValor());      
      if (i!=tablaCifrada.size()-1) {
        nombre += "|";
      } else {
        nombre += "\n|-*-|";
      }
    }   
    for (int i=0; i<desplazamiento.size(); i++) {                       //Cuerpo
      nombre += Matematicas.conversionHexa(desplazamiento.get(i));      
      if (i!=desplazamiento.size()-1) {
         nombre += ":";
      }
    }    
    return nombre;
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
