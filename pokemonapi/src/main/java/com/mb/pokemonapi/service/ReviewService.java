package com.mb.pokemonapi.service;

import com.mb.pokemonapi.dto.ReviewDto;

import java.util.List;

public interface ReviewService {

    ReviewDto createReview(int pokemonId, ReviewDto reviewDto);

    List<ReviewDto> getReviewByPokemonId(int id);

    ReviewDto getReviewById(int reviewId, int pokemonId);

    ReviewDto updateReview(int pokemonId, int reviewId, ReviewDto reviewDto);

    //need pokemon id as well because we need to find that relationship to the pokemon
    void deleteReview(int pokemondId, int reviewId);
}
