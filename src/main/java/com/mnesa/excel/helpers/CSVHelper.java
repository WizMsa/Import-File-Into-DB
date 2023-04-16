package com.mnesa.excel.helpers;

import com.mnesa.excel.Model.Serials;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class CSVHelper {
    public static final String TYPE = "text/csv";
    static String[] HEADERs = {"SERIAL_NO", "BATCH", "VERIFIED_VIA", "ORGANIZATION_ID","VERIFIED","ACTIVATED"};


    public static List<Serials> CSVToProducts(InputStream in){
        List<Serials> serials = new ArrayList<>();
        try(BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            CsvParserSettings settings = new CsvParserSettings();
            settings.setHeaderExtractionEnabled(true);
            CsvParser parser = new CsvParser(settings);
            List<Record> csvRecord = parser.parseAllRecords(in);

            log.info("List : {}", csvRecord);
            int size = csvRecord.size();
            csvRecord.forEach(
                    record->{
                        Serials serial = new Serials();
                        serial.setSerials(record.getString("SERIAL_NO"));
                        serial.setBatch(record.getString("BATCH"));
                        serial.setVerifiedVia(Long.parseLong(record.getString("VERIFIED_VIA")));
                        serial.setOrganizationId(Long.parseLong(record.getString("ORGANIZATION_ID")));
                        serial.setVerified(Boolean.parseBoolean(record.getString("VERIFIED")));
                        serial.setActivated(Boolean.parseBoolean(record.getString("ACTIVATED")));
                        serial.setManual(Boolean.parseBoolean(record.getString("IS_MANUAL")));
                        serials.add(serial);
                    }
            );
            log.info("Serial Numbers : {}", serials);
            return serials;
        } catch (IOException e) {
            e.printStackTrace();
            log.info("Failed To Upload : {} ", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static boolean checkFormat(MultipartFile file){
        return Objects.equals(file.getContentType(),TYPE);
    }
}
