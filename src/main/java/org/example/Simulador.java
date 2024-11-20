package org.example;

import java.util.ArrayList;
import java.util.List;

public class Simulador {
    List<Vehiculo> vehiculos = new ArrayList<>();



    private void rellenarVehiculos (){
        for (int i = 0; i < 300; i++) {
            String matricula = "C" + ((int) (Math.random() * 9000) + 1000);
            Vehiculo.tipoCoche tipo = (Math.random() < 0.5) ?
                    Vehiculo.tipoCoche.COCHE : Vehiculo.tipoCoche.ELECTRICO;
            vehiculos.add(new Vehiculo(((long) (Math.random() * 24) + 1), matricula, tipo));
        }

        for (int i = 0; i < 30; i++) {
            String matricula = "M" + ((int) (Math.random() * 9000) + 1000);
            vehiculos.add(new Vehiculo(((long) (Math.random() * 24) + 1), matricula, Vehiculo.tipoCoche.MOTO));
        }
    }
}
