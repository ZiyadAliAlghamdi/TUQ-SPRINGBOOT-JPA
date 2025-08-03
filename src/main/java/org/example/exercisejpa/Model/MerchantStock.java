package org.example.exercisejpa.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MerchantStock {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "MerchantStock MODEL(productID cannot be empty)")
    @Column(columnDefinition = "int not null")
    private Integer productID;


    @NotNull(message = "MerchantStock MODEL(merchantID cannot be empty)")
    @Column(columnDefinition = "int not null")
    private Integer merchantID;

    @NotNull(message = "stock cannot be empty")
    @Min(value = 10, message = "stock have to be 10 or more")
    @Column(columnDefinition = "int not null CHECK(stock >= 10)")
    private int stock;
}
