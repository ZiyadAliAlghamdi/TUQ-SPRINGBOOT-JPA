package org.example.exercisejpa.Service;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.exercisejpa.Model.Merchant;
import org.example.exercisejpa.Model.MerchantStock;
import org.example.exercisejpa.Model.Product;
import org.example.exercisejpa.Repository.MerchantRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class MerchantService {

    private final MerchantRepository merchantRepository;

    private final ProductService productService;
    private final MerchantStockService merchantStockService;



    //CRUD

    public List<Merchant> getAllMerchant(){
        return merchantRepository.findAll();
    }

    public Merchant getSingleMerchant(Integer id){
        return merchantRepository.getById(id);
    }


    public void addMerchant(Merchant merchant){
        merchantRepository.save(merchant);
    }


    public Boolean updateMerchant(Integer id, Merchant merchant){
        Merchant oldMerchant = merchantRepository.getById(id);

        if (oldMerchant == null){
            return false;
        }
        //id will be set using hibernate
        oldMerchant.setName(merchant.getName());
        return true;
    }

    public Boolean deleteMerchant(Integer id){
        Merchant merchant = merchantRepository.getById(id);

        if (merchant == null){
            return false;
        }

        merchantRepository.delete(merchant);
        return true;
    }

    //END OF CRUD


    public String generateDiscountCode(){
        String chars = "qwertyuiopasdfghjklzxcvbnm1234567890";
        Random rand = new Random();
        StringBuilder discountCode = new StringBuilder();

        int codeLength = 8;
        for (int i = 0; i < codeLength; i++) {
            int index = rand.nextInt(chars.length());
            discountCode.append(chars.charAt(index));
        }
        return String.valueOf(discountCode);
    }


    /*
    * -1 no product
    * 0 no merchant stock
    * 1 success
    * */
    public Integer assignDiscount(Integer productId, Integer merchantId, int percentage){
        Product product = productService.getSingleProduct(productId);


        if (product == null){
            return -1;
        }


        MerchantStock merchantStock = merchantStockService.getMerchantStockByProductIdAndMerchantId(merchantId, productId);

        if (merchantStock == null){
            return 0;
        }

        product.setDiscountCode(generateDiscountCode());
        product.setDiscountPercentage(percentage);
        productService.updateProduct(productId, product);

        return 1;
    }




}
