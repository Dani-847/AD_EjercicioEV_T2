package org.drk;

import org.drk.common.DAO;
import org.drk.common.DataProvider;
import org.drk.pelicula.Pelicula;
import org.drk.pelicula.PeliculaDAO;

import javax.sql.DataSource;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

/**
 * Clase principal que gestiona la aplicación de películas.
 * Permite añadir, enumerar y renombrar géneros de películas.
 * Utiliza un DAO para interactuar con la base de datos.
 * Usa while y switch para el menú de opciones.
 */

public class Main {
    public static void main(String[] args) {
        DataSource ds = DataProvider.getDataSource();
        DAO<Pelicula> peliculaDAO = new PeliculaDAO(ds);
        Scanner sc = new Scanner(System.in);
        System.out.println("Gestor de Películas");
        System.out.println("HU1 (1). Añadir película");
        System.out.println("HU2 (2). Enumerar peliculas");
        System.out.println("HU3 (3). Renombrar genero en todas las películas de un género");
        System.out.println("(4). Listar todas las peliculas");
        System.out.print("(0 = Salir) Selecciona una opción: ");
        String opcion = sc.nextLine();
        while(!opcion.equals("0")){
            switch (opcion) {
                case "1" -> {
                    Pelicula p = new Pelicula();
                    System.out.println("Nueva pelicula:");
                    System.out.print("Título: ");
                    String titulo = sc.nextLine();
                    System.out.print("Género: ");
                    String genero = sc.nextLine();
                    System.out.print("Año: ");
                    int año = Integer.parseInt(sc.nextLine());
                    p.setTitulo(titulo);
                    p.setGenero(genero);
                    p.setAño(año);
                    peliculaDAO.save(p);
                    System.out.println("Película añadida correctamente");

                }
                case "2" -> {
                    System.out.println("Cantidad de películas en la base de datos: " + peliculaDAO.findAll().size());
                }
                case "3" -> {
                    System.out.println("Renombrar género en todas las películas de un género");
                    System.out.print("Género actual: ");
                    String generoActual = sc.nextLine();
                    System.out.print("Nuevo género: ");
                    String nuevoGenero = sc.nextLine();
                    List<Pelicula> listaPeliculas = peliculaDAO.findAll();
                    for (Pelicula pelicula : listaPeliculas) {
                        if (pelicula.getGenero().equalsIgnoreCase(generoActual)) {
                            pelicula.setGenero(nuevoGenero);
                            peliculaDAO.update(pelicula);
                        }
                    }
                    System.out.println("Género renombrado correctamente");
                }
                case "4" -> {
                    System.out.println("Listado de todas las películas:");
                    List<Pelicula> listaPeliculas = peliculaDAO.findAll();
                    for (Pelicula pelicula : listaPeliculas) {
                        System.out.println(pelicula);
                    }
                }
                default -> System.out.println("Opción no válida");
            }
            System.out.print("(0 = Salir) Selecciona una nueva opción: ");
            opcion = sc.nextLine();
        }
        sc.close();
    }
}