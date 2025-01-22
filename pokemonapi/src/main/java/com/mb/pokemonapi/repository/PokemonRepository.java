package com.mb.pokemonapi.repository;

import com.mb.pokemonapi.model.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<Pokemon, Integer> {

    //Query methods to expand what we can get from the DB
    //the naming structure will play a major part
    //example: findBy [findByTitleLike, findByTitle...etc]
}
