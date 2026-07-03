package com.studentportfolio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.studentportfolio.dto.CertificateRequest;
import com.studentportfolio.model.Certificate;
import com.studentportfolio.security.AuthenticatedUserService;
import com.studentportfolio.service.CertificateService;

import jakarta.validation.Valid;

@RestController
@RequestMapping({"/api/certificates", "/api/certificate"})
public class CertificateController {

    private final CertificateService certificateService;
    private final AuthenticatedUserService authenticatedUserService;

    public CertificateController(
            CertificateService certificateService,
            AuthenticatedUserService authenticatedUserService) {
        this.certificateService = certificateService;
        this.authenticatedUserService = authenticatedUserService;
    }

    @PostMapping
    public ResponseEntity<String> saveCertificate(
            @Valid @RequestBody CertificateRequest request) {

        request.setUserId(authenticatedUserService.getAuthenticatedUserId());
        certificateService.saveCertificate(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Certificate Saved Successfully");
    }

    @GetMapping
    public ResponseEntity<List<Certificate>> getCertificates() {
        return ResponseEntity.ok(certificateService.getCertificatesByUserId(
                authenticatedUserService.getAuthenticatedUserId()));
    }
}
