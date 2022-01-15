package com.ssf.workshop13.services;

import com.ssf.workshop13.model.Contact;
import com.ssf.workshop13.repositories.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactCacheService {
    private final Logger logger = LoggerFactory.getLogger(ContactCacheService.class);

    @Autowired
    private ContactRepository contactRepository;

    public void save(String id, List<Contact> contact) {

    }
}
