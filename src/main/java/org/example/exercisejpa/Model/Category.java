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
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "CATEGORY MODEL(NAME cannot be empty)")
    @Size(min = 3, message = "CATEGORY MODEL(NAME must be between 3 and 30 length)")
    @Column(columnDefinition = "varchar(20) not null")
    private String name;
}
