package com.api.social_meli.repository.impl;

import com.api.social_meli.model.Product;
import com.api.social_meli.repository.IProductRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Repository
public class ProductRepositoryImpl extends BaseRepository<Product> implements IProductRepository {
    protected void loadDataBase() {
        try {
            File file;
            ObjectMapper objectMapper = new ObjectMapper();
            List<Product> objects;
            file = ResourceUtils.getFile("classpath:products.json");
            objects = objectMapper.readValue(file,new TypeReference<List<Product>>(){});
            this.entities = objects;
        }
        catch(IOException ignored) {
        }
    }
}
