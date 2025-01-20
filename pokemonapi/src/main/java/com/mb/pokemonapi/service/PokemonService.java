package com.mb.pokemonapi.service;

import com.mb.pokemonapi.dto.PokemonDto;
import com.mb.pokemonapi.dto.PokemonResponse;

import java.util.List;

public interface PokemonService {

    PokemonDto createPokemon(PokemonDto pokemonDto);

    void deletePokemonId(int id);

    PokemonResponse getAllPokemon(int pageNo, int pageSize);

    PokemonDto getPokemonById(int id);

    PokemonDto updatePokemon(PokemonDto pokemonDto, int id);

}


