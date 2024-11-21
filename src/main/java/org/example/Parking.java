package org.example;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Parking {
    /*
    26 Motos
    160 coches normales
    20 plazas DPI
    5 cargadores de coche electrico
    */

    //Añadir cargadores a las claves de semaforo normal y gestionar las plazas de coche/cargador/moto con int?
    //Creo que vamos a tener que usar 2 reentrantLock y gestionar las plazas con int para que los coches vayan de 1 en 1
    ReentrantLock lockEntradaNormal = new ReentrantLock();
    ReentrantLock lockEntradaDPI = new ReentrantLock();
    ReentrantLock lockSalidaNormal = new ReentrantLock();
    ReentrantLock lockSalidaPDI = new ReentrantLock();
    Semaphore maquinasPago = new Semaphore(3);

    final int MAX_PLAZAS_COCHES = 160;
    final int MAX_PLAZAS_ELECTRICO = 5;
    final int MAX_PLAZAS_MOTOS = 26;
    final int MAX_PLAZAS_DPI = 20;

    int plazasCochesDisponibles = MAX_PLAZAS_COCHES;
    int plazasMotosDisponibles = MAX_PLAZAS_MOTOS;
    int plazasCargadoresDisponibles = MAX_PLAZAS_ELECTRICO;
    int plazasDPI = MAX_PLAZAS_DPI;


    public boolean entrarVehiculo(Vehiculo vehiculo) {
        lockEntradaNormal.lock();
        try {
            if (vehiculo.getTipo() == Vehiculo.tipoVehiculo.COCHE) {
                System.out.println("Soy el coche con matrícula " + vehiculo.getMatricula() + " y voy a ver si puedo aparcar");
                if (plazasCochesDisponibles == 0) {
                    System.out.println("No hay plazas, me toca aparcar en la calle como un plebeyo");
                    return false;
                }
                System.out.println("¡Hay una plaza! ¡Que bien!");
                plazasCochesDisponibles--;

            } else if (vehiculo.getTipo() == Vehiculo.tipoVehiculo.MOTO) {
                System.out.println("Soy la moto con matricula " + vehiculo.getMatricula() + " y voy a ver si puedo aparcar");
                if (plazasMotosDisponibles == 0) {
                    System.out.println("No hay plazas me toca aparcar entre dos coches en la calle y que se busquen la vida");
                    return false;
                }
                System.out.println("¡Hay una plaza! ¡Que bien!");
                plazasMotosDisponibles--;

            } else if (vehiculo.getTipo() == Vehiculo.tipoVehiculo.ELECTRICO) {
                System.out.println("Soy el coche eléctrico con matrícula " + vehiculo.getMatricula() + " y voy a ver si puedo aparcar");

                if (vehiculo.getBateria() <= 20 && plazasCargadoresDisponibles == 0) {
                    System.out.println("¡Oh no!. Tengo muy poca batería y no hay cargadores, ¡me voy!");
                    return false;

                } else {
                    if ((vehiculo.getBateria() <= 20)) {
                        System.out.println("¡Hay una plaza! ¡Que bien!");
                        plazasCargadoresDisponibles--;

                    } else if (plazasCochesDisponibles != 0) {
                        System.out.println("¡Hay una plaza! ¡Que bien!");
                        plazasCochesDisponibles--;

                    } else {
                        System.out.println("Ni plazas, ni cargadores ni na'... que mal rollo...");
                        return false;
                    }
                }
            }
            return true;
        } finally {
            lockEntradaNormal.unlock();
        }
    }

    public void entrarVehiculoDPI(Vehiculo vehiculo) {
        lockEntradaDPI.lock();
        if (plazasDPI != 0) {
            System.out.println("¡Por una vez mi plaza favorita está!, para mi coche de matrícula " + vehiculo.getMatricula() + ", Hoy aprueban todos!");
            plazasDPI--;
            lockEntradaDPI.unlock();
            return true;
        } else {
            lockEntradaDPI.unlock();
            return false;
        }
    }

    public void salirVehiculo(Vehiculo vehiculo) {
        lockSalidaNormal.lock();
        try {
            if (vehiculo.getTipo() == Vehiculo.tipoVehiculo.COCHE) {
                System.out.println("El coche con matrícula " + vehiculo.getMatricula() + " ha salido.");
                plazasCochesDisponibles++;
            } else if (vehiculo.getTipo() == Vehiculo.tipoVehiculo.MOTO) {
                System.out.println("La moto con matrícula " + vehiculo.getMatricula() + " ha salido.");
                plazasMotosDisponibles++;

            } else if (vehiculo.getTipo() == Vehiculo.tipoVehiculo.ELECTRICO) {
                System.out.println("El coche eléctrico con matrícula " + vehiculo.getMatricula() + " ha salido.");
                if (vehiculo.getBateria() <= 20) {
                    plazasCargadoresDisponibles++;
                } else plazasCochesDisponibles++;
            }
        } finally {
            lockSalidaNormal.unlock();
        }
    }

    public void salirVehiculoDPI(Vehiculo vehiculo) {
        lockSalidaPDI.lock();
        try {
            System.out.println("El coche con matrícula " + vehiculo.getMatricula() + " ha finalizado su jornada laboral. ¡A descansar!");
            plazasDPI++;
        } finally {
            lockSalidaPDI.unlock();
        }
    }

    //representa el pago en la máquina pasada como parámetro.
    public void pagar(MaquinaPago maquina) {

    }
}
