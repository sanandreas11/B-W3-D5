package entities;

import enumerations.Periodicita;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;

@Entity
public class Rivista extends ElementoCatalogo {
    @Enumerated
    private Periodicita periodicita;

    public Rivista() {}

    public Rivista(String isbn, String titolo, int annoPubblicazione, int numeroPagine, Periodicita periodicita) {
        super(isbn, titolo, annoPubblicazione, numeroPagine);
        this.periodicita = periodicita;
    }

    public Periodicita getPeriodicita() { return periodicita; }
    public void setPeriodicita(Periodicita periodicita) { this.periodicita = periodicita; }
}
