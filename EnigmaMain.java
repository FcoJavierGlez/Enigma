package cifrado;

import java.util.Scanner;

public class EnigmaMain {
  public static void main(String[] args) {
    //    
    String mensaje = "";
    String cifrado = "";
    int opcion = 0;
    
    Scanner s = new Scanner(System.in);
    
    Cypher c = new Cypher();
    
    do {
      imprimeMenu();
      opcion = s.nextInt();
      s.nextLine();
      
      switch (opcion) {
        case 1:
          c.generaLlave();
          break;
        case 2:
          System.out.println(c.toString());
          break;
        case 3:
          if (c.getNumeroLlaves()<1) {
            System.out.println("Primero debes generar una llave.");
          } else {
            System.out.println("Escriba el mensaje a cifrar: ");
            mensaje = s.nextLine();
            
            cifrado = c.encripta(mensaje);
            
            System.out.println(cifrado);
          }
          break;
        case 4:          
          System.out.println("Escriba el mensaje a descifrar: ");
          cifrado = s.nextLine();
          
          mensaje = c.desencripta(cifrado);
          
          System.out.println(mensaje);
          break;
        default:
          
      }
    } while(opcion!=5);
    
    
    
  }
  
  
  
  
  private static void imprimeMenu() {
    System.out.println("(1) Genera una clave.");
    System.out.println("(2) Muestra llaves.");
    System.out.println("(3) Cifra un mensaje.");
    System.out.println("(4) Descifra un mensaje.");
    System.out.println("(5) Salir.");
  }
  
  
  
  
  
  
}
