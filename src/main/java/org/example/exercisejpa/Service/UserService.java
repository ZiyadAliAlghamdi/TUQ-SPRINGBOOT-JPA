package org.example.exercisejpa.Service;

import lombok.AllArgsConstructor;
import org.example.exercisejpa.Model.MerchantStock;
import org.example.exercisejpa.Model.Product;
import org.example.exercisejpa.Model.User;
import org.example.exercisejpa.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final ProductService productService;
    private final MerchantStockService merchantStockService;


    private final UserRepository userRepository;


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getSingleUser(Integer id){
        return userRepository.getById(id);
    }

    public void addUser(User user){
        userRepository.save(user);
    }

    public Boolean updateUser(Integer id, User user){
        User oldUser = userRepository.getById(id);

        if (oldUser == null){
            return false;
        }

        oldUser.setUsername(user.getUsername());
        oldUser.setPassword(user.getPassword());
        oldUser.setEmail(user.getEmail());
        oldUser.setRole(user.getRole());
        oldUser.setBalance(user.getBalance());
        oldUser.setLoyaltyPoint(user.getLoyaltyPoint());

        userRepository.save(oldUser);
        return true;
    }

    public Boolean deleteUser(Integer id){
        User oldUser = userRepository.getById(id);
        if (oldUser == null){
            return false;
        }

        userRepository.delete(oldUser);
        return true;
    }


    //todo buyItem and extraEndPoints

    public String buyItem(Integer userId, Integer productId, Integer merchantId){

        User user = userRepository.getById(userId);

        Product product = productService.getById(productId);

        MerchantStock stock = merchantStockService.findByProductAndMerchant(productId,merchantId);

        //checking id's
        if (user == null){
            return "USER ID invalid";
        }

        if (product == null){
            return "product ID invalid";
        }

        if (stock == null){
            return "stock ID invalid";
        }


        //checking stock
        if (stock.getStock() <= 0){
            return "out of stock";
        }

        //checking balance
        if (user.getBalance() < product.getPrice()){
            return "balance not enough";
        }

        //changing the stock
        stock.setStock(stock.getStock() - 1);

        //changing the balance
        user.setBalance(user.getBalance() - product.getPrice());

        //updating the number of buyers
        product.setNumberOfBuyers(product.getNumberOfBuyers() + 1);

        //add loyaltyPoints
        user.setLoyaltyPoint(user.getLoyaltyPoint()+1);

        return "Purchase successful";
    }

    //apply discount
    public boolean applyDiscount(Integer productId, String promoCode){
        Product product = productService.getSingleProduct(productId);
        int discount = (int) (((double) product.getDiscountPercentage() /100)*product.getPrice());

        if (product.getDiscountCode() != null && product.getDiscountCode().equalsIgnoreCase(promoCode)){
            product.setPrice(product.getPrice()-discount);
            return true;
        }
        return false;
    }

    public String applyLoyaltyDiscount(Integer userId, Integer productId) {
        User user = getSingleUser(userId);
        Product product = productService.getById(productId);

        if (user == null) {
            return "User not found";
        }
        if (product == null) {
            return "Product not found";
        }

        int loyaltyPoints = user.getLoyaltyPoint();
        if (loyaltyPoints < 10) {
            return "Not enough loyalty points to apply discount";
        }

        //10 points = 10 units discount
        double discount = loyaltyPoints;
        double discountedPrice = product.getPrice() - discount;
        if (discountedPrice < 0) {
            discountedPrice = 0;
        }


        user.setLoyaltyPoint(0);


        return "Loyalty discount applied successfully. Final price: " + discountedPrice;
    }
}
