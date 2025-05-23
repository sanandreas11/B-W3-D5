package dao;

import entities.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class Archivio {
    private final EntityManager em;

    public Archivio(EntityManager em) {
        this.em = em;
    }

    // Aggiunta e rimozione di elementi
    public void aggiungiElemento(ElementoCatalogo e) {
        em.getTransaction().begin();
        em.persist(e);
        em.getTransaction().commit();
    }

    public void rimuoviElemento(String isbn) {
        em.getTransaction().begin();
        ElementoCatalogo e = em.find(ElementoCatalogo.class, isbn);
        if (e != null) em.remove(e);
        em.getTransaction().commit();
    }

    // Ricerca nel catalogo
    public ElementoCatalogo cercaPerISBN(String isbn) {
        return em.find(ElementoCatalogo.class, isbn);
    }

    public List<ElementoCatalogo> cercaPerAnno(int anno) {
        TypedQuery<ElementoCatalogo> query = em.createQuery(
                "SELECT e FROM ElementoCatalogo e WHERE e.annoPubblicazione = :anno", ElementoCatalogo.class);
        query.setParameter("anno", anno);
        return query.getResultList();
    }

    public List<Libro> cercaPerAutore(String autore) {
        TypedQuery<Libro> query = em.createQuery(
                "SELECT l FROM Libro l WHERE l.autore = :autore", Libro.class);
        query.setParameter("autore", autore);
        return query.getResultList();
    }

    public List<ElementoCatalogo> cercaPerTitolo(String titolo) {
        TypedQuery<ElementoCatalogo> query = em.createQuery(
                "SELECT e FROM ElementoCatalogo e WHERE LOWER(e.titolo) LIKE LOWER(:titolo)", ElementoCatalogo.class);
        query.setParameter("titolo", "%" + titolo + "%");
        return query.getResultList();
    }

    // Gestione utenti
    public void registraUtente(Utente u) {
        em.getTransaction().begin();
        em.persist(u);
        em.getTransaction().commit();
    }

    public Utente cercaUtentePerTessera(String numeroTessera) {
        TypedQuery<Utente> query = em.createQuery(
                "SELECT u FROM Utente u WHERE u.numeroTessera = :numero", Utente.class);
        query.setParameter("numero", numeroTessera);
        return query.getSingleResult();
    }

    // Prestiti
    public void registraPrestito(Prestito p) {
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }

    public List<Prestito> getPrestitiAttiviUtente(String numeroTessera) {
        TypedQuery<Prestito> query = em.createQuery(
                "SELECT p FROM Prestito p WHERE p.utente.numeroTessera = :numero AND p.dataRestituzioneEffettiva IS NULL",
                Prestito.class);
        query.setParameter("numero", numeroTessera);
        return query.getResultList();
    }

    public List<Prestito> getPrestitiScadutiNonRestituiti() {
        TypedQuery<Prestito> query = em.createQuery(
                "SELECT p FROM Prestito p WHERE p.dataRestituzioneEffettiva IS NULL AND p.dataRestituzionePrevista < :oggi",
                Prestito.class);
        query.setParameter("oggi", LocalDate.now());
        return query.getResultList();
    }
}
