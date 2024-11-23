package org.example;

import java.io.*;
import java.util.concurrent.locks.ReentrantLock;

public class MaquinaPago {
    private final ReentrantLock lock = new ReentrantLock();
    private final String RUTA_REGISTRO = "src/main/resources/files/registro.txt";
    private final String RUTA_RECAUDACION = "src/main/resources/files/moneymoney.dat";
    private final File REGISTROVEHICULO = new File(RUTA_REGISTRO);
    private final File RECAUDACIONMAQUINA = new File(RUTA_RECAUDACION);
    private final double PRECIO_HORA = 1.5;

    /**
     * Metodo que simula el acto de recibir el dinero del coche que sale.
     * @param vehiculo vehiculo que sale del parking y paga
     * @return devuelve el dinero que paga el vehiculo
     */
    private double recibirDinero(Vehiculo vehiculo) {
        return vehiculo.getTiempo() * PRECIO_HORA;
    }

    /**
     * Metodo que crea un registro de los coches que salen, las horas que han permanecido en el parking y el dinero
     * que pagan y lo escribe en un archivo txt en texto plano.
     * @param vehiculo vehiculo que sale del parking
     * @throws IOException posible error que podria dar el metodo al escribir al documento
     */
    public void registrarSalida(Vehiculo vehiculo) throws IOException {
        lock.lock();
        double dinero = recibirDinero(vehiculo);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(REGISTROVEHICULO, true))) {
            bw.write(vehiculo + "\n \t pagando " + dinero + "€.\n");
        }
        if (!RECAUDACIONMAQUINA.exists()) {
            try (FileOutputStream fos = new FileOutputStream(RECAUDACIONMAQUINA);
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeDouble(0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        actualizarRecaudacion(dinero);
        lock.unlock();
    }

    /**
     * Metodo que actualiza la cantidad total recaudada por el parkingy la escribe en un archivo .dat en binario
     * @param dinero el dinero que se añade al total de la recaudacion.
     */
    private void actualizarRecaudacion(double dinero) {
        double recaudacion;
        try (FileInputStream fis = new FileInputStream(RECAUDACIONMAQUINA);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            recaudacion = ois.readDouble();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        recaudacion = recaudacion + dinero;

        try (FileOutputStream fos = new FileOutputStream(RECAUDACIONMAQUINA);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeDouble(recaudacion);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
