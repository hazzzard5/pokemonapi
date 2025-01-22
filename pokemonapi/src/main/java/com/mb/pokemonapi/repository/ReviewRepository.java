package com.mb.pokemonapi.repository;

import com.mb.pokemonapi.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    //here is the way to add more query
    //using the findBy keyword
    List<Review> findByPokemonId(int pokemonId);
}
