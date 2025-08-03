package org.example.exercisejpa.Service;


import lombok.AllArgsConstructor;
import org.example.exercisejpa.Model.Product;
import org.example.exercisejpa.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


//    //dependency injection
//    private final CategoryService categoryService;    //useless for now


    //CRUD
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getSingleProduct(Integer id){
        return productRepository.getById(id);
    }


    public void addProduct(Product product) {
        productRepository.save(product);
    }



    public boolean updateProduct(Integer id, Product product){
        Product oldProduct = productRepository.getById(id);

        if (oldProduct == null){
            return false;
        }

        oldProduct.setName(product.getName());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setCategoryId(product.getCategoryId());
        oldProduct.setDiscountCode(product.getDiscountCode());
        oldProduct.setDiscountPercentage(product.getDiscountPercentage());
        oldProduct.setNumberOfBuyers(product.getNumberOfBuyers());
        productRepository.save(oldProduct);
        return true;
    }

    public boolean deleteProduct(Integer id){
        Product product = productRepository.getById(id);

        if (product == null){
            return false;
        }
        productRepository.delete(product);
        return true;
    }

    public Product getById(Integer id){
        return productRepository.getById(id);
    }

    //END OF CRUD


    //Get single Product By Region//

    //assuming default price in USD like amazon
    public Product getProductByRegion(Integer id,String region){
        Product originalProduct = getSingleProduct(id);


        //creating an inner object to make sure we don't conflict with the original one
        Product innerProduct = new Product(
                originalProduct.getId(),
                originalProduct.getName(),
                originalProduct.getPrice(),
                originalProduct.getCategoryId(),
                null,    //make discount code null as default
                0,
                originalProduct.getNumberOfBuyers()
        );

        double originalPrice = originalProduct.getPrice();


        if (region == null || region.isEmpty()){
            originalProduct.setPrice(originalProduct.getPrice());
            return originalProduct;
        }

        switch (region){
            case "SA":  //dollar to saudi riyal
                innerProduct.setPrice(originalPrice *3.75);
                break;
            case "JP":
                innerProduct.setPrice(originalPrice*147.85);
                break;
            case "UK":
                innerProduct.setPrice(originalPrice*0.74);
                break;
            default:
                innerProduct.setPrice(originalPrice);
        }
        return innerProduct;
    }

    //Get All Products By Region//

    //assuming default price in USD like amazon
    public List<Product> getAllProductsByRegion(String region){
        List<Product> allProducts = productRepository.findAll();
        List<Product> regionProducts = new ArrayList<>();

        for (Product p : allProducts) {
            Product converted = new Product(
                    p.getId(),
                    p.getName(),
                    p.getPrice(),
                    p.getCategoryId(),
                    p.getDiscountCode(),
                    p.getDiscountPercentage(),
                    p.getNumberOfBuyers()
            );


            double basePrice = p.getPrice();

            switch (region) {
                case "SA":
                    converted.setPrice(basePrice * 3.75);
                    break;
                case "JP":
                    converted.setPrice(basePrice * 147.85);
                    break;
                case "UK":
                    converted.setPrice(basePrice * 0.74);
                    break;
                default:
                    converted.setPrice(basePrice);
                    break;
            }
            regionProducts.add(converted);
        }

        return regionProducts;
    }

    public List<Product> getTop10SoldItems() {
        return productRepository.findAll().stream()
                .sorted((p1,p2) -> Integer.compare(p2.getNumberOfBuyers(), p1.getNumberOfBuyers()))
                .limit(10)
                .collect(Collectors.toCollection(ArrayList::new));
    }


}
