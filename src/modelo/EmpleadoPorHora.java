package modelo;

/* Empleado por hora  hereda de Empleado dpi y nombre y agrega el pago por hora
   y las horas trabajadas. Su pago es pago por hora x horas trabajadas. */
public class EmpleadoPorHora extends Empleado {

    // atributos propios de esta clase double porque las horas pueden tener decimales
    private double pagoPorHora;
    private double horasTrabajadas;

    // constructor con parametros
    public EmpleadoPorHora(String dpi, String nombre, double pagoPorHora, double horasTrabajadas) {
        super(dpi, nombre); // inicializa los datos comunes en el padre
        this.pagoPorHora = pagoPorHora;
        this.horasTrabajadas = horasTrabajadas;
    }

    // getters
    public double getPagoPorHora() {
        return pagoPorHora;
    }

    public double getHorasTrabajadas() {
        return horasTrabajadas;
    }

    // sobrescribe el metodo abstracto del padre: multiplica en vez de sumar
    @Override
    public double calcularPago() {
        return pagoPorHora * horasTrabajadas;
    }

    @Override
    public String obtenerTipo() {
        return "Por hora";
    }
}