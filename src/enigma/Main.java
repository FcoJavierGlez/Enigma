package enigma;

import enigma.cypher.*;

import java.io.IOException;

public class Main {
    public static void main (String [] args) {
        Cypher c = new Cypher();

        final String KEY_NAME = "";

        try {
            // GENERACIÓN LLAVE \\
//            c.generaLlave();
//            c.seleccionaLlave(1);
//            c.exportaLlave("data\\keys\\" + KEY_NAME);

            // IMPORTAR LLAVE \\
            c.importaLlave("data\\keys\\" + KEY_NAME + ".kyph");
            c.seleccionaLlave(1);

            // CIFRAR FICHERO \\
            Texto.importaFichero("data\\messages\\mensaje.txt");
            c.encriptaTexto();
            Texto.exportaFichero("data\\messages\\mensaje_cifrado.txt");

            // DESCIFRAR FICHERO \\
            Texto.importaFichero("data\\messages\\mensaje_cifrado.txt");
            c.desencriptaTexto();
            Texto.exportaFichero("data\\messages\\mensaje_descifrado.txt");
        }
        catch (limiteLlavesException e) {} catch (IOException e) {}
        catch (ErrorSeleccionLlave e) {}
        catch (cabeceraInvalida e) {}
        catch (llaveDuplicada e) {}
        catch (versionLlaveIncorrecta e) {}
    }
}
