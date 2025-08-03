package org.example.exercisejpa.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.exercisejpa.ApiResponse.ApiResponse;
import org.example.exercisejpa.Model.Category;
import org.example.exercisejpa.Service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping("/get")
    public ResponseEntity<?> getAllCategory(){
        if (categoryService.getAllCategory().isEmpty()){
            return ResponseEntity.status(404).body(new ApiResponse("Categories list is empty"));
        }
        return ResponseEntity.ok(categoryService.getAllCategory());
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<?>getSingleCategory(@PathVariable Integer id){
        if (categoryService.getSingleCategory(id) ==  null){
            return ResponseEntity.status(404).body(new ApiResponse("category: "+id+" not found"));
        }
        return ResponseEntity.ok(categoryService.getSingleCategory(id));
    }

    @PostMapping("/post")
    public ResponseEntity<?> addCategory(@Valid @RequestBody Category category, Errors errors){


        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        categoryService.addCategory(category);
        return ResponseEntity.ok(new ApiResponse("category added successfully"));
    }


    @PutMapping("/put/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Integer id, @Valid @RequestBody Category category, Errors errors){

        //validate the category object
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }


        Boolean isUpdated = categoryService.updateCategory(id, category);

        //checking the category id
        if (!isUpdated){
            return ResponseEntity.status(404).body(new ApiResponse("category: "+id+" not found"));
        }

        return ResponseEntity.ok(new ApiResponse("category: "+ id +" have been updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id){
        if (!categoryService.deleteCategory(id)){
            return ResponseEntity.status(404).body(new ApiResponse("category: "+id+" not found"));
        }

        categoryService.deleteCategory(id);
        return ResponseEntity.ok(new ApiResponse("category: "+ id +" have been deleted successfully"));
    }

}

