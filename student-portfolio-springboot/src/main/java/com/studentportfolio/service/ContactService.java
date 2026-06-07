package com.studentportfolio.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studentportfolio.dao.ContactDAO;
import com.studentportfolio.dto.ContactRequest;
import com.studentportfolio.model.Contact;

@Service
public class ContactService {

    private final ContactDAO contactDAO;

    public ContactService(ContactDAO contactDAO) {
        this.contactDAO = contactDAO;
    }

    @Transactional
    public void saveContact(ContactRequest request) {

        Contact contact = new Contact();

        contact.setName(request.getName());
        contact.setEmail(request.getEmail());
        contact.setSubject(request.getSubject());
        contact.setMessage(request.getMessage());

        boolean saved = contactDAO.saveContact(contact);

        if (!saved) {
            throw new IllegalStateException("Unable to save contact");
        }
    }

    public List<Contact> getAllContacts() {
        return contactDAO.getAllContacts();
    }
}