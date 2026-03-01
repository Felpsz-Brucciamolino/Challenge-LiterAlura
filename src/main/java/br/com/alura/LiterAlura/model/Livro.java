package br.com.alura.LiterAlura.model;

import jakarta.persistence.*;

@Entity
@Table(
        name = "livros",
        uniqueConstraints = @UniqueConstraint(columnNames = {"titulo", "autor_id"})
)
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String titulo;
    @ManyToOne
    private Autor autor;
    @Column(columnDefinition = "TEXT")
    private String sumario;
    @Enumerated(EnumType.STRING)
    private Linguas lingua;
    private Integer qntDownload;

    public Livro(){}

    public Livro(DadosLivro dadosLivro, Autor autor) {
        this.titulo = dadosLivro.titulo();
        this.autor = autor;

        this.sumario = dadosLivro.sumarios() != null
                ? dadosLivro.sumarios().stream()
                .findFirst()
                .orElse(null) :null;

        this.lingua = dadosLivro.linguas() != null
                ? dadosLivro.linguas().stream().findFirst().map(Linguas::fromAPI).orElse(null)
                : null;

        this.qntDownload = dadosLivro.qntDownload();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getSumario() {
        return sumario;
    }

    public void setSumario(String sumario) {
        this.sumario = sumario;
    }

    public Linguas getLingua() {
        return lingua;
    }

    public void setLingua(Linguas lingua) {
        this.lingua = lingua;
    }

    public Integer getQntDownload() {
        return qntDownload;
    }

    public void setQntDownload(Integer qntDownload) {
        this.qntDownload = qntDownload;
    }



    @Override
    public String toString() {
        return """
            --------- LIVRO ----------
            Título: %s
            Autor: %s
            Idioma: %s
            Total de Downloads: %d
            --------------------------
            """.formatted(
                titulo,
                autor != null ? autor.getNome() : "Desconhecido",
                lingua,
                qntDownload
        );
    }


}
