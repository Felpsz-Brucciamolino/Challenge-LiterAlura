package br.com.alura.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosAutor(
        @JsonAlias("name") String nome,
        @JsonAlias("birth_year") Integer nascimento,
        @JsonAlias("death_year") Integer morte
        //@JsonAlias("id do livro") Long Id do livro, aí aqui ficaria somente os id dos livros e assim
        //eu poderia puxar os dados de cada livro pelo id deles e assim trazer o nome
) {}