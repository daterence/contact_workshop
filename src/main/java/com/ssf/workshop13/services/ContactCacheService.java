package com.ssf.workshop13.services;

import com.ssf.workshop13.model.Contact;
import com.ssf.workshop13.repositories.ContactRepository;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ContactCacheService {
    private final Logger logger = LoggerFactory.getLogger(ContactCacheService.class);

    @Autowired
    private ContactRepository contactRepository;

    private JsonArray parseJsonArray(String s) {
        try(InputStream is = new ByteArrayInputStream(s.getBytes())) {
            JsonReader reader = Json.createReader(is);
            logger.info("READER >>> " + reader.readArray());
            return reader.readArray();
        } catch (Exception e) {
            logger.info("Parsing " + e);
        }
        return Json.createArrayBuilder().build();
    }

    public void save(String id, Contact contact) {

//        String value = Json.createObjectBuilder()
//            .add("name", contact.getName())
//            .add("email", contact.getEmail())
//            .add("phoneNumber", contact.getPhoneNumber())
//            .build()
//            .toString();
        String value = Stream.of(contact.getName(), contact.getEmail(), contact.getPhoneNumber())
                        .map(n -> String.valueOf(n))
                        .collect(Collectors.joining(","));

        contactRepository.save(id, value);
    }

    public Optional<Contact> get(String id, Model model) {
        Optional<String> opt = contactRepository.get(id);
        if (opt.isEmpty())
            return Optional.empty();
        logger.info("OPT >>>> " + opt);

        String[] arr = opt.get().split(",");
        String name = arr[0];
        String email = arr[1];
        int phoneNumber = Integer.parseInt(arr[2]);

//        try (InputStream is = new ByteArrayInputStream())
//        JsonReader jsonReader = Json.createReader()
//        JsonArray nameArr = parseJsonArray(opt.get());
//        logger.info("ARRAY >>" + jsonArray);

//        List<Contact> contact = jsonArray.stream()
//                .map(v -> (JsonObject)v)
//                .map(Contact::create)
//                .collect(Collectors.toList());
        Contact contact = new Contact(name, email, phoneNumber);
        model.addAttribute("contact", contact);

        return Optional.of(contact);
    }
}
