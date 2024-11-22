package org.example;

import java.io.*;
import java.util.concurrent.locks.ReentrantLock;

public class MaquinaPago {
    final String RUTA_REGISTRO = "src/main/resources/files/registro.txt";
    final String RUTA_RECAUDACION = "src/main/resources/files/moneymoney.dat";
    final File REGISTROVEHICULO = new File(RUTA_REGISTRO);
    final File RECAUDACIONMAQUINA = new File(RUTA_RECAUDACION);
    final double PRECIO_HORA = 1.5;
    ReentrantLock lock = new ReentrantLock();

    public double recibirDinero(Vehiculo vehiculo) {
        return vehiculo.getTiempo() * PRECIO_HORA;
    }

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

    public void actualizarRecaudacion(double dinero) throws IOException {
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
