package org.example.exercisejpa.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "PRODUCT MODEL(NAME cannot be empty)")
    @Size(min= 6,max = 40, message = "PRODUCT MODEL(name cannot be less than 6 chars)")
    @Column(columnDefinition = "varchar(40) not null")
    private String name;

    @NotNull(message = "PRODUCT MODEL(PRICE cannot be null)")
    @PositiveOrZero(message = "PRODUCT MODEL(PRICE cannot be negative)")    //could be an item with offer(free)
    @Column(columnDefinition = "double not null default 0")
    private double price;

    @NotNull(message = "PRODUCT MODEL(categoryId cannot be null)")
    @Column(columnDefinition = "int not null")  //will be replaced by foreign key
    private Integer categoryId;


    @Column(columnDefinition = "varchar(20) DEFAULT null")
    private String discountCode;

    @NotNull(message = "PRODUCT MODEL(discountPercentage cannot be null, you can insert 0 if there is no percentage discount)")
    @Column(columnDefinition = "int not null DEFAULT 0")
    private int discountPercentage;


    @NotNull(message = "PRODUCT MODEL(number of buyers cannot be null)")
    @Column(columnDefinition = "int not null default 0")
    private int numberOfBuyers;
}
