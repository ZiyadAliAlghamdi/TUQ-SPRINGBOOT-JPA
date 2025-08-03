package org.example.exercisejpa.Controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.exercisejpa.ApiResponse.ApiResponse;
import org.example.exercisejpa.Model.User;
import org.example.exercisejpa.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/get")
    public ResponseEntity<?> getAllUsers(){
        if (userService.getAllUsers().isEmpty()){
            return ResponseEntity.status(404).body(new ApiResponse("no users found"));
        }
        return ResponseEntity.status(200).body(userService.getAllUsers());
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<?>getSingleUser(@PathVariable Integer id){
        //todo: handling exceptions
        return ResponseEntity.status(200).body(userService.getSingleUser(id));
    }


    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody @Valid User user, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("user("+user.getId()+") added successfully"));
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @Valid @RequestBody User user, Errors errors){

        //validate the user object
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        userService.updateUser(id, user);
        return ResponseEntity.ok(new ApiResponse("user("+ user.getId()+")have been updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id){
        if (!userService.deleteUser(id)){
            return ResponseEntity.status(404).body(new ApiResponse("user("+id+")not found"));
        }

        userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponse("user: "+ id +" have been deleted successfully"));
    }



    @PutMapping("/buy")
    public ResponseEntity<?> buyProduct(@RequestParam Integer userId, @RequestParam Integer productId, @RequestParam Integer merchantId) {
        String result = userService.buyItem(userId, productId, merchantId);
        if (result.equalsIgnoreCase("Purchase successful"))
            return ResponseEntity.status(200).body(new ApiResponse(result));
        else
            return ResponseEntity.badRequest().body(new ApiResponse(result));
    }


    @PutMapping("/applyDiscount")
    public ResponseEntity<?> applyDiscount(@RequestParam Integer productId, @RequestParam String promoCode){
        boolean applyStatus = userService.applyDiscount(productId, promoCode);

        if (!applyStatus){
            return ResponseEntity.status(400).body(new ApiResponse("error in discount applying"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("discount applied"));
    }

    //loyalty points
    @PutMapping("/applyLoyaltyDiscount")
    public ResponseEntity<?> applyLoyaltyDiscount(@RequestParam Integer userId, @RequestParam Integer productId) {
        String result = userService.applyLoyaltyDiscount(userId, productId);
        if (result.equalsIgnoreCase("Loyalty discount applied successfully")) {
            return ResponseEntity.ok(new ApiResponse(result));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse(result));
        }
    }

}
