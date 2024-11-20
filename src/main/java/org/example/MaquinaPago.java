package org.example;

import java.io.*;

public class MaquinaPago {



    //metodo el precio de 1'5€ por segundo
    public double recibirDinero(Vehiculo vehiculo){
        return vehiculo.getTiempo() * 1.5;
    }

    //Graba en registro.txt los datos
    public void registrarSalida(Vehiculo vehiculo, Double precio) throws IOException {
        File archivo = new File("src/main/resources/files/registro.txt");
        BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true));
        StringBuilder sb = new StringBuilder();
        sb.append(vehiculo.toString());
        sb.append("\t pagando " + recibirDinero(vehiculo)+"€.\n");
        bw.write(sb.toString());
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
