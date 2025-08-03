package org.example.exercisejpa.Controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.exercisejpa.ApiResponse.ApiResponse;
import org.example.exercisejpa.Model.Merchant;
import org.example.exercisejpa.Service.MerchantService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;


    @GetMapping("/get")
    public ResponseEntity<?> getAllMerchants() {
        if (merchantService.getAllMerchant().isEmpty()) {
            return ResponseEntity.status(404).body(new ApiResponse("Merchants list is empty"));
        }
        return ResponseEntity.ok(merchantService.getAllMerchant());
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<?> getSingleMerchant(@PathVariable Integer id) {
        if (merchantService.getSingleMerchant(id) == null) {
            return ResponseEntity.status(404).body(new ApiResponse("merchant: " + id + " not found"));
        }
        return ResponseEntity.ok(merchantService.getSingleMerchant(id));
    }

    @PostMapping("/post")
    public ResponseEntity<?> addMerchant(@Valid @RequestBody Merchant merchant, Errors errors) {


        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        merchantService.addMerchant(merchant);

        return ResponseEntity.ok(new ApiResponse("merchant added successfully"));
    }


    @PutMapping("/put/{id}")
    public ResponseEntity<?> updateMerchant(@PathVariable Integer id, @Valid @RequestBody Merchant merchant, Errors errors) {

        //checking the merchant id
        if (!merchantService.updateMerchant(id, merchant)) {
            return ResponseEntity.status(404).body(new ApiResponse("merchant: " + id + " not found"));
        }


        //validate the merchant object
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }

        merchantService.updateMerchant(id, merchant);
        return ResponseEntity.ok(new ApiResponse("merchant: " + id + " have been updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchant(@PathVariable Integer id) {
        if (!merchantService.deleteMerchant(id)) {
            return ResponseEntity.status(404).body(new ApiResponse("merchant: " + id + " not found"));
        }

        merchantService.deleteMerchant(id);
        return ResponseEntity.ok(new ApiResponse("merchant: " + id + " have been deleted successfully"));
    }

    @PutMapping("/assignDiscount/{productId}/{merchantId}")
    public ResponseEntity<?> assignDiscount(@PathVariable Integer productId,@PathVariable Integer merchantId, int percentage) {
        int discountAssignResult = merchantService.assignDiscount(productId,merchantId, percentage);

        return switch (discountAssignResult) {
            case -1 -> ResponseEntity.status(404).body(new ApiResponse("no product found"));
            case 0 -> ResponseEntity.status(404).body(new ApiResponse("no merchant stock found"));
            case 1 -> ResponseEntity.ok(new ApiResponse("discount added successfully"));
            default -> ResponseEntity.status(500).body(new ApiResponse("an error occurred"));
        };
    }




}