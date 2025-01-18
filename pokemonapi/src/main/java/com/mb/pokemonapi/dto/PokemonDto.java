package com.mb.pokemonapi.dto;

//DTO - they are similar to the model but we can say we only want to get these specific data
//example if the model had the password we wouldn't want anyone querying that data so we build a DTO
//just another layer of separation

import lombok.Data;

@Data
public class PokemonDto {
    private int id;
    private String name;
    private String type;

    public PokemonDto(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public PokemonDto() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
