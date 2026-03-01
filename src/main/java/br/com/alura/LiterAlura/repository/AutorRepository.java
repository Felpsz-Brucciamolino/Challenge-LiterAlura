package br.com.alura.LiterAlura.repository;

import br.com.alura.LiterAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNomeIgnoreCase(String nome);
    List<Autor> findByNascimentoLessThanEqualAndMorteGreaterThanEqual(Integer nascimento, Integer morte);
    List<Autor> findByNascimentoLessThanEqualAndMorteIsNull(Integer nascimento);
}