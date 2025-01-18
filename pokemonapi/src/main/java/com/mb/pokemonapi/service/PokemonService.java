package com.mb.pokemonapi.service;

import com.mb.pokemonapi.dto.PokemonDto;

import java.util.List;

public interface PokemonService {

    PokemonDto createPokemon(PokemonDto pokemonDto);

    void deletePokemonId(int id);

    List<PokemonDto> getAllPokemon();

    PokemonDto getPokemonById(int id);

    PokemonDto updatePokemon(PokemonDto pokemonDto, int id);

}


