package org.example;

public class Vehiculo implements Runnable {
    String matricula;
    enum tipoVehiculo {COCHE, MOTO, ELECTRICO}
    long tiempo;
    tipoVehiculo tipo;
    int bateria;
    boolean dpi;
    Parking parking;

    public Vehiculo(long tiempo, String matricula, tipoVehiculo tipo, Parking parking) {
        this.tiempo = tiempo;
        this.matricula = matricula;
        this.tipo = tipo;
        this.parking = parking;
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
        /*
        El coche entra y comprueba que hay plazas.
        si no hay se va, si hay entra.
        duerme Tiempo
        luego va a la maquinaPago,
        espera hasta que esté una libre.
        paga y se va.
        */
        if(parking.entrarVehiculo(this)){
            try {
                Thread.sleep(tiempo);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


    }

    @Override
    public String toString() {
        String texto = "";
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