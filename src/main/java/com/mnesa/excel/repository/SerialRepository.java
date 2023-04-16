package com.mnesa.excel.repository;

import com.mnesa.excel.Model.Serials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SerialRepository extends JpaRepository<Serials,Long> {
}
