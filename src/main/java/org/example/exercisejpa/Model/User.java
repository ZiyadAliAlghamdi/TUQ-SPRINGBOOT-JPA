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
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "USER MODEL(username cannot be empty)")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "USER MODEL(username must contains chars only)")
    @Size(min = 5, max = 20, message = "username length must be between 6 and 20")
    @Column(columnDefinition = "varchar(20) not null")
    private String username;


    @NotEmpty(message = "USER MODEL(password cannot be empty)")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d$@$!%*?&#]{6,}$",message = "USER MODEL(password must be secure)")
    @Size(min = 6, max = 20, message = "USER MODEL(password length must be between 6 and 20 chars)")
    @Column(columnDefinition = "varchar(20) not null")
    private String password;


    @NotEmpty(message = "USER MODEL(email cannot be empty)")
    @Email(message = "USER MODEL(please check your email format)")
    @Column(columnDefinition = "varchar(30) not null")
    @Size(max = 30, message = "email size cannot be up to 30 chars")
    private String email;


    @NotEmpty(message = "USER MODEL(role cannot be empty)")
    @Pattern(regexp = "^(Admin|Customer)$", message = "role must be: Admin|Customer")
    @Column(columnDefinition = "varchar(15) not null CHECK(role IN ('Admin','Customer'))")
    @Size(max =15, message = "USER MODEL(role length cannot be more than 15)")
    private String role;

    @NotNull(message = "USER MODEL(balance cannot be null)")
    @PositiveOrZero(message = "USER MODEL(balance cannot be negative)")
    @Column(columnDefinition = "double not null default 0")
    private double balance;

    @NotNull(message = "USER MODEL(loyalty points cannot be null)")
    @PositiveOrZero(message = "USER MODEL(loyalty points cannot be negative)")
    @Column(columnDefinition = "int not null default 0")
    private int loyaltyPoint;
}
