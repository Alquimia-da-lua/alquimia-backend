package com.alquimia.backend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CategoriaEnum {
    CABELO("cabelo"),
    PELE("pele"),
    AROMATIZANTE("aromatizante");

    private final String description;
}
