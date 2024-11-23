package org.example;

import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class Parking {
    private final ReentrantLock lockEntradaNormal = new ReentrantLock();
    private final ReentrantLock lockEntradaDPI = new ReentrantLock();
    private final ReentrantLock lockSalidaNormal = new ReentrantLock();
    private final ReentrantLock lockSalidaPDI = new ReentrantLock();
    private final Semaphore maquinasPago = new Semaphore(3);

    private final int MAX_PLAZAS_COCHES = 160;
    private final int MAX_PLAZAS_ELECTRICO = 5;
    private final int MAX_PLAZAS_MOTOS = 26;
    private final int MAX_PLAZAS_DPI = 20;

    private int plazasCochesDisponibles = MAX_PLAZAS_COCHES;
    private int plazasMotosDisponibles = MAX_PLAZAS_MOTOS;
    private int plazasCargadoresDisponibles = MAX_PLAZAS_ELECTRICO;
    private int plazasDPI = MAX_PLAZAS_DPI;

    /**
     * Constructor del parking
     */
    public Parking() {
    }

    /**
     * Metodo que simula la entrada de un vehiculo normal en el parking
     * @param vehiculo el vehiculo que intenta aparcar en el parking
     * @return devuelve si el vehiculo logro aparcar o no
     */
    public boolean entrarVehiculo(Vehiculo vehiculo) {
        lockEntradaNormal.lock();
        try {
            switch (vehiculo.getTipo()) {
                case COCHE -> {
                    return aparcarCoche(vehiculo);
                }
                case MOTO -> {
                    return aparcarMoto(vehiculo);
                }
                case ELECTRICO -> {
                    return aparcarCocheElectrico(vehiculo);
                }
            }
        } finally {
            lockEntradaNormal.unlock();
        }
        return false;
    }

    /**
     * Metodo para aparcar un coche normal
     * @param vehiculo el coche que se va a aparcar
     * @return devuelve verdadero si se aparco y falso si no se pudo
     */
    private boolean aparcarCoche(Vehiculo vehiculo) {
        System.out.println("Soy el coche con matrícula " + vehiculo.getMatricula() + " y voy a ver si puedo aparcar");
        if (plazasCochesDisponibles == 0) {
            System.out.println("No hay plazas, me toca aparcar en la calle como un plebeyo");
            return false;
        }
        System.out.println("¡Hay una plaza! ¡Que bien!");
        plazasCochesDisponibles--;
        return true;
    }

    /**
     * Metodo para aparcar una moto normal
     * @param vehiculo la moto que se va a aparcar
     * @return devuelve verdadero si se aparco y falso si no se pudo
     */
    private boolean aparcarMoto(Vehiculo vehiculo) {
        System.out.println("Soy la moto con matrícula " + vehiculo.getMatricula() + " y voy a ver si puedo aparcar");
        if (plazasMotosDisponibles == 0) {
            System.out.println("No hay plazas, me toca aparcar en la calle entre dos coches");
            return false;
        }
        System.out.println("¡Hay una plaza! ¡Que bien!");
        plazasMotosDisponibles--;
        return true;
    }

    /**
     * Metodo para aparcar un coche electrico
     * @param vehiculo el coche electrico que se va a aparcar
     * @return devuelve verdadero si se aparco y falso si no se pudo
     */
    private boolean aparcarCocheElectrico(Vehiculo vehiculo) {
        System.out.println("Soy el coche eléctrico con matrícula " + vehiculo.getMatricula() + " y voy a ver si puedo aparcar");

        if (vehiculo.getBateria() <= 20) {
            if (plazasCargadoresDisponibles > 0) {
                System.out.println("¡Hay un cargador libre! ¡Qué bien!");
                plazasCargadoresDisponibles--;
                return true;
            } else {
                System.out.println("¡Oh no! Tengo muy poca batería y no hay cargadores, ¡me voy!");
                return false;
            }
        }

        if (plazasCochesDisponibles > 0) {
            System.out.println("¡Hay una plaza! ¡Qué bien!");
            plazasCochesDisponibles--;
            return true;
        }

        System.out.println("Ni plazas, ni cargadores ni na'... que mal rollo...");
        return false;
    }

    /**
     * Metodo para aparcar un coche DPI por la entrada especial de estos
     * @param vehiculo el coche que se va a aparcar
     * @return devuelve verdadero si se aparco y falso si no se pudo
     */
    public boolean entrarVehiculoDPI(Vehiculo vehiculo) {
        lockEntradaDPI.lock();
        try {
            if (plazasDPI > 0) {
                System.out.println("¡Por una vez mi plaza favorita está!, para mi coche de matrícula " + vehiculo.getMatricula() + ", Hoy aprueban todos!");
                plazasDPI--;
                return true;
            } else {
                System.out.println("¡Me han cogido mi plaza favorita! Como no pueda aparcar al final todos suspendidos.");
                return false;
            }
        } finally {
            lockEntradaDPI.unlock();
        }

    }

    /**
     * Metodo que simula el vehiculo saliendo del parking
     * @param vehiculo el vehiculo que sale
     */
    public void salirVehiculo(Vehiculo vehiculo) {
        lockSalidaNormal.lock();
        try {
            switch (vehiculo.getTipo()) {
                case COCHE -> {
                    System.out.println("El coche con matrícula " + vehiculo.getMatricula() + " ha salido.");
                    plazasCochesDisponibles++;
                }
                case MOTO -> {
                    System.out.println("La moto con matrícula " + vehiculo.getMatricula() + " ha salido.");
                    plazasMotosDisponibles++;
                }
                case ELECTRICO -> {
                    System.out.println("El coche eléctrico con matrícula " + vehiculo.getMatricula() + " ha salido.");
                    if (vehiculo.getBateria() <= 20) {
                        plazasCargadoresDisponibles++;
                    } else plazasCochesDisponibles++;
                }
            }
        } finally {
            lockSalidaNormal.unlock();
        }
    }

    /**
     * Metodo que simula la salida de un vehiculo DPI del parking por su salida especial
     * @param vehiculo
     */
    public void salirVehiculoDPI(Vehiculo vehiculo) {
        lockSalidaPDI.lock();
        try {
            System.out.println("El coche con matrícula " + vehiculo.getMatricula() + " ha finalizado su jornada laboral. ¡A descansar!");
            plazasDPI++;
        } finally {
            lockSalidaPDI.unlock();
        }
    }

    /**
     * Metodo que simula el acto de ir a pagar el parking
     * @param maquina la maquina donde se va a pagar
     * @param vehiculo el vehiculo que va a pagar
     * @throws IOException Excepcion que puede ocurrir si no se puede registrar el vehiculo en el metodo de la maquina
     * @throws InterruptedException Excepcion que puede ocurrir si el hilo es interrumpido
     */
    public void pagar(MaquinaPago maquina, Vehiculo vehiculo) throws IOException, InterruptedException {
        maquinasPago.acquire();
        System.out.println("¡Por fin me toca pagar! Que bien que no me cobran por la espera...");
        System.out.println("Hay disponibles " + maquinasPago.availablePermits() + " maquinas de pago");
        maquina.registrarSalida(vehiculo);
        maquinasPago.release();
    }
}
