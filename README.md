# ENIGMA

### Sitio web con información detallada
```sh
Actualmente el sitio web está en desarrollo.
```

---

### Breve explicación del programa

**Programa realizado en Java** (actualmente en desarrollo) **que permite el cifrado y descifrado de cualquier texto de entrada**, bien por fichero .txt como por cualquier clase de input, **haciendo uso de una "llave"** que bien se habrá generado aleatoriamente o bien se habrá importado de otra anteriormente exportada.

Una vez generada una llave con información aleatoria, o importada de una llave anteriormente generada y exportada:

![Imagen con un breve fragmento de información de una llave de ejemplo](/img_markdown/ejemplo_llave.png)  
&nbsp; _Breve fragmento de información de una llave de ejemplo_

El programa tendrá la capacidad de recoger un texto, bien introducido en un textarea o en una consola de comandos o bien importado de un fichero de texto plano, y cifrarlo usando la información de la llave seleccionada y del algoritmo del programa. Dando como resultado algo similar a esto:

![Imagen con el poema del Señor de los Anillos sin cifrar](/img_markdown/anillo.png)  
&nbsp; _Poema del anillo del Señor de los anillos (sin cifrar)_ 

![Imagen con el poema del Señor de los Anillos cifrado](/img_markdown/anillo_cifrado.png)  
&nbsp; _Poema del anillo del Señor de los anillos tras un proceso de cifrado_

Finalmente, de igual manera, si obtenemos un texto cifrado y conocemos cuál fue la llave que se empleó para su cifrado, podremos revertir el proceso para recuperar la información original.
 

---

### Listado de objetivos cumplidos

Actualmente el programa ha logrado alcanzar los siguintes objetivos:

| Objetivos | Estado |
| ------ | ------ |
|Creación de llaves|:heavy_check_mark:|
|Proceso de cifrado desde un input|:heavy_check_mark:|
|Proceso de descifrado desde un input|:heavy_check_mark:|
|Capacidad de exportar llaves|:heavy_check_mark:|
|Capacidad de importar llaves|:heavy_check_mark:|
|Capacidad para leer y procesar ficheros .txt|:heavy_check_mark:|
|Proceso de cifrado desde .txt|:heavy_check_mark:|
|Proceso de descifrado desde .txt|:heavy_check_mark:|
|Capas de aleatoriedad (cifrado) >=3|:heavy_check_mark:|
|Capas de aleatoriedad (cifrado) >=5|:heavy_check_mark:|
|Capas de aleatoriedad (cifrado) >=7|:heavy_check_mark:|
|Capas de aleatoriedad (cifrado) >=10|:x:|
|Capas de aleatoriedad (cifrado) >=12|:x:|
|Capa de presentación (Entorno gráfico)|:x:|
|Capa de presentación (Consola de comandos)|:x:|