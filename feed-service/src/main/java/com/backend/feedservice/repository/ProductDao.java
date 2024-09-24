package com.backend.feedservice.repository;

import com.backend.feedservice.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductDao {

    private final RedisTemplate<String, Object> redisTemplate;
    public static final String HASH_KEY = "product";
    public Product save(Product product){
        redisTemplate.opsForHash().put(HASH_KEY, String.valueOf(product.getId()), product);
        return product;
    }

    public List<Object> findAll(){
        return redisTemplate.opsForHash().values(HASH_KEY);
    }

    public Object findProductById(int id){
        return redisTemplate.opsForHash().get(HASH_KEY,String.valueOf(id));
    }
    public String deleteProduct(int id){
        redisTemplate.opsForHash().delete(HASH_KEY,String.valueOf(id));
        return "product removed";
    }
}
