package com.wagdynavas.sushi2go.controllers;


import com.wagdynavas.sushi2go.model.Order;
import com.wagdynavas.sushi2go.model.Product;
import com.wagdynavas.sushi2go.service.ProductService;
import com.wagdynavas.sushi2go.util.SessionUtil;
import com.wagdynavas.sushi2go.util.type.CategoryTypes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
//. Any object can be added to the model in controller and it will stored in session if its name matches with the argument in @SessionAttributes
@SessionAttributes("order")
public class ProductController {

    private final  ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Reload The view without having to add new Objects to it
     * @return
     */
    private ModelAndView reloadProductList() {
        return productService.getAllProductsSortByCategory();
    }

    /**
     * get <code>Product</code> that will be edited
     *
     * @param id <code>Product</code> id
     * @return <code>ModelAndView</code>
     */
    @GetMapping("/manage/{id}")
    public ModelAndView manageProduct(@PathVariable("id") Long id) {
        Optional<Product> optionalProduct = productService.getProductById(id);
        ModelAndView view = new ModelAndView();
        if (optionalProduct.isEmpty()) {
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

    /**
     *
     *
     * @return <code>ModelAndView</code>
     */
    @GetMapping("/list")
    public ModelAndView listProduct(HttpServletRequest request) {
        ModelAndView mv =  productService.getAllProductsSortByCategory();
        Order order = new Order();
        Integer cartQuantity = SessionUtil.getCartQuantity(request);
        if (cartQuantity > 0) {
            order = (Order) request.getSession().getAttribute("order");
        }

        mv.addObject("cartQuantity", SessionUtil.getCartQuantity(request));
        mv.addObject("order", order);
        mv.addObject("menuItems", CategoryTypes.values());
        mv.addObject("menuLunches", createMenuItems(CategoryTypes.values(),CategoryTypes.LUNCH ));
        mv.addObject("menuDinners", createMenuItems(CategoryTypes.values(),CategoryTypes.DINNER ));

        return mv;
    }

    @GetMapping("/create")
    public ModelAndView addProduct() {
        ModelAndView view = new ModelAndView();
        view.setViewName("product/manage-product");
        view.addObject("product", new Product());
        view.addObject("categories", CategoryTypes.values());
        return view;
    }

    /**
     * Create new <code>Product</code>
     *
     * @param product <code>Product</code> that will be saved in the database
     * @param result used to make sure that <code>Product</code> attributes are valid
     * @param imageFile <code>Product</code> image
     *
     * @return ModelAndView
     */
    @PostMapping("/create")
    public ModelAndView createOrUpdate(@Valid Product product, BindingResult result, @RequestParam("imageFile") MultipartFile imageFile) {
        return productService.createProduct(product, result, imageFile);
    }

    /**
     * Remove <code>Product</code> from database
     *
     * @param id <dode>Product</dode> id
     * @return view redirect:/products/list
     */
    @GetMapping("/delete/{id}")
    public String delete( @PathVariable("id")  Long id) {
        productService.removeProduct(id);
        return "redirect:/products/list";
    }

    /**
     * Add <dode>Product</dode> and customer instructions to <code>Order</code>
     *
     * @param order customer order
     * @param request only using the method <code>getParameter</code>
     *
     *
     * @return the view
     */
    @PostMapping("/add-to-order")
    public ModelAndView addProductToOrder( Order order, HttpServletRequest request) {
        String productId = request.getParameter("product-id");
        String customersInstructions = request.getParameter("customers-instructions");


        Optional<Product> optionalProduct = productService.getProductById(Long.valueOf(productId));
        List<Product> products = order.getProducts();


        if (products == null) {
            products = new ArrayList<>();
        }

        String productQuantity = request.getParameter("product_quantity");
        Integer cartQuantity  = SessionUtil.getCartQuantity(request);

        int quantity = Integer.parseInt(productQuantity);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            BigDecimal productPrice = productService.calculatePrice(product.getPrice(), quantity);
            product.setPrice(productPrice);
            product.setQuantity(quantity);
            product.setCustomerInstructions(customersInstructions);
            products.add(product);
            order.setProducts(products);
        }

        cartQuantity += quantity;
        request.getSession().setAttribute("cartQuantity", cartQuantity);
        ModelAndView view = reloadProductList();
        view.addObject("cartQuantity", cartQuantity);

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
             menuItems = Arrays.stream(categoryTypes)
                     .filter(ct -> ct.getValue()
                             .startsWith("lunch_"))
                     .toList();
        } else {
            menuItems = Arrays.stream(categoryTypes)
                    .filter(ct -> !ct.getValue()
                            .startsWith("lunch") && !ct.getValue().equals("dinner"))
                    .toList();
        }

        return menuItems;
    }

 }
