package cifrado;

public class ErrorSeleccionLlave extends Exception{
  public ErrorSeleccionLlave() {
    System.out.println("La llave no existe.");
  }
}
