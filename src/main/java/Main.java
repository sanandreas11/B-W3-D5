import dao.Archivio;
import entities.*;
import enumerations.Periodicita;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("catalogo_bibliotecario");
        EntityManager em = emf.createEntityManager();

        Archivio archivio = new Archivio(em);

        // Creazione e registrazione utente
        Utente utente = new Utente("Luca", "Verdi", LocalDate.of(1985, 3, 22), "T123");
        archivio.registraUtente(utente);

        // Aggiunta elementi al catalogo
        Libro libro = new Libro("ISBN1001", "Il nome della rosa", 1980, 500, "Umberto Eco", "Giallo storico");
        Rivista rivista = new Rivista("ISBN1002", "Focus", 2024, 90, Periodicita.MENSILE);

        archivio.aggiungiElemento(libro);
        archivio.aggiungiElemento(rivista);

        // Ricerca per autore
        System.out.println("\nLibri di Umberto Eco:");
        archivio.cercaPerAutore("Umberto Eco").forEach(l -> System.out.println("- " + l.getTitolo()));

        // Ricerca per titolo
        System.out.println("\nRicerca per titolo contenente 'rosa':");
        archivio.cercaPerTitolo("rosa").forEach(e -> System.out.println("- " + e.getTitolo()));

        // Registrazione prestito
        Prestito prestito = new Prestito(utente, libro, LocalDate.now());
        archivio.registraPrestito(prestito);

        // Prestiti attivi per utente
        System.out.println("\nPrestiti attivi per tessera T123:");
        archivio.getPrestitiAttiviUtente("T123").forEach(p ->
                System.out.println("- " + p.getElementoPrestato().getTitolo() +
                        " (Scadenza: " + p.getDataRestituzionePrevista() + ")"));

        // Ricerca prestiti scaduti non restituiti (test futuro: manualmente cambia data nel DB)
        System.out.println("\nPrestiti scaduti non restituiti:");
        archivio.getPrestitiScadutiNonRestituiti().forEach(p ->
                System.out.println("- " + p.getElementoPrestato().getTitolo() +
                        " (Prevista: " + p.getDataRestituzionePrevista() + ")"));

        em.close();
        emf.close();
    }
}