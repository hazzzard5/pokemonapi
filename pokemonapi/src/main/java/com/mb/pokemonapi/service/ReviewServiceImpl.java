package com.mb.pokemonapi.service;

import com.mb.pokemonapi.dto.ReviewDto;
import com.mb.pokemonapi.exceptions.PokemonNotFoundException;
import com.mb.pokemonapi.model.Review;
import com.mb.pokemonapi.model.Pokemon;
import com.mb.pokemonapi.repository.PokemonRepository;
import com.mb.pokemonapi.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService{

    private ReviewRepository reviewRepository;
    private PokemonRepository pokemonRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, PokemonRepository pokemonRepository) {
        this.reviewRepository = reviewRepository;
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public ReviewDto createReview(int pokemonId, ReviewDto reviewDto) {
        //1. mapping the dto to review since I need to original model(review) to be able to pass back to the DB for changes
        Review review = mapToEntity(reviewDto);
        //2. now create a new pokemon object
        // and that object will be the pokemon that has this id. if you don't find it then throw message
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() -> new PokemonNotFoundException("Pokemon with associated review not found"));
        //3. you will now attach this review to this pokemon
        review.setPokemon(pokemon);
        //4. the new object of review will now be saved to the DB
        Review newReview = reviewRepository.save(review);
        //map this back to the DTO so the user doesnt see the internal entities(model fields)
        return mapToDto(newReview);
    }

    @Override
    public List<ReviewDto> getReviewByPokemonId(int id) {
        List<Review> reviews = reviewRepository.findByPokemonId(id);

        return reviews.stream().map(review -> mapToDto(review)).collect(Collectors.toList());
    }


    private ReviewDto mapToDto(Review review){
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setTitle(review.getTitle());
        reviewDto.setContent(review.getContent());
        reviewDto.setStars(review.getStars());
        return reviewDto;
    }

    private Review mapToEntity(ReviewDto reviewDto) {
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());
        return review;
    }



}
