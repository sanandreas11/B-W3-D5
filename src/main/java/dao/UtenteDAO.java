package dao;

import entities.Utente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class UtenteDAO {
    private final EntityManager em;

    public UtenteDAO(EntityManager em) {
        this.em = em;
    }

    public void salva(Utente utente) {
        em.getTransaction().begin();
        em.persist(utente);
        em.getTransaction().commit();
    }

    public Utente cercaPerNumeroTessera(String numero) {
        TypedQuery<Utente> query = em.createQuery(
                "SELECT u FROM Utente u WHERE u.numeroTessera = :numero", Utente.class);
        query.setParameter("numero", numero);
        return query.getSingleResult();
    }
}
