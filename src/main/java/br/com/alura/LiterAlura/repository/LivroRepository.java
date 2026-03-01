package br.com.alura.LiterAlura.repository;

import br.com.alura.LiterAlura.model.Autor;
import br.com.alura.LiterAlura.model.Linguas;
import br.com.alura.LiterAlura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findTop10ByOrderByQntDownloadDesc();

    List<Livro> findByLingua(Linguas lingua);

    Optional<Livro> findByTituloIgnoreCaseAndAutor(String titulo, Autor autor);
}