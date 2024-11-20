package org.example;

public class Vehiculo implements Runnable {
    String matricula;


    enum tipoCoche {COCHE, MOTO, ELECTRICO}

    ;
    long tiempo;
    tipoCoche tipo;
    int bateria;
    boolean dpi;

    public Vehiculo(long tiempo, String matricula, tipoCoche tipo) {
        this.tiempo = tiempo;
        this.matricula = matricula;
        this.tipo = tipo;
        if (tipo == tipoCoche.COCHE) {
            switch ((int) (Math.random() * 2)) {
                case 0 -> this.dpi = true;
                case 1 -> this.dpi = false;
            }
            this.tipo = tipo;
        } else if (tipo == tipoCoche.ELECTRICO) {
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

    public tipoCoche getTipo() {
        return tipo;
    }

    public void setTipo(tipoCoche tipo) {
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

    }

    @Override
    public String toString() {
        String texto = "";
        if (tipo == tipoCoche.COCHE) {
            texto = "El coche de matrícula " + getMatricula() + ":" +
                    "\n \t Estuvo " + getTiempo() + " Horas,";
        } else if (tipo == tipoCoche.MOTO) {
            texto = "La moto de matrícula " + getMatricula() + ":" +
                    "\n \t Estuvo " + getTiempo() + " Horas,";
        } else {
            texto = "El coche eléctrico de matrícula " + getMatricula() + ":" +
                    "\n \t Estuvo " + getTiempo() + " Horas,";
        }

        return texto;
    }
}