package org.example.exercisejpa.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Merchant {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "MERCHANT MODEL(NAME cannot be empty)")
    @Size(min = 3,max = 30, message = "MERCHANT MODEL(NAME length must be between 3 and 30 char length)")
    @Column(columnDefinition = "varchar(30) not null")
    private String name;
}

