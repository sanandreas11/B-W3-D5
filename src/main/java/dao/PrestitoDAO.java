package dao;

import entities.Prestito;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

public class PrestitoDAO {
    private final EntityManager em;

    public PrestitoDAO(EntityManager em) {
        this.em = em;
    }

    public void salva(Prestito prestito) {
        em.getTransaction().begin();
        em.persist(prestito);
        em.getTransaction().commit();
    }

    public List<Prestito> prestitiAttiviPerTessera(String numeroTessera) {
        TypedQuery<Prestito> query = em.createQuery(
                "SELECT p FROM Prestito p WHERE p.utente.numeroTessera = :numero AND p.dataRestituzioneEffettiva IS NULL",
                Prestito.class);
        query.setParameter("numero", numeroTessera);
        return query.getResultList();
    }

    public List<Prestito> prestitiScadutiNonRestituiti() {
        TypedQuery<Prestito> query = em.createQuery(
                "SELECT p FROM Prestito p WHERE p.dataRestituzioneEffettiva IS NULL AND p.dataRestituzionePrevista < :oggi",
                Prestito.class);
        query.setParameter("oggi", LocalDate.now());
        return query.getResultList();
    }
}
