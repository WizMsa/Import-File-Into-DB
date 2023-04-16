package com.mnesa.excel.repository;

import com.mnesa.excel.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExcelRepository extends JpaRepository<Product,Long> {
}
