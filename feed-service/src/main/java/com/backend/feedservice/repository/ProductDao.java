package com.backend.feedservice.repository;

import com.backend.feedservice.entity.Product;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao {
    private RedisTemplate temp;
    public static final String HASH_KEY = "product";
    public Product save(Product product){
        temp.opsForHash().put(HASH_KEY, product.getId(), product);
        return product;
    }

    public List<Product> findAll(){
        return temp.opsForHash().values(HASH_KEY);
    }

    public Object findProductById(int id){
        return temp.opsForHash().get(HASH_KEY,id);
    }
    public String deleteProduct(int id){
        temp.opsForHash().delete(HASH_KEY,id);
        return "product removed";
    }
}
