package com.example.jpaproject.domain.item;

import com.example.jpaproject.domain.Category;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
// 한테이블에 다 때려박기 전략
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// book이면
@DiscriminatorColumn(name = "dtype")
@Data
public abstract class Item{
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuatity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

}
