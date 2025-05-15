package enigma;

import enigma.cypher.*;
import enigma.cypher.error.*;

import java.io.IOException;

public class Main {
    public static void main (String [] args) {
        Cypher c = new Cypher();

        final String KEY_NAME = "Ares";
        final String MESSAGE_NAME = "anillos";


        /**
         * Bloque 1: Generación de llave.
         *
         * El primer paso necesario es generar una llave en caso de no poseer ninguna.
         *
         * Asigna nombre a la llave en la constante KEY_NAME y desbloquea el primer bloque
         *
         */
        /*      BLOQUE 1: GENERACIÓN DE LLAVE      */
        try {
            c.generaLlave();
            c.seleccionaLlave(1);
            c.exportaLlave("data\\keys\\" + KEY_NAME);
        }
        catch (limiteLlavesException | IOException | ErrorSeleccionLlave e) {}



        /**
         * Bloque 2: Importar llave.
         *
         * En caso de poseer una o más llaves en nuestro directorio de llaves hay que importar la que queramos usar para
         * que sea cargada por el programa y pueda usar su información en el proceso de cifrado / descifrado
         *
         */
        /*      BLOQUE 2: IMPORTAR LLAVE      */
        try {
            c.importaLlave("data\\keys\\" + KEY_NAME + ".kyph");
            c.seleccionaLlave(1);
        }
        catch (limiteLlavesException | IOException | ErrorSeleccionLlave | cabeceraInvalida | llaveDuplicada | versionLlaveIncorrecta e) {}



        /**
         * Bloque 3: Cifrar mensaje.
         *
         * Una vez que haya una llave cargada en el sistema podemos proceder a cifrar un mensaje seleccionado del directorio
         * de messages. En caso de querer descifrar mensajes en vez de cifrarlos comentar este bloque.
         *
         */
        /*      BLOQUE 3: CIFRAR MENSAJE      */
        try {
            Texto.importaFichero("data\\messages\\" + MESSAGE_NAME + ".txt");
            c.encriptaTexto();
            Texto.exportaFichero("data\\messages\\" + MESSAGE_NAME + "_cifrado.txt");
        } catch (IOException | ErrorSeleccionLlave e) {}


        /**
         * Bloque 4: Descifrar mensaje.
         *
         * Una vez que haya una llave cargada en el sistema podemos proceder a descifrar un mensaje seleccionado del directorio
         * de messages. En caso de querer cifrar mensajes en vez de descifrarlos comentar este bloque.
         *
         */
        /*      BLOQUE 4: DESCIFRAR MENSAJE      */
//        try {
//            Texto.importaFichero("data\\messages\\" + MESSAGE_NAME + "_cifrado.txt");
//            c.desencriptaTexto();
//            Texto.exportaFichero("data\\messages\\" + MESSAGE_NAME + "_descifrado.txt");
//        } catch (IOException | ErrorSeleccionLlave e) {}


    }
}
