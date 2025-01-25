package com.mb.pokemonapi.controller;

import com.mb.pokemonapi.dto.ReviewDto;
import com.mb.pokemonapi.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ReviewController {

    private ReviewService reviewService;

    //constructor
    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("pokemon/{pokemonId}/review")
    public ResponseEntity<ReviewDto> createReview(@PathVariable(value = "pokemonId") int pokemonId, @RequestBody ReviewDto reviewDto){
        return new ResponseEntity<>(reviewService.createReview(pokemonId, reviewDto), HttpStatus.CREATED);
    }

    @GetMapping("pokemon/{pokemondId}/reviews")
    public List<ReviewDto> getReviewByPokemonId(@PathVariable(value = "pokemonId") int pokemonId){
        return reviewService.getReviewByPokemonId(pokemonId);
    }

    @GetMapping("pokemon/{pokemonId}/reviews/{id}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable(value = "pokemonId") int pokemonId, @PathVariable(value = "id") int reviewId) {
        ReviewDto reviewDto = reviewService.getReviewById(pokemonId, reviewId);
        return new ResponseEntity<>(reviewDto, HttpStatus.OK);
    }

    @PutMapping("pokemon/{pokemonId}/reviews/{id}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable(value = "pokemonId") int pokemonId, @PathVariable(value = "id") int reviewId, @RequestBody ReviewDto reviewDto){
        ReviewDto updatedReviewDto = reviewService.updateReview(pokemonId, reviewId, reviewDto);
        return new ResponseEntity<>(updatedReviewDto, HttpStatus.OK);
    }

    @DeleteMapping("pokemon/{pokemonId}/reviews/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable(value = "pokemonId") int pokemonId, @PathVariable(value= "reviewId") int reviewId){
        reviewService.deleteReview(pokemonId, reviewId);
        return new ResponseEntity<>("Review has been deleted", HttpStatus.OK);
    }

}



