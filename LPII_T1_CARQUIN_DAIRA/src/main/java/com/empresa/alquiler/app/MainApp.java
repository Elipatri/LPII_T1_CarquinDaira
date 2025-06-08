package com.empresa.alquiler.app;

import com.empresa.alquiler.model.Cliente;
import com.empresa.alquiler.model.Pelicula;
import com.empresa.alquiler.service.AlquilerService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("alquilerPU");
        EntityManager em = emf.createEntityManager();

        try (Scanner sc = new Scanner(System.in)) {

            List<Cliente> clientes = em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
            List<Pelicula> peliculas = em.createQuery("SELECT p FROM Pelicula p", Pelicula.class).getResultList();

            System.out.println("Seleccione un cliente:");
            for (Cliente c : clientes) {
                System.out.printf("%d - %s\n", c.getIdCliente(), c.getNombre());
            }
            System.out.print("Ingrese ID del cliente: ");
            int idCliente = sc.nextInt();

            Cliente clienteSeleccionado = em.find(Cliente.class, idCliente);
            if (clienteSeleccionado == null) {
                System.out.println("Cliente no válido.");
                return;
            }

            System.out.println("\nPelículas disponibles:");
            for (Pelicula p : peliculas) {
                System.out.printf("%d - %s (Stock: %d)\n", p.getIdPelicula(), p.getTitulo(), p.getStock());
            }

            List<Pelicula> peliculasSeleccionadas = new ArrayList<>();
            List<Integer> cantidades = new ArrayList<>();

            String seguir = "S";
            while (seguir.equalsIgnoreCase("S")) {
                System.out.print("Ingrese ID de la película a alquilar: ");
                int idPelicula = sc.nextInt();
                Pelicula pelicula = em.find(Pelicula.class, idPelicula);
                if (pelicula == null) {
                    System.out.println("Película no válida.");
                    continue;
                }
                System.out.print("Ingrese cantidad a alquilar: ");
                int cantidad = sc.nextInt();
                if (cantidad <= 0) {
                    System.out.println("Cantidad inválida.");
                    continue;
                }
                if (cantidad > pelicula.getStock()) {
                    System.out.println("Cantidad supera el stock disponible.");
                    continue;
                }

                peliculasSeleccionadas.add(pelicula);
                cantidades.add(cantidad);

                System.out.print("¿Desea agregar otra película? (S/N): ");
                seguir = sc.next();
            }

            if (peliculasSeleccionadas.isEmpty()) {
                System.out.println("No se seleccionaron películas.");
                return;
            }

            double precioUnitario = 15.0;
            double total = 0;

            System.out.println("\nDetalle del alquiler:");
            System.out.printf("%-20s %-10s %-10s %-10s\n", "Película", "Precio", "Cantidad", "Subtotal");

            for (int i = 0; i < peliculasSeleccionadas.size(); i++) {
                Pelicula p = peliculasSeleccionadas.get(i);
                int cant = cantidades.get(i);
                double subtotal = cant * precioUnitario;
                total += subtotal;
                System.out.printf("%-20s $%-9.2f %-10d $%-9.2f\n", p.getTitulo(), precioUnitario, cant, subtotal);
            }

            System.out.println("-------------------------------------------");
            System.out.printf("Total a pagar: $%.2f\n\n", total);

            AlquilerService service = new AlquilerService(em);
            service.registrarAlquiler(idCliente, peliculasSeleccionadas, cantidades);

        } finally {
            em.close();
            emf.close();
        }
    }
}
