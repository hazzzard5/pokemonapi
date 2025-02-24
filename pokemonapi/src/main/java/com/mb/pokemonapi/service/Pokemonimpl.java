package com.mb.pokemonapi.service;

import com.mb.pokemonapi.dto.PokemonDto;
import com.mb.pokemonapi.dto.PokemonResponse;
import com.mb.pokemonapi.exceptions.PokemonNotFoundException;
import com.mb.pokemonapi.model.Pokemon;
import com.mb.pokemonapi.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
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
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(()-> new PokemonNotFoundException("Pokemon could not be delted"));
        //now once we are inside the repository then we can now utilize the .delete method
        pokemonRepository.delete(pokemon);
    }

    @Override
    public PokemonResponse getAllPokemon(int pageNo, int pageSize) {
        Pageable pageable = (Pageable) PageRequest.of(pageNo, pageSize);
        // Step 1: Fetch all Pokémon entities from the database
        Page<Pokemon> pokemons = pokemonRepository.findAll((org.springframework.data.domain.Pageable) pageable);
        List<Pokemon> listOfPokemon = pokemons.getContent();
        // Step 2: Convert the List<Pokemon> to List<PokemonDto> using a mapping function
        List<PokemonDto> content =  listOfPokemon.stream().map(p -> mapToDto(p)).collect(Collectors.toList());

        PokemonResponse pokemonResponse = new PokemonResponse();
        pokemonResponse.setContent(content);
        pokemonResponse.setPageNo(pokemons.getNumber());
        pokemonResponse.setPageSize(pokemons.getSize());
        pokemonResponse.setTotalElements(pokemons.getTotalElements());
        pokemonResponse.setTotalPages(pokemons.getTotalPages());
        pokemonResponse.setLast(pokemons.isLast());

        return pokemonResponse;
    }

    @Override
    public PokemonDto getPokemonById(int id) {
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(()-> new PokemonNotFoundException("Pokemon could not be found"));
        return mapToDto(pokemon);
    }

    @Override
    public PokemonDto updatePokemon(PokemonDto pokemonDto, int id) {
        // Step 1: create a new object based on this pokemon if you can find that pokemon in the repository
        Pokemon pokemon = pokemonRepository.findById(id).orElseThrow(()-> new PokemonNotFoundException("Pokemon cannot be updated"));

        //step 2: since we are pushing the update back to the database it needs to match the type
        //so we need to put it back in the model
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());

        Pokemon updatePokemon = pokemonRepository.save(pokemon);
        return mapToDto(updatePokemon);
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
