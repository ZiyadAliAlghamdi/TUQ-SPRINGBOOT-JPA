package org.example.exercisejpa.Repository;

import org.example.exercisejpa.Model.MerchantStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantStockRepository extends JpaRepository<MerchantStock,Integer> {
}
