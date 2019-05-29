package enigma.cypher;

public class ErrorSeleccionLlave extends Exception{
  public ErrorSeleccionLlave() {
    System.err.println("La llave no existe.");
  }
}
