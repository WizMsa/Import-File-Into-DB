package com.mnesa.excel.service;

import com.mnesa.excel.Model.Product;
import com.mnesa.excel.Model.Serials;
import com.mnesa.excel.helpers.CSVHelper;
import com.mnesa.excel.helpers.ExcelHelper;
import com.mnesa.excel.repository.ExcelRepository;
import com.mnesa.excel.repository.SerialRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExcelService {
    private final ExcelRepository excelRepository;
    private final SerialRepository serialRepository;


    @Transactional
    public HttpStatus save(MultipartFile file) {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        if (ExcelHelper.fileFormat(file)) {
            try {
                List<Product> products = ExcelHelper.savingExcelToDb(file.getInputStream());
                log.info("List : {}", products);
                excelRepository.saveAll(Objects.requireNonNull(products));
                httpStatus = HttpStatus.CREATED;
            } catch (Exception e) {
                log.error("Failed To upload : {}", e.getMessage());
                e.getStackTrace();
                httpStatus = HttpStatus.BAD_REQUEST;
            }
        } else if (CSVHelper.checkFormat(file)) {
            try {
                List<Serials> products = CSVHelper.CSVToProducts(file.getInputStream());
                log.info("List : {}", products);
                for (Serials serials : products) {
                    log.info("Creating serial : {}", serials);
                }
                serialRepository.saveAll(products);
                httpStatus = HttpStatus.CREATED;
            } catch (Exception e) {
                e.printStackTrace();
                log.error("CSV Uploading : {} : {}",e.getCause(), e.getMessage());
            }
        }
        return httpStatus;
    }

    public HttpStatus uploadCSV(MultipartFile file) throws IOException {
        if (CSVHelper.checkFormat(file)) {
            List<Serials> products = CSVHelper.CSVToProducts(file.getInputStream());
            log.info("List : {}", products);
            serialRepository.saveAll(products);
            return HttpStatus.CREATED;
        }
        return HttpStatus.BAD_REQUEST;
    }

    public List<Product> getAll() {
        return excelRepository.findAll();
    }

}
