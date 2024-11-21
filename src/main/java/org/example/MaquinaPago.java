package org.example;

import java.io.*;

public class MaquinaPago {

    //metodo el precio de 1'5€ por segundo
    public double recibirDinero(Vehiculo vehiculo) {
        return vehiculo.getTiempo() * 1.5;
    }

    //Graba en registro.txt los datos
    public synchronized void registrarSalida(Vehiculo vehiculo) throws IOException {
        File registroVehiculo = new File("src/main/resources/files/registro.txt");
        File recaudacionMaquinas = new File("src/main/resources/files/moneymoney.dat");
        double dinero = recibirDinero(vehiculo);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(registroVehiculo, true))) {
            StringBuilder sb = new StringBuilder();
            sb.append(vehiculo);
            sb.append("\t pagando " + dinero + "€.\n");
            bw.write(sb.toString());

        }
        if (!recaudacionMaquinas.exists()) {
            try (FileOutputStream fos = new FileOutputStream(recaudacionMaquinas);
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeDouble(0);
            }
        }

        try (FileInputStream fis = new FileInputStream(recaudacionMaquinas);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            double recaudacion =ois.readDouble()+dinero;

            try (FileOutputStream fos = new FileOutputStream(recaudacionMaquinas);
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeDouble(recaudacion);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


/*
Ejemplo:
* El coche de matricula 45634jn56:
*   Estuvo 43 Horas,
*   pagando 58€.
*
* La moto de matricula 523342JBL:
*   Estuvo 2 Horas,
*   pagando 3€.
*
* */
    }
}
