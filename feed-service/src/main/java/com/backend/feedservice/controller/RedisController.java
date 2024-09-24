package com.backend.feedservice.controller;

import com.backend.feedservice.entity.Product;
import com.backend.feedservice.repository.ProductDao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/redis")
public class RedisController {
    private ProductDao dao;

    @PostMapping
    public Product save(@RequestBody Product product){
        return dao.save(product);
    }
    @GetMapping
    public List<Object> getAllProducts(){
        return dao.findAll();
    }
    @GetMapping("/{id}")
    public Object findProduct(@PathVariable int id){
        return dao.findProductById(id);
    }
    @DeleteMapping
    public String remove(@PathVariable int id){
        return dao.deleteProduct(id);
    }

}
