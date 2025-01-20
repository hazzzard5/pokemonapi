package com.mb.pokemonapi.dto;

import lombok.Data;

import java.util.List;

//this is for the GETALL with pagination
@Data
public class PokemonResponse {
    private List<PokemonDto> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
