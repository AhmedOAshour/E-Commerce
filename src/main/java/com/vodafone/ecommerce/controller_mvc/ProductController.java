package com.vodafone.ecommerce.controller_mvc;

import com.vodafone.ecommerce.model.Category;
import com.vodafone.ecommerce.model.Product;
import com.vodafone.ecommerce.service.CategoryService;
import com.vodafone.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

// Homepage - All Products
// Product Details
// Add Product - Admin
// CartList

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    //TODO: page size according to spring profile?
    @GetMapping
    public ModelAndView getAllProducts(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "category", required = false) Long categoryId) { //TODO: cat_id?
        List<Product> allProducts = productService.getAllProducts(page, size, name, categoryId);
        List<Category> allCatgories = categoryService.getAllCategories();
        ModelAndView modelAndView = new ModelAndView("product_index");
        modelAndView.addObject("all_products",allProducts);
        modelAndView.addObject("all_categories",allCatgories);
        return modelAndView;
    }

    @GetMapping(value = "/{id}")
    public ModelAndView getProductById(@PathVariable(name = "id") Long id) {
        Product product = productService.getProductById(id);
        ModelAndView modelAndView = new ModelAndView("product_single_index");
        modelAndView.addObject("product",product);
        return modelAndView;
    }

    @PostMapping
    public ModelAndView addProduct(@RequestBody Product product) { //TODO: handle image
        Product productRes = productService.addProduct(product);
        ModelAndView modelAndView = new ModelAndView("product_update");
        modelAndView.addObject("product",product);
        return modelAndView;
    }

    @PutMapping(value = "/{id}")
    public ModelAndView updateProduct(@RequestBody Product product, @PathVariable(name = "id") Long id) {
        Product productRes = productService.updateProduct(product, id);
        ModelAndView modelAndView = new ModelAndView("product_update");
        modelAndView.addObject("product",product);
        return modelAndView;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable(name = "id") Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
