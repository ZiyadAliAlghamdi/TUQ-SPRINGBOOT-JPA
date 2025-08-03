package org.example.exercisejpa.Repository;

import org.example.exercisejpa.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
