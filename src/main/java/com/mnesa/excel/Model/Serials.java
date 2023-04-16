package com.mnesa.excel.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Serials {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @DateTimeFormat(pattern = "dd-mm-yyyy HH:mm:ss:SSSS")
    private Date createdAt = new Date();
    private String serials;
    private String Batch;
    private long verifiedVia;
    private long organizationId;
    private boolean verified;
    private boolean activated;
    private boolean manual;
}
