package ru.nsu.fit.korobova.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
public class Tag {
    private String key;

    private String value;

    private BigInteger nodeId;
}
