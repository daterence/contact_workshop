package com.ssf.workshop13.controller;

import com.ssf.workshop13.model.Contact;
import com.ssf.workshop13.util.Contacts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/contact", produces = MediaType.TEXT_HTML_VALUE)
public class ContactController {

    private Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ApplicationArguments applicationArguments;

    // submit & save contact information
    @PostMapping
    public String saveContact(@ModelAttribute Contact contact, Model model) {
        logger.info("ID >> " + contact.getId());
        logger.info("Name >> " + contact.getName());
        logger.info("Email >> " + contact.getEmail());
        logger.info("Phone Number >> " + contact.getPhoneNumber());
        Contacts contacts = new Contacts();
        contacts.saveContact(contact, model, applicationArguments);
        return "contact";
    }

    // search for contact information using ID
    @GetMapping
    public String searchContact(@ModelAttribute Contact contact, Model model) {
        logger.info("ID >> " + contact.getId());
        Contacts contacts = new Contacts();
        contacts.getContactById(contact.getId(), model, applicationArguments);

        return "contact";
    }

}
