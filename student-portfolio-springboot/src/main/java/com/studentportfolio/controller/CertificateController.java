package com.studentportfolio.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentportfolio.dto.CertificateRequest;
import com.studentportfolio.service.CertificateService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/certificate")
public class CertificateController {

    private final CertificateService certificateService;

    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @PostMapping
    public ResponseEntity<String> saveCertificate(
            @Valid @RequestBody CertificateRequest request) {

        certificateService.saveCertificate(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Certificate Saved Successfully");
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class
    })
    public ResponseEntity<String> handleBadRequest(Exception ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}