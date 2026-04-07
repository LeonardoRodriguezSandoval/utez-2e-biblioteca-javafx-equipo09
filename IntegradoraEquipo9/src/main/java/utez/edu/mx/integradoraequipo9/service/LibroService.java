package utez.edu.mx.integradoraequipo9.service;

import utez.edu.mx.integradoraequipo9.model.Libro;

import java.io.*;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class LibroService {

    private final String ARCHIVO = "libros.txt";

    public List<Libro> cargarLibros() {

        File archivo = new File(ARCHIVO);

        if (!archivo.exists()) {
            System.out.println("El archivo no existe,se creará un archivo nuevo");
            return new ArrayList<>();
        }

        List<Libro> lista = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO))) {
            String linea;

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");

                Libro libro = new Libro(
                        datos[0],
                        datos[1],
                        datos[2],
                        Integer.parseInt(datos[3]),
                        datos[4],
                        Boolean.parseBoolean(datos[5])
                );

                lista.add(libro);
            }

        } catch (IOException e) {
            System.out.println("No se pudo leer el archivo");
        }

        return lista;
    }

    public void guardarLibros(List<Libro> lista) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO))) {

            for (Libro l : lista) {
                writer.write(
                        l.getIsbn() + "," +
                                l.getTitulo() + "," +
                                l.getAutor() + "," +
                                l.getAnio() + "," +
                                l.getGenero() + "," +
                                l.isDisponible()
                );
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error al guardar archivo");
        }
    }

    public void exportarReporte(List<Libro> lista) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("reporte_catalogo.csv"))) {

            writer.write("ISBN,Titulo,Autor,Año,Genero,Disponible");
            writer.newLine();

            for (Libro l : lista) {
                writer.write(
                        l.getIsbn() + "," +
                                l.getTitulo() + "," +
                                l.getAutor() + "," +
                                l.getAnio() + "," +
                                l.getGenero() + "," +
                                l.isDisponible()
                );
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error al exportar reporte");
        }
    }

    public int obtenerSiguienteId(List<Libro> lista) {
        int max = 0;

        for (Libro l : lista) {
            try {
                int id = Integer.parseInt(l.getIsbn());
                if (id > max) {
                    max = id;
                }
            } catch (Exception ignored) {}
        }

        return max + 1;
    }
}
