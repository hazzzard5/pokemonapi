package com.mb.pokemonapi.service;

import com.mb.pokemonapi.dto.PokemonDto;
import com.mb.pokemonapi.model.Pokemon;
import com.mb.pokemonapi.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class Pokemonimpl implements PokemonService {

    private PokemonRepository pokemonRepository;

    //best practice is below on creating a constructor for a repository
    //so it will be easier to unit test in the future
    @Autowired
    public Pokemonimpl(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public PokemonDto createPokemon(PokemonDto pokemonDto) {
        Pokemon pokemon = new Pokemon();
        //instead of creating a mapper, just get use to mapping it yourself.
        //this is usually how it is in production environments

        //map fields from pokemonDTO to Pokemon
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());

        // Save the entity to the repository
        Pokemon newPokemon = pokemonRepository.save(pokemon);


        // Map fields from the saved Pokemon entity back to PokemonDTO
        PokemonDto pokemonResponse = new PokemonDto();
        pokemonResponse.setId(newPokemon.getId());
        pokemonResponse.setName(newPokemon.getName());
        pokemonResponse.setType(newPokemon.getType());
        return pokemonResponse;
    }

    @Override
    public void deletePokemonId(int id) {
        //go into the repository
        //find the pokemon by id
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow();
        //now once we are inside the repository then we can now utilize the .delete method
        pokemonRepository.delete(pokemon);
    }

    @Override
    public List<PokemonDto> getAllPokemon() {
        // Step 1: Fetch all Pokémon entities from the database
        List<Pokemon> pokemon = pokemonRepository.findAll();
        // Step 2: Convert the List<Pokemon> to List<PokemonDto> using a mapping function
        return pokemon.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
    }


    //Here is the beginner way to do the above ^
//    @Override
//    public List<PokemonDto> getAllPokemon() {
//        List<Pokemon> pokemons = pokemonRepository.findAll(); // Get the list of Pokémon from the repository
//        List<PokemonDto> pokemonDtos = new ArrayList<>(); // Create a new list to store the DTOs
//
//        for (Pokemon pokemon : pokemons) {
//            PokemonDto dto = new PokemonDto(pokemon.getId(), pokemon.getName(), pokemon.getType());
//            pokemonDtos.add(dto); // Add the transformed DTO to the new list
//        }
//
//        return pokemonDtos; // Return the new list
//    }


    //here we are creating a mapper by hand
    private PokemonDto mapToDto(Pokemon pokemon){
        PokemonDto pokemonDto = new PokemonDto();
        pokemonDto.setId(pokemon.getId());
        pokemonDto.setName(pokemon.getName());
        pokemonDto.setType(pokemon.getType());
        return pokemonDto;
    }

    //another mapper
    private Pokemon mapToEntity(PokemonDto pokemonDto){
        Pokemon pokemon = new Pokemon();
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());
        return pokemon;
    }


}
