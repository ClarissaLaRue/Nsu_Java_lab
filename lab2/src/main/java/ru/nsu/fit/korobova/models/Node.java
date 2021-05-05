package ru.nsu.fit.korobova.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
@Builder
public class Node {
    private BigInteger id;

    private String user;

    private List<Tag> tags;
}
