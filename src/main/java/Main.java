import dao.Archivio;
import entities.*;
import enumerations.Periodicita;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("catalogo_bibliotecario");
        EntityManager em = emf.createEntityManager();
        Archivio archivio = new Archivio(em);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("""
                \n--- MENU ---
                1. Aggiungi libro
                2. Aggiungi rivista
                3. Ricerca per ISBN
                4. Ricerca per titolo
                5. Ricerca per autore
                6. Ricerca per anno
                7. Registra utente
                8. Registra prestito
                9. Visualizza prestiti attivi per utente
                10. Visualizza prestiti scaduti non restituiti
                0. Esci
                Scelta:
                """);

            int scelta = Integer.parseInt(scanner.nextLine());

            switch (scelta) {
                case 1 -> {
                    System.out.print("ISBN: ");
                    String isbn = scanner.nextLine();
                    System.out.print("Titolo: ");
                    String titolo = scanner.nextLine();
                    System.out.print("Anno pubblicazione: ");
                    int anno = Integer.parseInt(scanner.nextLine());
                    System.out.print("Numero pagine: ");
                    int pagine = Integer.parseInt(scanner.nextLine());
                    System.out.print("Autore: ");
                    String autore = scanner.nextLine();
                    System.out.print("Genere: ");
                    String genere = scanner.nextLine();

                    Libro libro = new Libro(isbn, titolo, anno, pagine, autore, genere);
                    archivio.aggiungiElemento(libro);
                    System.out.println("Libro aggiunto con successo.");
                }
                case 2 -> {
                    System.out.print("ISBN: ");
                    String isbn = scanner.nextLine();
                    System.out.print("Titolo: ");
                    String titolo = scanner.nextLine();
                    System.out.print("Anno pubblicazione: ");
                    int anno = Integer.parseInt(scanner.nextLine());
                    System.out.print("Numero pagine: ");
                    int pagine = Integer.parseInt(scanner.nextLine());
                    System.out.print("Periodicità (SETTIMANALE, MENSILE, SEMESTRALE): ");
                    Periodicita periodicita = Periodicita.valueOf(scanner.nextLine().toUpperCase());

                    Rivista rivista = new Rivista(isbn, titolo, anno, pagine, periodicita);
                    archivio.aggiungiElemento(rivista);
                    System.out.println("Rivista aggiunta con successo.");
                }
                case 3 -> {
                    System.out.print("Inserisci ISBN da cercare: ");
                    String isbn = scanner.nextLine();
                    ElementoCatalogo e = archivio.cercaPerISBN(isbn);
                    System.out.println(e != null ? e : "Elemento non trovato.");
                }
                case 4 -> {
                    System.out.print("Inserisci parte del titolo da cercare: ");
                    String titolo = scanner.nextLine();
                    List<ElementoCatalogo> risultati = archivio.cercaPerTitolo(titolo);
                    risultati.forEach(System.out::println);
                }
                case 5 -> {
                    System.out.print("Inserisci autore: ");
                    String autore = scanner.nextLine();
                    archivio.cercaPerAutore(autore).forEach(System.out::println);
                }
                case 6 -> {
                    System.out.print("Inserisci anno di pubblicazione: ");
                    int anno = Integer.parseInt(scanner.nextLine());
                    archivio.cercaPerAnno(anno).forEach(System.out::println);
                }
                case 7 -> {
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Cognome: ");
                    String cognome = scanner.nextLine();
                    System.out.print("Data di nascita (AAAA-MM-GG): ");
                    LocalDate nascita = LocalDate.parse(scanner.nextLine());
                    System.out.print("Numero tessera: ");
                    String tessera = scanner.nextLine();

                    Utente utente = new Utente(nome, cognome, nascita, tessera);
                    archivio.registraUtente(utente);
                    System.out.println("Utente registrato con successo.");
                }
                case 8 -> {
                    System.out.print("Numero tessera utente: ");
                    String tessera = scanner.nextLine();
                    Utente u;
                    try {
                        u = archivio.cercaUtentePerTessera(tessera);
                    } catch (Exception ex) {
                        System.out.println("Utente non trovato.");
                        break;
                    }

                    System.out.print("ISBN elemento da prestare: ");
                    String isbn = scanner.nextLine();
                    ElementoCatalogo ec = archivio.cercaPerISBN(isbn);
                    if (ec == null) {
                        System.out.println("Elemento non trovato.");
                        break;
                    }

                    Prestito p = new Prestito(u, ec, LocalDate.now());
                    archivio.registraPrestito(p);
                    System.out.println("Prestito registrato.");
                }
                case 9 -> {
                    System.out.print("Numero tessera utente: ");
                    String tessera = scanner.nextLine();
                    List<Prestito> attivi = archivio.getPrestitiAttiviUtente(tessera);
                    attivi.forEach(System.out::println);
                }
                case 10 -> {
                    List<Prestito> scaduti = archivio.getPrestitiScadutiNonRestituiti();
                    scaduti.forEach(System.out::println);
                }
                case 0 -> {
                    running = false;
                    System.out.println("Uscita...");
                }
                default -> System.out.println("Scelta non valida.");
            }
        }

        scanner.close();
        em.close();
        emf.close();
    }
}