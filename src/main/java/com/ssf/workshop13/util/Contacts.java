package com.ssf.workshop13.util;

import com.ssf.workshop13.model.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Utility class to save and read contact information
 * from the data file that is stored on specific local directory
 */
public class Contacts {
    private static final Logger logger = LoggerFactory.getLogger(Contacts.class);

    public void saveContact(Contact contact, Model model, ApplicationArguments applicationArguments) {
        String contactFileName = contact.getId();
        String dirPath = applicationArguments.getOptionValues("dataDir").get(0);
        logger.info("dirPath >>> " + dirPath);
        PrintWriter printWriter = null;

        try {
            FileWriter fileWriter = new FileWriter(dirPath + "/" + contactFileName, Charset.forName("utf-8"));
            printWriter = new PrintWriter(fileWriter);
            printWriter.println(contact.getName());
            printWriter.println(contact.getEmail());
            printWriter.println(contact.getPhoneNumber());
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            printWriter.close();
        }

        model.addAttribute("contact", new Contact(contact.getId(),
                contact.getName(), contact.getEmail(), contact.getPhoneNumber()));
    }

    public void getContactById(String id, Model model, ApplicationArguments applicationArguments) {
        String dirPath = applicationArguments.getOptionValues("dataDir").get(0);
        logger.info("Search contact using ID >>> " + dirPath);
        Contact contactInfo = new Contact();

        try {
            Path filepath = new File(dirPath + "/" + id).toPath();
            List<String> contactList = Files.readAllLines(filepath, Charset.forName("utf-8"));
            contactInfo.setId(id);
            contactInfo.setName(contactList.get(0));
            contactInfo.setEmail(contactList.get(1));
            contactInfo.setPhoneNumber(Integer.parseInt(contactList.get(2)));
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Contact Not Found", e);
        }
        model.addAttribute("contact", contactInfo);
    }
}
