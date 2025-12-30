package com.waresense.backend.controller;

import com.waresense.backend.service.QrCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/qr")
@RequiredArgsConstructor
public class QrCodeController {

    private final QrCodeService qrCodeService;

    @GetMapping(value = "/product/{sku}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getProductQr(@PathVariable String sku) {
        // Embed SKU into QR code. In real app, could be a URL: https://waresense.com/product/{sku}
        byte[] qrImage = qrCodeService.generateQrCode("PRODUCT:" + sku, 300, 300);
        return ResponseEntity.ok(qrImage);
    }

    @GetMapping(value = "/shelf/{code}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getShelfQr(@PathVariable String code) {
        byte[] qrImage = qrCodeService.generateQrCode("SHELF:" + code, 300, 300);
        return ResponseEntity.ok(qrImage);
    }
}
