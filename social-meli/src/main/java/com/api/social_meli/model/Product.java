package com.api.social_meli.model;

import com.api.social_meli.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product implements Identifiable{
    private int productId;
    private String name;
    private String type;
    private String brand ;
    private String colour;
    private String notes;

    @Override
    public int getId() {
        return this.productId;
    }

    public Product(ProductDto dto){
        this.name = dto.getProductName();
        this.type = dto.getType();
        this.brand = dto.getBrand();
        this.colour = dto.getColor();
        this.notes = dto.getNotes();
    }

}
