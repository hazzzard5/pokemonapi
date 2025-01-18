package com.mb.pokemonapi.controller;


import com.mb.pokemonapi.dto.PokemonDto;
import com.mb.pokemonapi.model.Pokemon;
import com.mb.pokemonapi.service.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PokemonController {

    private PokemonService pokemonService;

    @Autowired
    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping("pokemon")
    //getting a full list of the pokemon using the ResponseEntity
    public ResponseEntity<List<PokemonDto>> getPokemons() {
        return new ResponseEntity<>(pokemonService.getAllPokemon(), HttpStatus.OK);
    }




    @GetMapping("pokemon/new/{id}")
    public Pokemon newPokemon(@PathVariable int id){
        return new Pokemon(id, "MewTwo", "physic");
    }

    @PostMapping("pokemon/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PokemonDto> createPokemon(@RequestBody PokemonDto pokemonDto){
        //inital example before we created the service + repository
//        return new ResponseEntity<>(pokemon,HttpStatus.CREATED);
        System.out.println(pokemonDto.getName());
        System.out.println(pokemonDto.getType());
        return new ResponseEntity<>(pokemonService.createPokemon(pokemonDto), HttpStatus.CREATED);
    }

    @DeleteMapping("pokemon/{id}/delete")
    public ResponseEntity<String> deletePokemon(@PathVariable("id") int pokemonId){
        //now we go into our service and trigger the method we created
        //in this case the delete method, and going to pass that parameter over
        pokemonService.deletePokemonId(pokemonId);
        //now return the HTTP status this and this message
        return new ResponseEntity<>("Pokemon has been deleted", HttpStatus.OK);
    }




}
