package org.example;

import java.util.ArrayList;
import java.util.List;

public class Simulador {

    /**
     * Metodo para rellenar una lista con 300 coches y 30 motos creados aleatoriamente
     * @return devuelve una lista con 330 vehiculos
     */
    static private List<Vehiculo> rellenarVehiculos() {
        Parking parking = new Parking();
        MaquinaPago maquina = new MaquinaPago();
        List<Vehiculo> vehiculos = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            String matricula = "C" + ((int) (Math.random() * 9000) + 1000);
            Vehiculo.tipoVehiculo tipo = (Math.random() < 0.5) ?
                    Vehiculo.tipoVehiculo.COCHE : Vehiculo.tipoVehiculo.ELECTRICO;
            int estanciaParking = (int) (Math.random() * 24) + 1;
            boolean esDPI = switch ((int) (Math.random() * 2)) {
                case 0 -> true;
                case 1 -> false;
                default -> false;
            };
            vehiculos.add(new Vehiculo(matricula, estanciaParking, tipo, esDPI, parking, maquina));
        }

        for (int i = 0; i < 30; i++) {
            String matricula = "M" + ((int) (Math.random() * 9000) + 1000);
            int estanciaParking = (int) (Math.random() * 24) + 1;
            vehiculos.add(new Vehiculo(matricula, estanciaParking,Vehiculo.tipoVehiculo.MOTO, false, parking, maquina));
        }
        return vehiculos;
    }


    /**
     * Para ejecutar la simulacion del parking
     */
    public static void main(String[] args) throws InterruptedException {
        List<Vehiculo> listaVehiculos = rellenarVehiculos();
        ArrayList<Thread> hilos = new ArrayList<>();
        for (Vehiculo vehiculo : listaVehiculos) {
            Thread thread = new Thread(vehiculo);
            hilos.add(thread);
        }

        hilos.forEach(Thread::start);

        for (Thread hilo : hilos) {
            hilo.join();
        }
        System.out.println("El parking ha cerrado por hoy");
    }
}
