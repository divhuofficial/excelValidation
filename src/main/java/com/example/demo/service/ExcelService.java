package com.example.demo.service;

import com.example.demo.exception.ValidationException;
import com.example.demo.model.Error;
import com.example.demo.model.User;
import com.example.demo.repository.ErrorRepository;
import com.example.demo.repository.UserRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelService {

    private final UserRepository userRepository;
    private final ErrorRepository errorRepository;

    @Autowired
    public ExcelService(UserRepository userRepository, ErrorRepository errorRepository) {
        this.userRepository = userRepository;
        this.errorRepository = errorRepository;
    }

    public List<User> validateExcel(String filePath) throws IOException {
        List<User> users = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {
            Sheet sheet = workbook.getSheet("Sheet1");
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header
                User user = new User();
                try {
                    if (row.getCell(0) == null || row.getCell(1) == null || row.getCell(2) == null) {
                        throw new ValidationException(101, "Row " + (row.getRowNum() + 1) + " is missing required data.");
                    }
                    user.setSerialNumber((int) row.getCell(0).getNumericCellValue());
                    user.setId((int) row.getCell(1).getNumericCellValue());
                    user.setName(row.getCell(2).getStringCellValue());
                    users.add(user);
                    userRepository.save(user);
                } catch (ValidationException e) {
                    // Save error to the database
                    errorRepository.save(new Error(String.valueOf(e.getErrorCode()), e.getErrorDescription()));
                }
            }
        }
        return users;
    }
}
