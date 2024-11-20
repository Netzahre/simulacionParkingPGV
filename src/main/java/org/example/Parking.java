package org.example;

import java.util.concurrent.Semaphore;

public class Parking {
    /*
    26 Motos
    160 coches normales
    20 plazas DPI
    5 cargadores de coche electrico
    */

    //Añadir cargadores a las claves de semaforo normal y gestionar las plazas de coche/cargador/moto con int?
    //Creo que vamos a tener que usar 2 reentrantLock y gestionar las plazas con int para que los cohes vayan de 1 en 1
    Semaphore entradaNormal = new Semaphore(186);
    Semaphore entradaDPI = new Semaphore(20);

    //Los cargadores igual porque el coche entra y aparca o en plaza o en cargador asi qeu lock igual
    Semaphore cargadorElectrico = new Semaphore(5);

    public static void entrarVehiculo (Vehiculo vehiculo){
        if (vehiculo.getTipo()== Vehiculo.tipoCoche.COCHE){

        } else if (vehiculo.getTipo()== Vehiculo.tipoCoche.MOTO){

        } else if (vehiculo.getTipo()== Vehiculo.tipoCoche.ELECTRICO){

        }

    }

    public static void salirVehiculo (Vehiculo vehiculo){

    }

    //representa el pago en la máquina pasada como parámetro.
    public static void pagar(MaquinaPago maquina){

    }
}
