package br.com.alura.LiterAlura.principal;

import br.com.alura.LiterAlura.model.*;
import br.com.alura.LiterAlura.repository.AutorRepository;
import br.com.alura.LiterAlura.repository.LivroRepository;
import br.com.alura.LiterAlura.service.ConsumoAPI;
import br.com.alura.LiterAlura.service.ConverteDados;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Principal {
    private Scanner Leitura = new Scanner(System.in);
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();

    private final String ENDERECO = "https://gutendex.com/books/?search=";
    private List<DadosLivro> dadosLivro = new ArrayList<>();

    private List<Livro> livros = new ArrayList<>();
    private LivroRepository repositorio;
    private AutorRepository autorRepository;
    private List<DadosLivro> dadosLivros = new ArrayList<>();
    private Optional<DadosLivro> serieBusca;

    public Principal(LivroRepository repositorio, AutorRepository autorRepository) {
        this.repositorio = repositorio;
        this.autorRepository = autorRepository;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar Autores registrados
                    4 - Listar Autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma
                    6 - Listar os 10 maiores livros por quantidade de download
                    0 - Sair
                    """;
            System.out.println(menu);
            System.out.print("Escolha uma opção:\n");

            String entrada = Leitura.nextLine().trim();

            try {
                opcao = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida: digite um número válido");
                continue;
            }
            if (opcao < 0 || opcao > 6) {
                System.out.println("Número inválido: escolha entre 0 e 6");
                continue;
            }
            switch (opcao) {
                case 1:
                    buscarLivroPeloTitulo();
                    break;
                case 2:
                    ListarLivrosRegistrados();
                    break;
                case 3:
                    ListarAutoresRegistrados();
                    break;
                case 4:
                    ListarAutoresVivosPorAno();
                    break;
                case 5:
                    ListarLivrosPorIdioma();
                    break;
                case 6:
                    ListarLivrosPorDownload();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
                    return;

            }
        }
    }

    private void buscarLivroPeloTitulo() {
        try {
        DadosLivro dadosLivro = getDadosLivro();
        DadosAutor dadosAutor = dadosLivro.autores().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Livro veio sem autor na API"));

        Autor autor = autorRepository.findByNomeIgnoreCase(dadosAutor.nome())
                .orElseGet(() -> autorRepository.save(new Autor(dadosAutor)));
        Livro livro = new Livro(dadosLivro, autor);
            Optional<Livro> existente = repositorio.findByTituloIgnoreCaseAndAutor(dadosLivro.titulo(), autor);
            if (existente.isPresent()) {
                System.out.println("Esse livro já está salvo com esse autor");
                return;
            }
        repositorio.save(livro);

        System.out.println("✅ Livro salvo: " + livro.getTitulo() + " | Autor: " + autor.getNome());
        } catch (RuntimeException e) {
            System.out.println("-------- ERRO: " + e.getMessage() + " --------");
        }
    }

    private DadosLivro getDadosLivro() {
        System.out.println("Digite o nome do Livro para busca:");
        String nomeLivro = Leitura.nextLine().trim();
        System.out.println("Em qual tradução? (digite o código ou o nome)");
        Linguas.listarFormatado().forEach(System.out::println);

        String entradaLingua = Leitura.nextLine().trim();

        Linguas linguaEscolhida;
        try {
            linguaEscolhida = Linguas.fromAPI(entradaLingua.toLowerCase());
        } catch (IllegalArgumentException e) {
            linguaEscolhida = Linguas.fromInterface(entradaLingua);
        } final Linguas linguaFinal = linguaEscolhida;

        String nomeLivroFormatado = URLEncoder.encode(nomeLivro, StandardCharsets.UTF_8);

        String url = ENDERECO
                + nomeLivroFormatado
                + "&languages=" + linguaEscolhida.getLinguaAPI();

        var json = consumo.obterDados(url);

        TextoGutendex resposta = conversor.obterDados(json, TextoGutendex.class);
        return resposta.results().stream()
                .filter(l -> l.linguas() != null &&
                        l.linguas().stream()
                                .anyMatch(lang -> lang.equalsIgnoreCase(linguaFinal.getLinguaAPI())))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "Nenhum livro encontrado com o título \"" + nomeLivro + "\" no idioma " + linguaFinal.getLinguaAPI()
                ));
    }


    private void ListarLivrosRegistrados() {
        livros = repositorio.findAll();
        livros.stream()
                .sorted(Comparator.comparing(Livro::getTitulo))
                .forEach(System.out::println);
    }

    private void ListarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("Nenhum autor registrado.");
            return;
        }

        autores.stream()
                .sorted(Comparator.comparing(Autor::getNome))
                .forEach(System.out::println);
    }

    private void ListarAutoresVivosPorAno() {
        System.out.println("Digite o ano:");
        int ano = Leitura.nextInt();
        Leitura.nextLine();

        List<Autor> vivosComMorte = autorRepository
                .findByNascimentoLessThanEqualAndMorteGreaterThanEqual(ano, ano);

        List<Autor> vivosSemMorte = autorRepository
                .findByNascimentoLessThanEqualAndMorteIsNull(ano);

        List<Autor> vivos = new ArrayList<>();
        vivos.addAll(vivosComMorte);
        vivos.addAll(vivosSemMorte);

        if (vivos.isEmpty()) {
            System.out.println("Nenhum autor encontrado baseado nesse ano");
            return;
        }

        vivos.stream()
                .sorted(Comparator.comparing(Autor::getNome))
                .forEach(System.out::println);
    }

    private void ListarLivrosPorIdioma() {
        System.out.println("Qual tradução você está buscando?");
        Linguas.listarFormatado().forEach(System.out::println);
        var nomeLingua = Leitura.nextLine();
        Linguas lingua;
        try {
            lingua = Linguas.fromAPI(nomeLingua.toLowerCase());
        } catch (IllegalArgumentException e) {
            try {
                lingua = Linguas.fromInterface(nomeLingua);
            } catch (IllegalArgumentException ex) {
                System.out.println("Idioma inválido.");
                return;
            }
        }
        List<Livro> livrosPorIdioma = repositorio.findByLingua(lingua);
        if (livrosPorIdioma.isEmpty()) {
            System.out.println("Nenhum livro encontrado nesse idioma.");
            return;
        }
        livrosPorIdioma.forEach(System.out::println);
    }

    private void ListarLivrosPorDownload(){
        System.out.println("Os 10 livros mais baixados salvos no banco de dados");
        List<Livro> topLivros = repositorio.findTop10ByOrderByQntDownloadDesc();
        topLivros.forEach(System.out::println);
    }

}
