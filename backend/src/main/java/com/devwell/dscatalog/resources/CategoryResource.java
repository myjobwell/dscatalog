package com.devwell.dscatalog.resources;


import com.devwell.dscatalog.entities.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/category")
public class CategoryResource {
    //implementa o controlador rest

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        List<Category> list = new ArrayList<>();
        list.add(new Category(1L, "BOOK"));
        list.add(new Category(2L, "ELECTRONICS"));
        return ResponseEntity.ok().body(list);
    }



}
