package com.ssf.workshop13.controller;

import com.ssf.workshop13.model.Contact;
import com.ssf.workshop13.repositories.ContactRepository;
import com.ssf.workshop13.services.ContactCacheService;
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

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/contact", produces = MediaType.TEXT_HTML_VALUE)
public class ContactController {

    private final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ApplicationArguments applicationArguments;

    @Autowired
    private ContactCacheService contactCacheService;

    @Autowired
    private ContactRepository contactRepository;

    // submit & save contact information
    @PostMapping
    public String saveContact(@ModelAttribute Contact contact, Model model) {
        logger.info("ID >> " + contact.getId());
        logger.info("Name >> " + contact.getName());
        logger.info("Email >> " + contact.getEmail());
        logger.info("Phone Number >> " + contact.getPhoneNumber());

//        create file in the specified directory and save contact info
//        Contacts contacts = new Contacts();
//        contacts.saveContact(contact, model, applicationArguments);

        // Save contact info to redis repo
        contactCacheService.save(contact.getId(), contact);
        model.addAttribute("contact", contact);
        model.addAttribute("id", contact.getId());
        return "contact";
    }

    // search for contact information using ID
    @GetMapping
    public String searchContact(@ModelAttribute Contact contact, Model model) {
//        Contacts contacts = new Contacts();
//        contacts.getContactById(contact.getId(), model, applicationArguments);
        String id = contact.getId();
        logger.info("ID >> " + id);

        Optional<Contact> optionalContact = contactCacheService.get(id, model);

        List<Contact> contactList;

//        if (optionalContact.isPresent()) {
//            contactList = optionalContact.get();
//            ctc = new Contact(id, contactList);
//        } else {
//            logger.info("ID does not exist");
//            return "index";
//        }

        model.addAttribute("id", id);
        return "contact";
    }

}
