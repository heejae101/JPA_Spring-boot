package com.example.jpaproject.domain.item;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
@DiscriminatorValue("B")
public class Book extends Item{
    private String author;
    private String isbn;
}
