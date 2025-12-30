package com.waresense.backend.controller;

import com.waresense.backend.service.ProductService;
import com.waresense.backend.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private final ProductService productService;

    @GetMapping("/inventory")
    public ResponseEntity<byte[]> downloadInventoryReport() {
        byte[] pdfReport = reportService.generateInventoryReport(productService.getAllProducts());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=inventory_report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfReport);
    }
}
