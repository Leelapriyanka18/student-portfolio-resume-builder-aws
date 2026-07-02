package com.studentportfolio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studentportfolio.dto.ContactRequest;
import com.studentportfolio.model.Contact;
import com.studentportfolio.security.AuthenticatedUserService;
import com.studentportfolio.service.ContactService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final ContactService contactService;
    private final AuthenticatedUserService authenticatedUserService;

    public ContactController(ContactService contactService, AuthenticatedUserService authenticatedUserService) {
        this.contactService = contactService;
        this.authenticatedUserService = authenticatedUserService;
    }

    @PostMapping
    public ResponseEntity<String> saveContact(
            @Valid @RequestBody ContactRequest request) {

        request.setUserId(authenticatedUserService.getAuthenticatedUserId());
        contactService.saveContact(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Message Sent Successfully");
    }

    @GetMapping
    public ResponseEntity<List<Contact>> getAllContacts(
            @RequestParam(required = false) Integer userId) {
        return ResponseEntity.ok(contactService.getContactsByUserId(
                authenticatedUserService.getAuthenticatedUserId()));
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class
    })
    public ResponseEntity<String> handleBadRequest(Exception ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
