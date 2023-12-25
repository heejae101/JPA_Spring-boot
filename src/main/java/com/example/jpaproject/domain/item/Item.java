package com.example.jpaproject.domain.item;

import com.example.jpaproject.domain.Category;
import com.example.jpaproject.exception.NotEnoughStockException;
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

    // 비즈니스 로직에 데이터 수정 하는게 있는 것이 좋다.
    /**
     *  stock 증가
     */
    public void addStock(int quantity){
        this.stockQuatity+=quantity;
    }

    /**
     *  stock 감소
     */
    public void remove(int quantity){
        int restStock = this.stockQuatity - quantity;
        if(restStock < 0 ){
            throw new NotEnoughStockException("need more stock");
        }
    }

}
