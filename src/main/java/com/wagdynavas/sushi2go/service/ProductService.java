package com.wagdynavas.sushi2go.service;

import com.wagdynavas.sushi2go.model.Product;
import com.wagdynavas.sushi2go.repository.ProductRepository;
import com.wagdynavas.sushi2go.util.files.ImagesUtil;
import com.wagdynavas.sushi2go.util.type.CategoryTypes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;



    public ModelAndView getAllProductsSortByCategory() {
        CategoryTypes[] categories  = CategoryTypes.values();
        ModelAndView view = new ModelAndView();
        view.setViewName("product/products");
        final String quantity = "_quantity";
        String quantityName = "";
            for (CategoryTypes categoryType : categories) {
                List<Product> products = productRepository.findAllByCategory(categoryType.toString());

                switch (categoryType) {
                    case LUNCH_SUSHI: {
                        view.addObject(CategoryTypes.LUNCH_SUSHI.toString(), products);
                        quantityName = CategoryTypes.LUNCH_SUSHI.toString().toLowerCase() + quantity;
                        break;

                    } case LUNCH_ENTREES: {
                        view.addObject(CategoryTypes.LUNCH_ENTREES.toString(), products);
                        quantityName =  CategoryTypes.LUNCH_ENTREES.toString().toLowerCase() + quantity;
                        break;
                    } case LUNCH_NOODLES: {
                        view.addObject(CategoryTypes.LUNCH_NOODLES.toString(), products);
                        quantityName =  CategoryTypes.LUNCH_NOODLES.toString().toLowerCase() + quantity;
                        break;
                    } case FUNKY_ROLLS: {
                        view.addObject(CategoryTypes.FUNKY_ROLLS.toString(), products);
                        quantityName =  CategoryTypes.FUNKY_ROLLS.toString().toLowerCase() + quantity;
                        break;
                    } case MAKI: {
                        view.addObject(CategoryTypes.MAKI.toString(), products);
                        quantityName =  CategoryTypes.MAKI.toString().toLowerCase() + quantity;
                        break;
                    } case NIGIRI_AND_MAKI_SUSHI: {
                        view.addObject(CategoryTypes.NIGIRI_AND_MAKI_SUSHI.toString(), products);
                        quantityName =  CategoryTypes.NIGIRI_AND_MAKI_SUSHI.toString().toLowerCase() + quantity;
                        break;
                    } case SASHIMI: {
                        view.addObject(CategoryTypes.SASHIMI.toString(), products);
                        quantityName =  CategoryTypes.SASHIMI.toString().toLowerCase() + quantity;
                        break;
                    } case APPETIZER: {
                        view.addObject(CategoryTypes.APPETIZER.toString(), products);
                        quantityName =  CategoryTypes.APPETIZER.toString().toLowerCase() + quantity;
                        break;
                    } case DESSERT: {
                        view.addObject(CategoryTypes.DESSERT.toString(), products);
                        quantityName =  CategoryTypes.DESSERT.toString().toLowerCase() + quantity;
                        break;
                    } case SOUP_AND_SALAD: {
                        view.addObject(CategoryTypes.SOUP_AND_SALAD.toString(), products);
                        quantityName =  CategoryTypes.SOUP_AND_SALAD.toString().toLowerCase() + quantity;
                        break;
                    } case SIDE_ORDER: {
                        view.addObject(CategoryTypes.SIDE_ORDER.toString(), products);
                        quantityName =  CategoryTypes.SIDE_ORDER.toString().toLowerCase() + quantity;
                        break;
                    } case BENTO_BOX: {
                        view.addObject(CategoryTypes.BENTO_BOX.toString(), products);
                        quantityName =  CategoryTypes.BENTO_BOX.toString().toLowerCase() + quantity;
                        break;
                    } case LUNCH_BENTO_BOX: {
                        view.addObject(CategoryTypes.LUNCH_BENTO_BOX.toString(), products);
                        quantityName =  CategoryTypes.LUNCH_BENTO_BOX.toString().toLowerCase() + quantity;
                        break;
                    } case SASHIMI_NIGIRI_MAKI:{
                        view.addObject(CategoryTypes.SASHIMI_NIGIRI_MAKI.toString(), products);
                        quantityName =  CategoryTypes.SASHIMI_NIGIRI_MAKI.toString().toLowerCase() + quantity;
                        break;
                    } case VEGETABLE_MAKI: {
                        view.addObject(CategoryTypes.VEGETABLE_MAKI.toString(), products);
                        quantityName =  CategoryTypes.VEGETABLE_MAKI.toString().toLowerCase() + quantity;
                        break;
                    } case HAND_ROLL: {
                        view.addObject(CategoryTypes.HAND_ROLL.toString(), products);
                        quantityName =  CategoryTypes.HAND_ROLL.toString().toLowerCase() + quantity;
                        break;
                    } case BEVERAGES: {
                        view.addObject(CategoryTypes.BEVERAGES.toString(), products);
                        quantityName =  CategoryTypes.BEVERAGES.toString().toLowerCase() + quantity;
                        break;
                    } case NIGIRI_A_LA_CARTE: {
                        view.addObject(CategoryTypes.NIGIRI_A_LA_CARTE.toString(), products);
                        quantityName =  CategoryTypes.NIGIRI_A_LA_CARTE.toString().toLowerCase() + quantity;
                        break;
                    } case SASHIMI_A_LA_CARTE: {
                        view.addObject(CategoryTypes.SASHIMI_A_LA_CARTE.toString(), products);
                        quantityName =  CategoryTypes.SASHIMI_A_LA_CARTE.toString().toLowerCase() + quantity;
                        break;
                    } case DINNER_ENTREES: {
                        view.addObject(CategoryTypes.DINNER_ENTREES.toString(), products);
                        quantityName =  CategoryTypes.DINNER_ENTREES.toString().toLowerCase() + quantity;
                        break;
                    } default: {
                        break;
                    }

                }
                    view.addObject(quantityName, 0);
                }


        return view;
    }


    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);

    }

    /**
     *
     * @param product
     * @param result
     * @param imageFile
     * @return
     */
    public ModelAndView createProduct(Product product, BindingResult result, MultipartFile imageFile) {
        ModelAndView view = new ModelAndView();

        if (result.hasErrors()) {
            view.addObject(product);
            view.setViewName("product/manage-product");
        } else if(!imageFile.isEmpty()) {
            Path imagePath;
            try {
                String imageDirectory = product.getCategory().toLowerCase();
                String productName = product.getProductName();
                String fileName = ImagesUtil.changeImageFileName(productName ,imageFile.getOriginalFilename());

                imagePath = ImagesUtil.saveImageInNewDirectory(imageFile.getBytes(), fileName, imageDirectory);
                if (imagePath != null) {
                    product.setProductImagePath(fileName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            productRepository.save(product);
            view.setViewName("redirect:/products/list");
        } else {
            product.setPrice(product.getPrice().setScale(2, RoundingMode.CEILING));
            productRepository.save(product);
            view.setViewName("redirect:/products/list");
        }

        return view;
    }

    public void removeProduct(Long id) {
        productRepository.deleteById(id);
    }

    private String parseImageAbsolutePathToSRC(String imageAbsolutePath) {
        int menuDirectoryIndex = imageAbsolutePath.indexOf("menu" + File.separator );

        return menuDirectoryIndex != -1 ? imageAbsolutePath.substring(menuDirectoryIndex).replaceAll("\\\\", "/") : imageAbsolutePath;
    }


    public BigDecimal calculatePrice(BigDecimal productPrice, int productQuantity) {
        BigDecimal productBigDecimal = new BigDecimal(productQuantity);
        return productPrice.multiply(productBigDecimal);
    }


}
