package aplicacion;

// imports de las clases que usamos
import java.util.ArrayList;
import java.util.Scanner;

import modelo.Empleado;
import modelo.EmpleadoFijo;
import modelo.EmpleadoPorHora;


 /* CASO DE COMPROBACION (seccion 11, mes 3 = Marzo)
* Ana (fija, 4000 + 300) = Q 4300.00 y Luis (por hora, 25 x 120) = Q 3000.00.
* Resultado que mostro el programa:
*   1234567890101  |  Ana Gomez  |  Fijo  |  4300.00
*   9876543210101  |  Luis Sanchez  |  Por hora  |  3000.00
*   Total de trabajadores: 2
*   Total de nomina: Q 7300.00
*/


public class Main {

    // atributos de la clase, todos private
    private static Scanner entrada = new Scanner(System.in); // para leer del teclado

    // arreglo con los 12 meses del año, ya esta definido en el codigo
    private static String[] meses = {
        "Enero", "Febrero", "Marzo", "Abril",
        "Mayo", "Junio", "Julio", "Agosto",
        "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };

    // lista donde guardamos a todos los empleados (fijos y por hora juntos)
    private static ArrayList<Empleado> empleados = new ArrayList<Empleado>();

    public static void main(String[] args) {

        // pedimos el mes que se va a trabajar (ya validado entre 1 y 12)
        int numeroMes = pedirNumero("Ingrese el mes a trabajar (1-12): ", 1, 12);

        int opcionMenuIngresada;

        // repetimos el menu hasta que se seleccione la opcion 3
        do {
            limpiarPantalla();
            mostrarMenuPrincipal(numeroMes);
            opcionMenuIngresada = pedirNumero("Seleccione una opcion: ", 1, 3);

            // revisamos que opcion selecciono
            switch (opcionMenuIngresada) {
                case 1:
                    ingresarNomina(numeroMes);
                    break;

                case 2:
                    imprimirNomina(numeroMes);
                    break;

                case 3:
                    System.out.println("Gracias por usar el sistema de nomina. Hasta luego!");
                    break;
            }

        } while (opcionMenuIngresada != 3);

        entrada.close(); // cerramos el scanner
    }

    // MENUS 

    // muestra el titulo con el nombre del mes sacado del arreglo
    private static void mostrarTitulo(int numeroMes) {
        System.out.println("NOMINA DEL MES: " + meses[numeroMes - 1]); // -1 porque los indices empiezan en 0
    }

    // muestra las opciones del menu principal
    private static void mostrarMenuPrincipal(int numeroMes) {
        mostrarTitulo(numeroMes);
        System.out.println("1 - Ingreso de nomina");
        System.out.println("2 - Impresion de nomina");
        System.out.println("3 - Salir");
    }

    // simula limpiar la pantalla imprimiendo lineas en blanco
    private static void limpiarPantalla() {
        for (int i = 0; i < 4; i++) {
            System.out.println();
        }
    }

    // OPCION 1: INGRESO 

    // muestra el submenu y registra empleados hasta que el usuario quiera volver
    private static void ingresarNomina(int numeroMes) {
        int otro;

        do {
            limpiarPantalla();
            mostrarTitulo(numeroMes);
            System.out.println("INGRESO DE EMPLEADO A NOMINA");
            System.out.println("Trabajadores actuales = " + empleados.size()); // size() dice cuantos hay
            System.out.println("1 - Empleado fijo");
            System.out.println("2 - Empleado por hora");

            int tipo = pedirNumero("Seleccione el tipo de empleado: ", 1, 2);

            limpiarPantalla();
            mostrarTitulo(numeroMes);

            // aqui usamos if porque es solo para elegir que objeto crear
            Empleado nuevo;
            if (tipo == 1) {
                nuevo = capturarFijo();
            } else {
                nuevo = capturarPorHora();
            }

            // calcularPago() lo resuelve cada clase hija 
            System.out.println("Pago de " + nuevo.getNombre() + " = Q " + formatoDinero(nuevo.calcularPago()));
            System.out.println();

            // solo se guarda cuando ya todos los datos estaban bien
            empleados.add(nuevo);

            System.out.println("Empleado agregado correctamente.");
            System.out.println("Trabajadores actuales = " + empleados.size());
            System.out.println("Desea ingresar otro empleado?");
            System.out.println("1 - Si, ingresar otro empleado");
            System.out.println("2 - Volver al menu principal");
            otro = pedirNumero("Seleccione una opcion: ", 1, 2);

        } while (otro == 1);
    }

    // pide los datos de un empleado fijo y devuelve el objeto ya creado
    private static Empleado capturarFijo() {
        System.out.println("INGRESO DE EMPLEADO FIJO");
        String dpi = pedirDpi();
        String nombre = pedirNombre();
        double sueldo = pedirDecimal("Sueldo mensual (Q): ");
        double bonificacion = pedirDecimal("Bonificacion de este mes (Q): ");

        // los valores capturados se pasan como argumentos al constructor
        return new EmpleadoFijo(dpi, nombre, sueldo, bonificacion);
    }

    // pide los datos de un empleado por hora y devuelve el objeto ya creado
    private static Empleado capturarPorHora() {
        System.out.println("INGRESO DE EMPLEADO POR HORA");
        String dpi = pedirDpi();
        String nombre = pedirNombre();
        double pagoPorHora = pedirDecimal("Pago por hora (Q): ");
        double horas = pedirDecimal("Horas trabajadas este mes: ");

        return new EmpleadoPorHora(dpi, nombre, pagoPorHora, horas);
    }

    // OPCION 2: IMPRESION 

    // muestra el reporte de la nomina en pantalla
    private static void imprimirNomina(int numeroMes) {
        limpiarPantalla();
        mostrarTitulo(numeroMes);

        double total = 0; // variable local, solo existe en este metodo

        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados en la nomina de este mes");
        } else {
            System.out.println("DPI  |  Nombre  |  Tipo  |  Pago (Q)");

            // recorremos la lista, cada empleado calcula su propio pago
            for (Empleado e : empleados) {
                double pago = e.calcularPago(); // polimorfismo: no preguntamos el tipo
                total = total + pago;
                System.out.println(e.getDpi() + "  |  " + e.getNombre() + "  |  " + e.obtenerTipo() + "  |  " + formatoDinero(pago));
            }
        }

        System.out.println();
        System.out.println("Total de trabajadores: " + empleados.size());
        System.out.println("Total de nomina: Q " + formatoDinero(total));
        System.out.println();
        System.out.print("Presione Enter para volver al menu principal...");
        entrada.nextLine(); // espera el Enter
    }

    // METODOS PARA PEDIR DATOS 

    /* Pide un numero entero y lo vuelve a pedir hasta que este entre
       el minimo y el maximo. Devuelve el numero ya validado.
       mensaje, minimo y maximo son parametros del metodo. */
    private static int pedirNumero(String mensaje, int minimo, int maximo) {
        int numero = 0;
        boolean valido = false;

        while (!valido) {
            System.out.print(mensaje);

            // comprobamos si ingreso un numero entero
            if (entrada.hasNextInt()) {
                numero = entrada.nextInt();

                if (numero < minimo || numero > maximo) {
                    System.out.println("Error: debe ser un numero entre " + minimo + " y " + maximo);
                } else {
                    valido = true;
                }
            } else {
                // si escribe letras, mostramos un mensaje
                System.out.println("Error: debe ingresar un numero entero");
                entrada.next(); // descartamos lo que escribio
            }
        }

        entrada.nextLine(); // limpiamos el Enter que queda en el buffer
        return numero;
    }

    // pide un numero con decimales que sea 0 o mayor (igual que pedirNumero pero con double)
    private static double pedirDecimal(String mensaje) {
        double numero = 0;
        boolean valido = false;

        while (!valido) {
            System.out.print(mensaje);

            if (entrada.hasNextDouble()) {
                numero = entrada.nextDouble();

                if (numero < 0) {
                    System.out.println("Error: el valor no puede ser negativo");
                } else {
                    valido = true;
                }
            } else {
                System.out.println("Error: debe ingresar un numero");
                entrada.next(); // descartamos lo que escribio
            }
        }

        entrada.nextLine(); // limpiamos el Enter que queda en el buffer
        return numero;
    }

    // pide el DPI y revisa que tenga exactamente 13 digitos
    private static String pedirDpi() {
        String dpi = "";
        boolean valido = false;

        while (!valido) {
            System.out.print("DPI: ");
            dpi = entrada.nextLine().trim(); // trim quita los espacios de los lados

            if (dpi.length() == 13 && soloNumeros(dpi)) {
                valido = true;
            } else {
                System.out.println("Error: el DPI debe tener exactamente 13 digitos");
            }
        }

        return dpi;
    }

    /* Pide el nombre y lo vuelve a pedir hasta que no este vacio.
    Devuelve el nombre ya validado. */
 private static String pedirNombre() {

     // variables locales solo existen dentro de este metodo
     String nombre = "";
     boolean valido = false; // false = todavia no tenemos un nombre bueno

     // mientras NO sea valido, seguimos pidiendo el dato
     while (!valido) {
         System.out.print("Nombre: ");

         // nextLine lee toda la linea (con espacios, por eso sirve para nombres completos)
         // trim quita los espacios del principio y del final
         nombre = entrada.nextLine().trim();

         // si el usuario solo dio Enter o solo espacios, trim lo deja vacio
         if (nombre.isEmpty()) {
             System.out.println("Error: el nombre no puede estar vacio");
         } else {
             valido = true; // ahora si, el while termina
         }
     }

     // devolvemos el nombre al metodo que lo pidio (capturarFijo o capturarPorHora)
     return nombre;
 }

   
    /* Revisa que un texto tenga solo digitos del 0 al 9
    texto es el parametro: el dato que se va a revisar por ejemplo el DPI
    Devuelve true si todos son digitos y false si encuentra algo que no lo es. */
 private static boolean soloNumeros(String texto) {

     // recorremos el texto letra por letra, i es la posicion (0, 1, 2...)
     for (int i = 0; i < texto.length(); i++) {

         // char guarda una sola letra, numero o simbolo aqui la que esta en la posicion i
         char c = texto.charAt(i);

         // si el caracter esta fuera del rango '0' a '9' entonces no es un digito
         if (c < '0' || c > '9') {
             return false; // encontramos uno malo, salimos del metodo de una vez
         }
     }

     // si llegamos aqui, el for reviso todo y ningun caracter fallo
     return true;
 }
    

    // devuelve el monto como texto con 2 decimales ej: 4300.00
    private static String formatoDinero(double monto) {
        return String.format("%.2f", monto);
    }
}