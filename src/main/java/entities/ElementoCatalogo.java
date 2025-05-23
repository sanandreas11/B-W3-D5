package entities;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class ElementoCatalogo {
    @Id
    private String isbn;

    private String titolo;
    private int annoPubblicazione;
    private int numeroPagine;

    // Costruttori
    public ElementoCatalogo() {}

    public ElementoCatalogo(String isbn, String titolo, int annoPubblicazione, int numeroPagine) {
        this.isbn = isbn;
        this.titolo = titolo;
        this.annoPubblicazione = annoPubblicazione;
        this.numeroPagine = numeroPagine;
    }

    // Getter e Setter
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }

    public int getAnnoPubblicazione() { return annoPubblicazione; }
    public void setAnnoPubblicazione(int annoPubblicazione) { this.annoPubblicazione = annoPubblicazione; }

    public int getNumeroPagine() { return numeroPagine; }
    public void setNumeroPagine(int numeroPagine) { this.numeroPagine = numeroPagine; }
}
