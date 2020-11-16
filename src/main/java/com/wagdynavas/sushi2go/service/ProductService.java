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
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;



    public ModelAndView getAllProductsSortByCategory() {
        List<CategoryTypes> categories  = Arrays.asList(CategoryTypes.values());
        ModelAndView view = new ModelAndView();
        view.setViewName("product/list-products");

            for (CategoryTypes categoryType : categories) {
                List<Product> products = productRepository.findAllByCategory(categoryType.toString());
                    if (categoryType.equals(CategoryTypes.LUNCH_SUSHI)) {
                        view.addObject(CategoryTypes.LUNCH_SUSHI.toString(), products);
                    } else if (categoryType.equals(CategoryTypes.LUNCH_ENTREES)) {
                        view.addObject(CategoryTypes.LUNCH_ENTREES.toString(), products);
                    } else if (categoryType.equals(CategoryTypes.LUNCH_NOODLES)) {
                        view.addObject(CategoryTypes.LUNCH_NOODLES.toString(), products);
                    } else if (categoryType.equals(CategoryTypes.DINNER)) {
                        view.addObject(CategoryTypes.DINNER.toString(), products);
                    } else if (categoryType.equals(CategoryTypes.FUNKY_ROLLS)) {
                        view.addObject(CategoryTypes.FUNKY_ROLLS.toString(), products);
                    } else if (categoryType.equals(CategoryTypes.NIGIRI_SUSHI)) {
                        view.addObject(CategoryTypes.NIGIRI_SUSHI.toString(), products);
                    } else if (categoryType.equals(CategoryTypes.MAKI_SUSHI)) {
                        view.addObject(CategoryTypes.MAKI_SUSHI.toString(), products);
                    } else if (categoryType.equals(CategoryTypes.NIGIRI_AND_MAKI_SUSHI)) {
                        view.addObject(CategoryTypes.NIGIRI_AND_MAKI_SUSHI.toString(), products);
                    } else if (categoryType.equals(CategoryTypes.SASHIMI)) {
                        view.addObject(CategoryTypes.SASHIMI.toString(), products);
                    } else if (categoryType.equals(CategoryTypes.APPETIZER)) {
                        view.addObject(CategoryTypes.APPETIZER.toString(), products);
                    } else if (categoryType.equals(CategoryTypes.DESSERT)) {
                        view.addObject(CategoryTypes.DESSERT.toString(), products);
                    } else if (categoryType.equals(CategoryTypes.SOUP_AND_SALAD)) {
                        view.addObject(CategoryTypes.SOUP_AND_SALAD.toString(), products);
                    } else if (categoryType.equals(CategoryTypes.SIDE_ORDER)) {
                        view.addObject(CategoryTypes.SIDE_ORDER.toString(), products);
                    } else if (categoryType.equals(CategoryTypes.BENTO_BOX)) {
                        view.addObject(CategoryTypes.BENTO_BOX.toString(), products);
                    } else if (categoryType.equals(CategoryTypes.LUNCH_BENTO_BOX)) {
                        view.addObject(CategoryTypes.LUNCH_BENTO_BOX.toString(), products);
                    } else if (categoryType.equals(CategoryTypes.SASHIMI_NIGIRI_MAKI)) {
                        view.addObject(CategoryTypes.SASHIMI_NIGIRI_MAKI.toString(), products);
                    } else if (categoryType.equals(CategoryTypes.VEGETABLE_MAKI)) {
                        view.addObject(CategoryTypes.VEGETABLE_MAKI.toString(), products);
                    }
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
        //TODO User ImageFile para armazanar o path na base de dados e tambem salvar a imagem no sistema
        ModelAndView view = new ModelAndView();
        if (result.hasErrors()) {
            view.addObject(product);
            view.setViewName("product/manage-product");
        } else {
            Path imagePath = null;
            try {
                String imageDirectory = product.getCategory().toLowerCase();
                String productName = product.getProductName();
                String fileName = ImagesUtil.changeImageFileName(productName ,imageFile.getOriginalFilename());

                imagePath = ImagesUtil.saveImageInNewDirectory(imageFile.getBytes(), fileName, imageDirectory);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String productImagePath = parseImageAbsolutePathToSRC(imagePath.toString());
            product.setProductImagePath(productImagePath);
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


}
