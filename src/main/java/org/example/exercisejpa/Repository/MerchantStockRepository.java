package org.example.exercisejpa.Repository;

import org.example.exercisejpa.Model.MerchantStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantStockRepository extends JpaRepository<MerchantStock,Integer> {
}
