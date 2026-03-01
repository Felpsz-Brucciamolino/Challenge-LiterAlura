package br.com.alura.LiterAlura.service;

import br.com.alura.LiterAlura.dto.LivroDTO;
import br.com.alura.LiterAlura.model.DadosLivro;
import br.com.alura.LiterAlura.model.Livro;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {


    private List<LivroDTO> converteParaDTO(List<Livro> livros) {
        return livros.stream()
                .map(l -> new LivroDTO(
                        l.getTitulo(),
                        l.getAutor() != null ? l.getAutor().getNome() : "Autor desconhecido",
                        l.getAutor() != null && l.getAutor().getNascimento() != null
                                ? l.getAutor().getNascimento().toString()
                                : "Desconhecido"
                ))
                .toList();
    }

}
