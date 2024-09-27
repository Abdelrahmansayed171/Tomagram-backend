package com.backend.feedservice.controller;

import com.backend.feedservice.dto.PostRequest;
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
    public void save(@RequestBody PostRequest req){
        postService.storePost(
                req.getId(), req.getContent(), req.getCreatedAt(),
                req.getLocation(), req.getUsername());
        postService.addPostToUserFeed(req.getUsername(), req.getId(), req.getCreatedAt());
        postService.markPostAsSeen("orcaABS", "3");
        postService.markPostAsSeen("orcaABS", "2");
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
