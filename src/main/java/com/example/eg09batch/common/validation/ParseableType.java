package com.example.eg09batch.common.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum ParseableType {
    TO_SHORT("Short"),
    TO_INT("Integer"),
    TO_LONG("Long"),
    TO_DOUBLE("Double"),
    TO_FLOAT("Float");
    @Getter
    private final String friendlyName;
}