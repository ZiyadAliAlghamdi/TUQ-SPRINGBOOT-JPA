package org.example.exercisejpa.Service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.exercisejpa.Model.MerchantStock;
import org.example.exercisejpa.Repository.MerchantStockRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MerchantStockService {

    //virtual database for merchantStocks's
    private final MerchantStockRepository merchantStockRepository;

    //dependency injection
    private final ProductService productService;

    //CRUD

    public List<MerchantStock> getAllMerchantStock(){
        return merchantStockRepository.findAll();
    }

    public MerchantStock getSingleMerchantStock(Integer id){
        return merchantStockRepository.getById(id);
    }


    public int addMerchantStock(MerchantStock merchantStock) {

        //check product exists
        if (productService.getSingleProduct(merchantStock.getProductID()) == null) {
            return 0; // product not found
        }


        merchantStockRepository.save(merchantStock);
        return 1; // success
    }



    public Boolean updateMerchantStock(Integer id, MerchantStock merchantStock){
    MerchantStock oldMerchantStock = merchantStockRepository.getById(id);

    if (oldMerchantStock == null){
        return false;
    }

    oldMerchantStock.setProductID(merchantStock.getProductID());
    oldMerchantStock.setMerchantID(merchantStock.getMerchantID());
    oldMerchantStock.setStock(merchantStock.getStock());
    return true;
    }

    public Boolean deleteMerchantStock(Integer id){
        MerchantStock merchantStock = merchantStockRepository.getById(id);
        if (merchantStock == null){
            return false;
        }
        merchantStockRepository.delete(merchantStock);
        return true;
    }

    //END OF CRUD

    public MerchantStock findByProductAndMerchant(Integer productId, Integer merchantId) {
        return merchantStockRepository.findAll().stream()
                .filter(ms -> Objects.equals(ms.getProductID(), productId) && Objects.equals(ms.getMerchantID(), merchantId))
                .findFirst()
                .orElse(null);
    }

    public boolean addStock(Integer productId, Integer merchantId, int amount) {
        MerchantStock stock = findByProductAndMerchant(productId, merchantId);

        if (stock != null) {
            stock.setStock(stock.getStock() + amount);
            return true;
        }

        return false;
    }


    public MerchantStock getMerchantStockByProductIdAndMerchantId(Integer merchantId, Integer productId){
        return merchantStockRepository.findAll().stream()
                .filter(ms -> Objects.equals(ms.getMerchantID(), merchantId) && Objects.equals(ms.getProductID(), productId))
                .findFirst()
                .orElse(null);
    }

}
