package org.example;

import java.io.IOException;


public class Vehiculo implements Runnable {
    private String matricula;
    private long tiempo;
    private tipoVehiculo tipo;
    private int bateria;
    private boolean esDPI = false;
    private Parking parking;
    private MaquinaPago maquina;

    /**
     * Enum que define los tipos de vehiculo, debido a su estrecha relacion con vehiculo lo hemos mantenido dentro de
     * la clase vehiculo
     */
    public enum tipoVehiculo {COCHE, MOTO, ELECTRICO}

    /**
     * Constructor parametrizado de vehiculo
     * @param tiempo el tiempo que permanecera el vehiculo en el parking
     * @param matricula la matricula del vehiculo
     * @param tipo el tipo de vehiculo, se utiliza un enum para elegir entre tres tipos
     * @param maquina la maquina en la que paga el vehiculo
     * @param parking el parking al que va el vehiculo a intentar aparcar
     */
    public Vehiculo(String matricula, int tiempo, tipoVehiculo tipo, boolean esDPI, Parking parking, MaquinaPago maquina) {
        this.tiempo = tiempo;
        this.matricula = matricula;
        this.tipo = tipo;
        this.parking = parking;
        this.maquina = maquina;
        if (!(tipo == tipoVehiculo.COCHE)) {
            this.esDPI = false;
        } else this.esDPI = esDPI;
        if (tipo == tipoVehiculo.ELECTRICO) {
            this.bateria = (int) (Math.random() * 100) + 1;
        }
    }

    /**
     * metodo get de la matricula
     * @return devuelve la matricula de este coche
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * El metodo get del tiempo
     * @return devuelve el tiempo que permanece el coche en el parking
     */
    public long getTiempo() {
        return tiempo;
    }

    /**
     * El metodo get del tipo de vehiculo
     * @return devuelve el tipo de vehiculo
     */
    public tipoVehiculo getTipo() {
        return tipo;
    }

    /**
     * El metodo get de la bateria del coche si fuera electrico
     * @return
     */
    public int getBateria() {
        return bateria;
    }

    /**
     * Metodo run que implementa el comportamiendo del coche
     */
    @Override
    public void run() {
        try {
            Thread.sleep((long) (Math.random() * 6000));
            if (this.esDPI && parking.entrarVehiculoDPI(this)) {
                Thread.sleep(tiempo * 1000);
                parking.pagar(this.maquina, this);
                parking.salirVehiculoDPI(this);
                return;
            }

            if (parking.entrarVehiculo(this)) {
                Thread.sleep(tiempo * 1000);
                parking.pagar(this.maquina, this);
                parking.salirVehiculo(this);
            }
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo toString del vehiculo
     * @return devuelve un mensaje personalizado segun parametros del objeto vehiculo
     */
    @Override
    public String toString() {
        String texto;
        if (tipo == tipoVehiculo.COCHE) {
            texto = "El coche de matrícula " + getMatricula() + ":" +
                    "\n \t Estuvo " + getTiempo() + " Horas,";
        } else if (tipo == tipoVehiculo.MOTO) {
            texto = "La moto de matrícula " + getMatricula() + ":" +
                    "\n \t Estuvo " + getTiempo() + " Horas,";
        } else {
            texto = "El coche eléctrico de matrícula " + getMatricula() + ":" +
                    "\n \t Estuvo " + getTiempo() + " Horas,";
        }
        return texto;
    }
}