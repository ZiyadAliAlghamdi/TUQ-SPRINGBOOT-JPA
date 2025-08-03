package org.example.exercisejpa.Repository;

import org.example.exercisejpa.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
