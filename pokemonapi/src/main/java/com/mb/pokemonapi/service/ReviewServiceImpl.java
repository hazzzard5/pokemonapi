package com.mb.pokemonapi.service;

import com.mb.pokemonapi.dto.ReviewDto;
import com.mb.pokemonapi.exceptions.PokemonNotFoundException;
import com.mb.pokemonapi.exceptions.ReviewNotFoundException;
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

    @Override
    public ReviewDto getReviewById(int reviewId, int pokemonId) {
        //1. find the pokemon from the repository
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() -> new PokemonNotFoundException("Pokemon with associated ID not found"));
        //2. find the review from the repository
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review associated with ID not found"));
        //3. go into the review to get the pokemon's id and then compare it to the pokemon.getid
        if(review.getPokemon().getId() != pokemon.getId()){
            throw new ReviewNotFoundException("This review does not belong to a pokemon");
        }

        return mapToDto(review);
    }

    @Override
    public ReviewDto updateReview(int pokemonId, int reviewId, ReviewDto reviewDto) {
        //1. find the pokemon from the repository
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() -> new PokemonNotFoundException("Pokemon with associated ID not found"));
        //2. find the review from the repository
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review associated with ID not found"));
        //3. go into the review to get the pokemon's id and then compare it to the pokemon.getid
        if(review.getPokemon().getId() != pokemon.getId()){
            throw new ReviewNotFoundException("This review does not belong to a pokemon");
        }

        //okay now we have to get the DTO object and map it back to our review since \n
        //we are pushing that back to the database

        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setStars(reviewDto.getStars());

        //push this back via the repository
        Review updateReview = reviewRepository.save(review);
        //return back to DTO since we dont want the end users to see the original entity fields
        return mapToDto(updateReview);
    }

    @Override
    public void deleteReview(int pokemonId, int reviewId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(() -> new PokemonNotFoundException("unable to find that associated pokemon"));

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("unable to find that review to delete"));

        if(review.getPokemon().getId() != pokemon.getId()){
            throw new ReviewNotFoundException("This review is unable to be found");
        }

        reviewRepository.delete(review);
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
