package com.mnesa.excel.controller;

import com.mnesa.excel.service.ExcelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/excel")
@Controller
@Slf4j
public class ExcelController {
    private  final ExcelService service;
    @PostMapping("/uploadXLS")
    public ResponseEntity<?> save(MultipartFile file){
        log.info("Request From Client : {}", file.getOriginalFilename());
        return new ResponseEntity<>(service.save(file));
    }
    @GetMapping("view-all")
    public ResponseEntity<?> show(){
        return new ResponseEntity<>(service.getAll(),HttpStatus.FOUND);
    }

    @PostMapping("/uploadCSV")
    public ResponseEntity<?> upload(MultipartFile file) throws IOException {
        log.info("Request From Client CSV: {}", file.getInputStream().available());
        return new ResponseEntity<>(service.uploadCSV(file));
    }
}
