package cifrado;

import java.util.Scanner;

public class Matematicas {
  
  static final double PI = 3.14159265359;
  
  
  
  /**
   * Muestra si el número introduce es o no capicúa.
   * @param numeroEntrada
   * @return
   */
  public static boolean esCapicua (int numeroEntrada) {
    String cadena=Integer.toString(numeroEntrada);
    String cadena2="";
    boolean salida=false;
    
    cadena2=voltea(numeroEntrada);
    
    if (cadena.equals(cadena2)) {
      salida = true;
    }
    return salida;
  }
  
  
  
  /**
   * Muestra si el número introducido es o no primo.
   * @param entrada
   * @return
   */
  public static boolean esPrimo(int entrada) {
    int divisor=2;
    boolean primo=true;
    
    while (divisor<=Math.sqrt(entrada) || entrada==0 || entrada==1) {
      if (entrada%divisor==0 || entrada==1) {
        primo=false;
        break;
      }
      divisor++;
    }
    return primo;
  }
  
  
  
  /**
   * Retorna el número primo consecutivo al número que hayamos insertado.
   * @param entrada
   * @return
   */
  public static int siguientePrimo (int entrada) {
    int divisor=2;
    int salida=0;
    
    if (esPrimo(entrada)) {
      entrada++;
    }
    
    while (divisor<=entrada) {
      if (entrada%divisor==0 && divisor!=entrada) {
        entrada++;
        divisor=2;
      } else if (entrada%divisor==0 && divisor==entrada && entrada!=0) {
        salida=entrada;
      }
      divisor++;
    }
    
    if (entrada==0 || entrada==1) {
      salida=2;
    }
    
    
    return salida;
  }
  
  
  
  /**
   * Retorna el número del elemento elegido de la secueccia Fibonacci.
   * @param entrada
   * @return
   */
  public static int elementoFibonacci (int entrada) {
    int num1=1;
    int num2=1;
    for (int i=1; i<=entrada; i++) {
      if (i==1) {
        num2=1;
      } else if (i==2) {
        num2=1;
      } else {
        num2+=num1;
        num1=num2-num1;
      }
    }
    return num2;
  }
  
  
  /**
   * Calcular el factorial de un número:
   * 
   * El número entra en un bucle for que va en orden descendente incrementando la variable multiplicándola
   * por sí misma -1, el bucle cesa al llegar a 1.
   * 
   * @param numero
   * @return
   */
  public static int numFactorial (int numero) {
      for (int i=numero-1; i>1; i--) {
        numero*=i;
      }
      return numero;
  }
  
  
  
  /**
   * Retorna el resultado de una potencia.
   * @param base
   * @param exponente
   * @return
   */
  public static int potencia (int base, int exponente) {
    int resultado=base;
    
    if (exponente==0) {
      base=1;
    } else if(exponente==1) {
      base*=1;
    } else {
      for (int i=exponente; i>1; i--) {
        resultado*=base;
      }
    }
    return resultado;
  }
  
  
  
  /**
   * Muestra la longitud total del número introducido.
   * @param entrada
   * @return
   */
  public static int digitos (int entrada) {
    String cadena=Integer.toString(entrada);
    int salida=cadena.length();
    
    return salida;
  }
  
  
  
  /**
   * Retorna una cadena invertida.
   * @param entero
   * @return
   */
  public static String voltea(int entero) {     //Invertimos el orden de la cadena
    String cadena=Integer.toString(entero);
    String salida="";
    for (int i=cadena.length()-1; i>-1; i--) {     //Leemos la cadena y almacenamos los caracteres en variable salida
      salida+=cadena.charAt(i);
    }
    return salida;
  }
  
  
  
  /**
   * Muestra el dígito ubicado en la posición seleccionada dentro de un número.
   * @param numero
   * @param digito
   * @return
   */
  public static int digitoN (int numero, int digito) {
    String cadena;
    int salida=-1;
    
    cadena=Integer.toString(numero);
    
    //Si el valor del dígito es correcto buscamos dentro de la cadena la posición indicada y la imprimimos:
    for (int i=0; i<=digito; i++) {
      if (i==digito) {
        salida=Integer.parseInt(cadena.substring(i,i+1));
      }
    }
    return salida;
  }
  
  
  
  
  /**
   * Retorna la posición del dígito buscado dentro de un número.
   * @param entrada
   * @param numeroBuscado
   * @return
   */
  public static int posicionDeDigito(int entrada, int numeroBuscado) {
    String cadena=Integer.toString(entrada);
    String comprueba=Integer.toString(numeroBuscado);
    int salida=-1;
    
    for (int i=0; i<cadena.length(); i++) {
      if (comprueba.equals(cadena.substring(i,i+1))) {
        salida=i+1;
        break;
      }
    }
    return salida;
  }
  
  
  
  /**
   * Elimina los primeros dígitos del número insertado.
   * @param entrada
   * @param digito
   * @return
   */
  public static int quitaPorDelante(int entrada, int digito) {
    //Convertimos entrada en cadena:
    String cadena = Integer.toString(entrada);
    String cadena2="";
    int salida;
    
    /* Tres posibilidades:
     * 
     * (1) Si la cantidad de digitos a borrar es superior a la cantidad de números
     * retorna un valor negativo como diferencia entre ambos.
     * (2) Si la cantidad de digitos a borrar es inferior a 1 devuelve el número de entrada.
     * (3) Si ninguno de los casos anteriores es cierto entramos el número convertido en cadena
     * a un bucle for para concatenar en la variable cadena2 los números restantes
     * tras la posición inicial del dígito.
     */
    if (digito>cadena.length()) {
      return (cadena.length()-digito);
    } else if (digito<1) {
      return entrada;
    } else {
      for (int i=digito; i<cadena.length(); i++) {
        cadena2+=cadena.charAt(i);
      }
    }
    
    //Finalmente retornamos el resultado de reconvertir a int la variable cadena2:
    salida=Integer.parseInt(cadena2);
    
    return salida;
  }
  
  
  
  /**
   * Elimina los últimos dígitos del número insertado.
   * @param entrada
   * @param digito
   * @return
   */
  public static int quitaPorDetras(int entrada, int digito) {
    String cadena = Integer.toString(entrada);
    String cadena2="";
    
    if (digito>cadena.length()) {
      return (cadena.length()-digito);
    } else if (digito<0) {
      return entrada;
    } else {
      for (int i=1; i<=cadena.length()-digito; i++) {
        cadena2+=cadena.charAt(i-1);
      }
    }
    
    //Finalmente retornamos el resultado de reconvertir a int la variable cadena2:
    int salida=Integer.parseInt(cadena2);
    
    return salida;
  }
  
  
  
  /**
   * Une por detrás de un número el dígito que hayamos introducido.
   * @param numero
   * @param digito
   * @return
   */
  public static int pegaPorDetras(int numero, int digito) {
    //Creamos una variable cadena y concatenamos la conversión a cadena de numero1+numero2
    String cadenaNumero=Integer.toString(numero);
    String cadenaDigito=Integer.toString(digito);
    
    //Comprobamos que ha introduci un dígito, de lo contrario damos un error y cerramos:
    if (cadenaDigito.length()>1) {
      System.out.print("\n\nERROR. Ha insertado más de un dígito a añadir.");
      System.exit(1);
    } else {
      cadenaNumero+=cadenaDigito;
    }
    
    //Creamos variable de tipo int salida y convertimos el total de cadena a entero.
    int salida=Integer.parseInt(cadenaNumero);
    
    //Retornamos la salida:
    return salida;
  }
  
  
  
  /**
   * Une por delante de un número el dígito que hayamos introducido.
   * @param numero
   * @param digito
   * @return
   */
  public static int pegaPorDelante(int numero, int digito) {
    //Creamos una variable cadena y concatenamos la conversión a cadena de numero1+numero2
    String cadenaNumero=Integer.toString(numero);
    String cadenaDigito=Integer.toString(digito);
    
    //Comprobamos que ha introduci un dígito, de lo contrario damos un error y cerramos:
    if (cadenaDigito.length()>1) {
      System.out.print("\n\nERROR. Ha insertado más de un dígito a añadir.");
      System.exit(1);;
    } else {
      cadenaNumero=cadenaDigito+cadenaNumero;
    }
    
    //Creamos variable de tipo int salida y convertimos el total de cadena a entero.
    int salida=Integer.parseInt(cadenaNumero);
    
    //Retornamos la salida:
    return salida;
  }
  
  
  
  /**
   * Devuelve el trozo de número correspondiente dentro de la posición inicial y final de un número dado.
   * @param numero
   * @param posicionInicial
   * @param posicionFinal
   * @return
   */
  public static int trozoDeNumero(int numero, int posicionInicial, int posicionFinal) {
    //Creamos dos variables de tipo String para trabajar con cadenas:
    String cadena = Integer.toString(numero);
    String cadena2="";
    
    //Comprobamos que la posición inicial no sea inferior a 1, de serlo asignamos valor 1:
    if (posicionInicial<1) {
      posicionInicial=1;
    }
    
    //Comprobamos que laposición final no sea superior a la longitud del número, de serlo asignamos la longitud:
    if (posicionFinal>cadena.length()) {
      posicionFinal=cadena.length();
    }
    
    //Comprobamos que la posición final no sea superior a la inicial, de serlo damos error y cerramos:
    if (posicionFinal<posicionInicial) {
      System.out.println("\n\nERROR. La posición final no puede ser inferior a la inicial.");
      System.exit(1);
    }
    
    //Concatenamos la sección del número elegida:
    for (int i=posicionInicial-1; i<posicionFinal; i++) {
      cadena2+=cadena.charAt(i);
    }
    
    //Finalmente convertimos el resultado a entero y lo retornamos:
    int salida = Integer.parseInt(cadena2);
    return salida;
  }
  
  
  
  /**
   * Dados dos números de entrada los juntamos en uno solo y retornamos el resultado.
   * @param numeroInicial
   * @param numeroFinal
   * @return
   */
  public static int juntaNumeros(int numeroInicial, int numeroFinal) {
    //Convertimos y concatenamos los números de entrada a cadenas:
    String cadenaInicial = Integer.toString(numeroInicial)+Integer.toString(numeroFinal);
    
    //Pasamos el resultado a entero y retornamos:
    int salida = Integer.parseInt(cadenaInicial);
    return salida;
  }
  
  
  
  /**
   * Comprueba si el número introducido es binario.
   * @param numero
   * @return
   */
  public static boolean esBinario(String cadena) {
    boolean salida=true;
    
    //Comprobamos medianto un bucle for si todos los elementos del números son 0 ó 1:
    for (int i=0; i<cadena.length(); i++) {
      if (!(cadena.substring(i, i+1).equals("0") || cadena.substring(i, i+1).equals("1"))) {
        salida=false;
        break;
      }
    }
    
    //Retornamos salida:
    return salida;
  }
  
  
  
  
  /**
   * Recibe un número y lo convierte a binario.
   * @param numero
   * @return
   */
  public static String conversionBinario (int numero) {
    String resultado="";
    String salida="";
    
    while (numero>0) {
      resultado+= Integer.toString(numero%2);
      numero/=2;
    }
    
    if (resultado.length()==0) {
      resultado="00";
    } else if (resultado.equals("1")) {
      resultado+="0";
    }
    
    salida=invertirCadena(resultado);
    
    return salida;
  }
  
  
  
  /**
   * Pasa un número en base binaria a otro en base decimal.
   * @param numero
   * @return
   */
  public static int binarioADecimal(String cadena) {
    int longitud=cadena.length();
    int salida=0;
    
    if (esBinario(cadena)) {
      for (int i=0; i<longitud; i++) {
        if (cadena.substring(i,i+1).equals("1")) {
          salida+=Math.pow(2, longitud-i-1);
        }
      }
    } else {
      System.out.print("\n\nEl número introducido no es binario.");
      System.out.print("\n\nFIN DE PROGRAMA.");
      System.exit(1);
    }
    
    return salida;
  }
  
  
  
  /** 
   * Esta función recibe una cadena y la invierte:
   * 
   *   -La cadena entra en un bucle cuya variable de control equivale a la longitud de la cadena.
   *   -Y caracter a caracter va concatenando desde el final de la cadena en una variable String llamada salida.
   * 
   * @param cadena
   * @return
   */
  public static String invertirCadena(String cadena) {     //Invertimos el orden de la cadena
    String salida="";
    for (int i=cadena.length()-1; i>-1; i--) {     //Leemos la cadena y almacenamos los caracteres en variable salida
      salida+=cadena.charAt(i);
    }
    return salida;
  }
  
  
  
  /** 
   * Esta función se encarga de convertir un número de base decimal a hexadecimal:
   * 
   *   -Obtiene el resultado del módulo de dividir el número entre 16.
   *    Mientras el módulo dé un resultado comprendido entre 10-15 concatenamos la letra correspondiente A-F.
   *    Pero si su módulo es inferior concatenamos el resultado numérico.
   *    
   *    Acto seguido divide el resultado entre 16 y repite el proceso mientras el resultado sea mayor que 0.
   *   
   *   -Si el número es menor que 16 y su módulo no da un resultado comprendido 10-15 concatena su valor directamente.
   * 
   * @param numero
   * @return
   */
  public static String conversionHexa (int numero) {
    String resultado="";
    String salida="";
    
    if (numero==0) {
      resultado += Integer.toString(numero);
    }
    
    while (numero>0) {
      if (numero%16==10) {
        resultado+="A";
      } else if (numero%16==11) {
        resultado+="B";
      } else if (numero%16==12) {
        resultado+="C";
      } else if (numero%16==13) {
        resultado+="D";
      } else if (numero%16==14) {
        resultado+="E";
      } else if (numero%16==15) {
        resultado+="F";
      }  else if(numero%16==0) {
        resultado+="0";
      } else if (numero>16){  //Si las condiciones anteriores no se cumplen haz el módulo que concatenará un número
        resultado+= Integer.toString(numero%16);
      } else {                       //Si es menor que 16 y su módulo no equivale a 10-15 entonces guarda el propio número
        resultado+= Integer.toString(numero);
      }
      numero/=16;
    }
    
    if (resultado.length()==1) {
      resultado += "0";
    }
     
    salida=invertirCadena(resultado);
     
    return salida;
  }
  
  
  
  /**
   * Comprueba que el valor introducido sea en base hexadecimal.
   * @param numero
   * @return
   */
  public static boolean esHexa(String cadena) {
    boolean salida=true;
    String contiene="0123456789AaBbCcDdEeFf";
    
    /*
     * Comprobamos medianto un bucle for si todos los elementos del número están comprendidos
     * en cualquiera de los elementos de la subcadena. De lo contrario se retorna un -1 entra en la condición
     * y asignamos un valor falso a salida y rompemos el bucle.
     */
    for (int i=0; i<cadena.length(); i++) {
      if (contiene.indexOf(cadena.substring(i, i+1))<0) {
        salida=false;
        break;
      }
    }
    
    //Retornamos salida:
    return salida;
  }
  
  
  
  
  /**
   * Convierte el valor hexadecimal en decimal.
   * @param numero
   * @return
   */
  public static int hexaADecimal(String cadena) {
    int longitud=cadena.length();
    int digito=0;
    int salida=0;
    
    /*
     * Comprobamos si es hexadecimal llamando a la función esHexa():
     * 
     * Si es hexa decimal debemos reconvertir las letras a su valor en entero y los caracteres numéricos
     * a su correspondiente valor.
     * 
     * Cada fin de iteración sumamos a salida el valor número multiplicado por la base 16 elevada
     * a su correspondiente posición, siendo exponente cero la derecha del número, es decir, la posición
     * correspondiente a las unidades.
     */
    if (esHexa(cadena)) {
      for (int i=0; i<longitud; i++) {
        if (cadena.substring(i, i+1).equals("A") || cadena.substring(i, i+1).equals("a")) {
          digito=10;
        } else if (cadena.substring(i, i+1).equals("B") || cadena.substring(i, i+1).equals("b")) {
          digito=11;
        } else if (cadena.substring(i, i+1).equals("C") || cadena.substring(i, i+1).equals("c")) {
          digito=12;
        } else if(cadena.substring(i, i+1).equals("D") || cadena.substring(i, i+1).equals("d")) {
          digito=13;
        } else if (cadena.substring(i, i+1).equals("E") || cadena.substring(i, i+1).equals("e")) {
          digito=14;
        } else if (cadena.substring(i, i+1).equals("F") || cadena.substring(i, i+1).equals("f")) {
          digito=15;
        } else {
          digito=Integer.parseInt(cadena.substring(i, i+1));
        }
        salida+=digito*Math.pow(16, longitud-i-1);
      }
    } else {
      System.out.print("\n\nEl número introducido no es binario.");
      System.out.print("\n\nFIN DE PROGRAMA.");
      System.exit(1);
    }
    
    //Retornamos salida:
    return salida;
  }
  
  
  
  /**
   * Recibe un número y lo convierte a base octal.
   * @param numero
   * @return
   */
  public static String conversionOctal (int numero) {
    String resultado="";
    String salida="";
    
    while (numero>0) {
      resultado+= Integer.toString(numero%8);
      numero/=8;
    }
    
    salida=invertirCadena(resultado);
    
    return salida;
  }
  
  
  
  /**
   * Comprueba si el valor introducido está en base octal.
   * @param numero
   * @return
   */
  public static boolean esOctal(String cadena) {
    boolean salida=true;
    String contiene="01234567";
    
    /*
     * Comprobamos medianto un bucle for si todos los elementos del número están comprendidos
     * en cualquiera de los elementos de la subcadena. De lo contrario se retorna un -1 entra en la condición
     * y asignamos un valor falso a salida y rompemos el bucle.
     */
    for (int i=0; i<cadena.length(); i++) {
      if (contiene.indexOf(cadena.substring(i, i+1))<0) {
        salida=false;
        break;
      }
    }
    
    //Retornamos salida:
    return salida;
  }
  
  
  
  /**
   * Convierte el valor de entrada de base octal a base decimal.
   * @param numero
   * @return
   */
  public static int octalADecimal(String cadena) {
    int longitud=cadena.length();
    int salida=0;
    
    if (esOctal(cadena)) {
      for (int i=0; i<longitud; i++) {
        salida+=Integer.parseInt(cadena.substring(i, i+1))*Math.pow(8, longitud-i-1);
      }
    } else {
      System.out.print("\n\nEl número introducido no es binario.");
      System.out.print("\n\nFIN DE PROGRAMA.");
      System.exit(1);
    }
    
    //Retornamos salida:
    return salida;
  }
  
  
  
  /**
   * 
   * @param numero
   * @return
   */
  public static String convierteEnLetras(int numero) {
    String cadena = Integer.toString(numero);
    String salida = "";
    String [] numerosEnLetras = new String[10];
    
    numerosEnLetras[0]="cero";
    numerosEnLetras[1]="uno";
    numerosEnLetras[2]="dos";
    numerosEnLetras[3]="tres";
    numerosEnLetras[4]="cuatro";
    numerosEnLetras[5]="cinco";
    numerosEnLetras[6]="seis";
    numerosEnLetras[7]="siete";
    numerosEnLetras[8]="ocho";
    numerosEnLetras[9]="nueve";
    
    
    for (int i=0; i<cadena.length(); i++) {
      salida+=numerosEnLetras[Integer.parseInt(cadena.substring(i,i+1))];
      if (i!=cadena.length()-1) {
        salida+=", ";
      }
    }
    
    return salida;
  }
  
  
  
  
  
}
