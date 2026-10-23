package modelo;

/* Clase abstracta: guarda los datos que tienen todos los empleados como dpi y nombre
   no se pueden crear objetos de esta clase con new, solo de sus hijas. */
public abstract class Empleado {

    // atributos private
    private String dpi;
    private String nombre;

    // constructor con parametros, se llama igual que la clase y no tiene tipo de retorno
    public Empleado(String dpi, String nombre) {
        // this.dpi es el atributo, dpi es el parametro
        this.dpi = dpi;
        this.nombre = nombre;
    }

    // getters para consultar los datos desde otras clases
    public String getDpi() {
        return dpi;
    }

    public String getNombre() {
        return nombre;
    }

    // metodos abstractos aqui no llevan codigo, cada clase hija lo resuelve a su manera
    public abstract double calcularPago();

    public abstract String obtenerTipo();
}