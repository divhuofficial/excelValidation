package com.example.demo.controller;

import com.example.demo.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ExcelController {
    private final ExcelService excelService;

    @Autowired
    public ExcelController(ExcelService excelService) {
        this.excelService = excelService;
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateExcel() {
        String filePath = "C:\\Users\\findp\\Downloads\\data.xlsx"; // Your path
        try {
            excelService.validateExcel(filePath);
            return ResponseEntity.ok("Validation successful");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("File processing error");
        }
    }
}
