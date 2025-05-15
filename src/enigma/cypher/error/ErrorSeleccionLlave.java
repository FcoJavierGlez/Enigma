package enigma.cypher.error;

/**
 * Se lanzará esta excepción cuando no haya ninguna llave seleccionada.
 * 
 * @author Francisco Javier González Sabariego
 *
 */
public class ErrorSeleccionLlave extends Exception{
  public ErrorSeleccionLlave() {
    System.err.println("La llave no existe.");
  }
}
