package cifrado;

import java.util.Scanner;

public class EnigmaMain {
  public static void main(String[] args) throws ErrorSeleccionLlave {
    //Variables:
    int opcion = 0;
    int numLlave = 0;
    
    Scanner s = new Scanner(System.in);
    
    Cypher c = new Cypher();
    
    do {
      imprimeMenu();
      try {
        opcion = Integer.parseInt(s.nextLine());
      } catch (Exception e) {
        opcion = 0;
      }
            
      switch (opcion) {
        case 1:
          c.generaLlave();
          break;
        case 2:
          if (c.getNumeroLlaves()!=0) {
            System.out.println(c.toString());
          } else {
            System.out.println("Primero debe generar o cargar alguna llave.");
          }
          
          break;
        case 3:
          numLlave = seleccionaLlave(numLlave, s, c);          
          break;
        case 4:
          cifraMensaje(numLlave, s, c);
          break;
        case 5: 
          descifraMensaje(numLlave, s, c);          
          break;
        default:
          
      }
    } while(opcion!=6);
    
    
    
  }




  /**
   * @param numLlave
   * @param s
   * @param c
   */
  private static void descifraMensaje(int numLlave, Scanner s, Cypher c) {
    String mensaje;
    String cifrado;
    if (c.getNumeroLlaves()<1) {
      System.out.println("Primero debes generar una llave.");
    } else if (numLlave==0) {
      System.out.println("Selecciona una llave.");
    } else {
      System.out.println("Escriba el mensaje a descifrar: ");
    cifrado = s.nextLine();
    
    mensaje = c.desencripta(numLlave, cifrado);
    
    System.out.println(mensaje);
    }
  }




  /**
   * @param numLlave
   * @param s
   * @param c
   */
  private static void cifraMensaje(int numLlave, Scanner s, Cypher c) {
    String mensaje;
    String cifrado;
    if (c.getNumeroLlaves()<1) {
      System.out.println("Primero debes generar una llave.");
    } else if (numLlave==0) {
      System.out.println("Selecciona una llave.");
    } else {
      System.out.println("Escriba el mensaje a cifrar: ");
      mensaje = s.nextLine();
      
      cifrado = c.encripta(numLlave, mensaje);
      
      System.out.println(cifrado);
    }
  }




  /**
   * @param numLlave
   * @param s
   * @param c
   * @return
   */
  private static int seleccionaLlave(int numLlave, Scanner s, Cypher c) {
    if (c.getNumeroLlaves()!=0) {
      try {
        System.out.println("Selecciona una llave:");
        numLlave = Integer.parseInt(s.nextLine());
        if (numLlave<1 || numLlave>c.getNumeroLlaves()) {
          numLlave = 0;
          throw new ErrorSeleccionLlave();
        }
      } catch (ErrorSeleccionLlave esl) {
        System.out.println("No se ha podido seleccionar ninguna llave.");
      } catch (Exception e) {
        System.out.println("No se ha podido seleccionar ninguna llave.");
      }
    } else {
      System.out.println("Primero debe generar o cargar alguna llave.");
    }
    return numLlave;
  }
  
  
  
  
  private static void imprimeMenu() {
    System.out.println("(1) Genera una clave.");
    System.out.println("(2) Muestra llaves.");
    System.out.println("(3) Selecciona llave.");
    System.out.println("(4) Cifra un mensaje.");
    System.out.println("(5) Descifra un mensaje.");
    System.out.println("(6) Salir.");
  }
  
  
  
  
  
  
}
