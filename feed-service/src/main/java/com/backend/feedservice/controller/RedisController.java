package com.backend.feedservice.controller;

import com.backend.feedservice.entity.Product;
import com.backend.feedservice.repository.ProductDao;
import com.backend.feedservice.service.PostService;
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
    private final ProductDao dao;
    private final PostService postService;

    @PostMapping
//    public Product save(@RequestBody Product product){
//        System.out.println(product.getId());
//        System.out.println(product.getName());
//        System.out.println(product.getQty());
//        System.out.println(product.getPrice());
//
//        return dao.save(product);
//    }
    public Product save(@RequestBody Product product){
        System.out.println(product.getId());
        System.out.println(product.getName());
        System.out.println(product.getQty());
        System.out.println(product.getPrice());

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
