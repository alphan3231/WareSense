package com.waresense.backend.service;

import com.waresense.backend.dto.ProductDto;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    public byte[] generateInventoryReport(List<ProductDto> products) {
        try {
            // Compile the Jasper Report Template (We need to create this .jrxml file)
            // For now, we will load it from resources
            InputStream reportStream = getClass().getResourceAsStream("/reports/inventory_report.jrxml");
            
            // If template doesn't exist, we might need to generate one dynamically or throw error.
            // For this MVP, I'll create a simple dynamic one or assume the file exists.
            // Since creating .jrxml by hand is verbose, I will use a simple "Dynamic Report" approach or just assume a simple structure.
            // Let's assume validation passes if I create a basic .jrxml file.
            
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(products);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "WareSense System");

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new RuntimeException("Error generating report", e);
        }
    }
}
