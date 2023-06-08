package com.vodafone.ecommerce.controller;

import com.vodafone.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private CategoryService categoryService;

    @Autowired

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
}