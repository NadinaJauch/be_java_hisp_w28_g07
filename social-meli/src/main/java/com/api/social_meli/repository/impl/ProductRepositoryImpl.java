package com.api.social_meli.repository.impl;

import com.api.social_meli.model.Post;
import com.api.social_meli.model.Product;
import com.api.social_meli.model.User;
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


    public ProductRepositoryImpl() throws IOException {

        loadDataBase();
    }

    private void loadDataBase() throws IOException {
        File file;
        ObjectMapper objectMapper = new ObjectMapper();
        List<Product> products ;

        file= ResourceUtils.getFile("classpath:products.json");
        products= objectMapper.readValue(file,new TypeReference<List<Product>>(){});
        this.entities = products;
    }
}
