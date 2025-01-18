package com.mb.pokemonapi.exceptions;

public class PokemonNotFoundException extends RuntimeException{

    //here we are building a global exception
    private static final long serialVersionUID = 1;

    public PokemonNotFoundException(String message){
        //using the super from the RuntimeException
        super(message);
    }


}
