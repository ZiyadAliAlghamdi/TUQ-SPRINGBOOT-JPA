package org.example.exercisejpa.Repository;

import org.example.exercisejpa.Model.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant,Integer> {
}
