package br.com.alura.LiterAlura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nome;
    private Integer nascimento;
    private Integer morte;

    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor(){}


    public Autor(DadosAutor dados) {
        this.nome = dados.nome();
        this.nascimento = dados.nascimento();
        this.morte = dados.morte();
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNascimento() {
        return nascimento;
    }

    public void setNascimento(Integer nascimento) {
        this.nascimento = nascimento;
    }

    public Integer getMorte() {
        return morte;
    }

    public void setMorte(Integer morte) {
        this.morte = morte;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }


    @Override
    public String toString() {
        return """
            --------------------------
            Autor: %s
            Ano de Nascimento: %s
            Ano de Morte: %s
            Livros: %s
            --------------------------
            """.formatted(
                nome,
                nascimento,
                morte != null ? morte : "Ainda vivo",
                livros.stream()
                        .map(Livro::getTitulo)
                        .sorted()
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("Nenhum livro registrado")
        );
    }


}
