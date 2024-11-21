package org.example;

import java.io.IOException;

public class Vehiculo implements Runnable {
    String matricula;
    enum tipoVehiculo {COCHE, MOTO, ELECTRICO}
    long tiempo;
    tipoVehiculo tipo;
    int bateria;
    boolean dpi;
    Parking parking;
    MaquinaPago maquina;

    public Vehiculo(long tiempo, String matricula, tipoVehiculo tipo,MaquinaPago maquina ,Parking parking) {
        this.tiempo = tiempo;
        this.matricula = matricula;
        this.tipo = tipo;
        this.parking = parking;
        this.maquina = maquina;
        if (tipo == tipoVehiculo.COCHE) {

            switch ((int) (Math.random() * 2)) {
                case 0 -> this.dpi = true;
                case 1 -> this.dpi = false;
            }
            this.tipo = tipo;
        } else if (tipo == tipoVehiculo.ELECTRICO) {
            this.bateria = (int) (Math.random() * 100) + 1;
            this.dpi = false;
        } else this.dpi = false;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public long getTiempo() {
        return tiempo;
    }

    public void setTiempo(long tiempo) {
        this.tiempo = tiempo;
    }

    public tipoVehiculo getTipo() {
        return tipo;
    }

    public void setTipo(tipoVehiculo tipo) {
        this.tipo = tipo;
    }

    public void setBateria(int bateria) {
        this.bateria = bateria;
    }

    public int getBateria() {
        return bateria;
    }

    @Override
    public void run() {
        try {
            if (this.dpi && parking.entrarVehiculoDPI(this)) {
                Thread.sleep(tiempo);
                parking.pagar(this.maquina, this);
                parking.salirVehiculoDPI(this);
                return;
            }

            if (parking.entrarVehiculo(this)) {
                Thread.sleep(tiempo);
                parking.pagar(this.maquina, this);
                parking.salirVehiculo(this);
            }
        } catch (InterruptedException | IOException e) {throw new RuntimeException(e);}
    }

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