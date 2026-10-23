package modelo;

/* Empleado fijo: hereda de Empleado dpi y nombre y agrega su sueldo y su bono
   Su pago es sueldo mensual + bono. */
public class EmpleadoFijo extends Empleado {

    // atributos propios de esta clase
    private double sueldoMensual;
    private double bono;

    // constructor los 4 son parametros que recibe
    public EmpleadoFijo(String dpi, String nombre, double sueldoMensual, double bono) {
        super(dpi, nombre); // le pasa dpi y nombre al padre Empleado)
        this.sueldoMensual = sueldoMensual;
        this.bono = bono;
    }

    // getters esto nos permite leer y recuperar el valor de un atributo privado de una clase 
    public double getSueldoMensual() {
        return sueldoMensual;
    }

    public double getBono() {
        return bono;
    }

    // sobrescribe el metodo abstracto del padre: suma sueldo y bono
    @Override
    public double calcularPago() {
        return sueldoMensual + bono;
    }

    // texto que se muestra en la columna Tipo del reporte
    @Override
    public String obtenerTipo() {
        return "Fijo";
    }
}