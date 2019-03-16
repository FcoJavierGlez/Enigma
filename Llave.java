package cifrado;

import java.awt.image.ConvolveOp;
import java.util.ArrayList;

public class Llave {
  
  private ArrayList<Caracteres> tablaOriginal = new ArrayList<Caracteres>();  //Tabla de caracteres con valores originales.
  private ArrayList<Integer> numerosAleatorios = new ArrayList<Integer>();    //Tabla de valores aleatorios con los que se genera la tabla original.
  private ArrayList<Caracteres> tablaCifrada = new ArrayList<Caracteres>();   //Tabla de caracteres con valores aleatorios.
  private ArrayList<Boolean> usados = new ArrayList<Boolean>();               //Tabla de valores booleanos del mismo tamaño que las tablas de caracteres
  private ArrayList<Integer> desplazamiento = new ArrayList<Integer>();       //Tabla con los desplazamientos de los caracteres del texto a cifrar.
  private String nombreLlave;
  
  
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
    numerosAleatorios.clear();
    nombreLlave = generaNombre();
  }  
  
  /**
   * Constructor cargando una llave importada 
   */
  public Llave(String llave) {
    cargaLlave(llave);
  }  
  
  /**
   * 
   */
  private void cargaLlave(String llave) {
    
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
      tablaOriginal.add(new Caracteres(azar));
      tablaCifrada.add(new Caracteres(azar));
      (tablaOriginal.get(i)).setValor(i);
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
  private String generaNombre() {
    String nombre = "|***|";    
    for (int i=0; i<tablaCifrada.size(); i++) {    
      nombre += Matematicas.conversionHexa((tablaCifrada.get(i)).getValor());      
      if (i!=tablaCifrada.size()-1) {
        nombre += "|";
      } else {
        nombre += "\n|**|";
      }
    }    
    for (int i=0; i<desplazamiento.size(); i++) {
      nombre += Matematicas.conversionHexa(desplazamiento.get(i));      
      if (i!=desplazamiento.size()-1) {
         nombre += ":";
      }
    }    
    return nombre;
  }
  
  
  
  //#######################################   GETTERS   #######################################\\  
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
  
  public String toString() {
    return this.nombreLlave;
  }
  
}
