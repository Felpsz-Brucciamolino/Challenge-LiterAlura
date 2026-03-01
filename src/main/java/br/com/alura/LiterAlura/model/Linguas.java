package br.com.alura.LiterAlura.model;

import java.util.Arrays;
import java.util.List;

public enum Linguas {
    ESPANHOL("es", "Espanhol"),
    INGLES("en", "Inglês"),
    FRANCES("fr", "Francês"),
    PORTUGUES("pt","Português"),
    RUSSO("ru","Russo");

    private String linguaAPI;

    private String linguaInterface;


    public String getLinguaAPI() {
        return linguaAPI;
    }

    public String getLinguaInterface() {
        return linguaInterface;
    }


    public static List<String> listarFormatado() {
        return Arrays.stream(Linguas.values())
                .map(l -> l.getLinguaAPI().toUpperCase() + " - " + l.getLinguaInterface())
                .toList();
    }


    Linguas(String linguaAPI, String linguaInterface){
        this.linguaAPI = linguaAPI;
        this.linguaInterface = linguaInterface;
    }

    public static Linguas fromAPI(String text){
        for(Linguas categoria : Linguas.values()){
            if (categoria.linguaAPI.equalsIgnoreCase(text)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma língua encontrada para o livro");
    }

    public static Linguas fromInterface(String text){
        for(Linguas categoria : Linguas.values()){
            if (categoria.linguaInterface.equalsIgnoreCase(text)){
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma língua encontrada para o livro");
    }



}