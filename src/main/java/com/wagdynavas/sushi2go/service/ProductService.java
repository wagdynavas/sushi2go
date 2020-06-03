package com.wagdynavas.sushi2go.service;

import com.wagdynavas.sushi2go.model.Product;
import com.wagdynavas.sushi2go.repository.ProductRepository;
import com.wagdynavas.sushi2go.util.type.CategoryTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;


@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ModelAndView getAllProductsSortByCategory() {
        List<CategoryTypes> categories  = Arrays.asList(CategoryTypes.values());
        ModelAndView view = new ModelAndView();
        view.setViewName("product/all-products");

            for (CategoryTypes categoryType : categories) {
                List<Product> products = productRepository.findAllByCategory(categoryType.toString());
                if (!CollectionUtils.isEmpty(products)) {
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
                    }
                }
            }


        return view;
    }
}
