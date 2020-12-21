package com.wagdynavas.sushi2go.controllers;


import com.wagdynavas.sushi2go.model.Category;
import com.wagdynavas.sushi2go.model.Order;
import com.wagdynavas.sushi2go.model.Product;
import com.wagdynavas.sushi2go.service.ProductService;
import com.wagdynavas.sushi2go.util.type.CategoryTypes;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
//. Any object can be added to the model in controller and it will stored in session if its name matches with the argument in @SessionAttributes
@SessionAttributes("order") //
public class ProductController {

    private final  ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list")
    public ModelAndView listProduct() {
        ModelAndView mv =  productService.getAllProductsSortByCategory();
        Order order = new Order();
        order.setProduct(new Product());
        order.getProduct().setQuantity(0);
        mv.addObject("order", order);
        mv.addObject("menuItems", CategoryTypes.values());
        mv.addObject("menuLunches", createMenuItems(CategoryTypes.values(),CategoryTypes.LUNCH ));
        mv.addObject("menuDinners", createMenuItems(CategoryTypes.values(),CategoryTypes.DINNER ));

        return mv;
    }

    /**
     * Reload The view without having to add new Objects to it
     * @return
     */
    private ModelAndView reloadProductList() {
        ModelAndView mv =  productService.getAllProductsSortByCategory();
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

    @PostMapping("/add-to-order")
    public ModelAndView addProductToOrder( Order order, HttpServletRequest  request) {
        String productId = request.getParameter("product-id");
        Optional<Product> optionalProduct = productService.getProductById(Long.valueOf(productId));
        List products = order.getProducts();


        if (products == null) {
            products = new ArrayList();
        }

        String productQuantity = request.getParameter("product_quantity");

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            int quantity = Integer.valueOf(productQuantity);
            for (int i = 0; i < quantity; i++) {
                products.add(product);
            }
            order.setProducts(products);
        }

        ModelAndView view = listProduct();
        return view;
    }

    /**
     *
     * @param categoryTypes
     * @param menuType define menu type, ex: Dinner, Lunch etc..
     * @return
     */
    private List<CategoryTypes> createMenuItems(CategoryTypes[] categoryTypes, CategoryTypes menuType) {
        List<CategoryTypes> menuItems;
        if (menuType == CategoryTypes.LUNCH) {
             menuItems = Arrays.asList(categoryTypes).stream().filter(ct -> ct.getValue().startsWith("lunch_")).collect(Collectors.toList());
        } else {
            menuItems = Arrays.asList(categoryTypes).stream().filter(ct -> !ct.getValue().startsWith("lunch") && !ct.getValue().equals("dinner")).collect(Collectors.toList());
        }

        return menuItems;
    }

 }
