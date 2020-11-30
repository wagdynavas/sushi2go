package com.wagdynavas.sushi2go.controllers;


import com.wagdynavas.sushi2go.model.Order;
import com.wagdynavas.sushi2go.model.Product;
import com.wagdynavas.sushi2go.service.ProductService;
import com.wagdynavas.sushi2go.util.type.CategoryTypes;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final  ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list")
    public ModelAndView listMenu() {
        ModelAndView mv =  productService.getAllProductsSortByCategory();
        Order order = new Order();
        order.setProduct(new Product());
        order.getProduct().setQuantity(0);
        mv.addObject("order", order);
        mv.addObject("addProductToOrder", new Product());


        return mv;
    }

    @GetMapping("/manage/{id}")
    public ModelAndView manageProduct(@PathVariable("id") Long id) {
        Optional<Product> optionalProduct = productService.getProductById(id);
        ModelAndView view = new ModelAndView();
        if (!optionalProduct.isPresent()) {
            view.setViewName("errors/default");
            return view;
        }

        Product product = optionalProduct.get();
        view.setViewName("product/manage-product");
        view.addObject("product", product);
        view.addObject("categories", CategoryTypes.values());
        view.addObject("h1", "Update Product");

        return view;
    }

    @GetMapping("/create")
    public ModelAndView addProduct() {
        ModelAndView view = new ModelAndView();
        view.setViewName("product/manage-product");
        view.addObject("product", new Product());
        view.addObject("categories", CategoryTypes.values());
        return view;
    }

    @PostMapping("/create")
    public ModelAndView createOrUpdate(@Valid Product product, BindingResult result, @RequestParam("imageFile") MultipartFile imageFile) {
        return productService.createProduct(product, result, imageFile);
    }

    @GetMapping("/delete/{id}")
    public String delete( @PathVariable("id")  Long id) {
        productService.removeProduct(id);
        return "redirect:/products/list";
    }

    @GetMapping("/add-to-order")
    public ModelAndView addProductToOrder(@Valid Order order, BindingResult result) {
        Optional<Product> optionalProduct = productService.getProductById(order.getProduct().getId());
        ModelAndView view = new ModelAndView();
        view.setViewName("redirect:/products/list");
        List products = order.getProducts();

        if (products == null) {
            products = new ArrayList();
        }

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            int quantity = product.getQuantity();
            for (int i = 0; i > quantity; i++) {
                products.add(product);
            }
            order.getProducts().addAll(products);
        }
        return view;
    }


    @GetMapping("/add/{product_quantity_id}")
    public Long addToProductQuantity(@PathVariable("product_quantity_id") String product_quantity_id) {

        return Long.valueOf(product_quantity_id) + 1;
    }

}
