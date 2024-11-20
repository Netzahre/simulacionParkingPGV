package org.example;

import java.io.*;

public class MaquinaPago {

    //metodo el precio de 1'5€ por segundo
    public double recibirDinero(Vehiculo vehiculo){
        return vehiculo.getTiempo() * 1.5;
    }

    //Graba en registro.txt los datos
    public void registrarSalida(Vehiculo vehiculo) throws IOException {
        File registroVehiculo = new File("src/main/resources/files/registro.txt");
        File recaudacionMaquinas = new File("src/main/resources/files/moneymoney.dat");
        double dinero = recibirDinero(vehiculo);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(registroVehiculo, true))) {
            StringBuilder sb = new StringBuilder();
            sb.append(vehiculo.toString());
            sb.append("\t pagando " + dinero + "€.\n");
            bw.write(sb.toString());
        }

        try (BufferedReader br = new BufferedReader(new FileReader(recaudacionMaquinas))){
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            for (String s : line.split(" ")) {
                sb.append(s);
            }
            double precioNuevo = sb.charAt(4)+dinero;
            try(BufferedWriter bw = new BufferedWriter(new FileWriter(recaudacionMaquinas,true))){
                bw.write("El parking ha recaudado "+precioNuevo+" €.");
            }
        }
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
