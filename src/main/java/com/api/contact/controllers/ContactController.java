package com.api.contact.controllers;

import com.api.contact.dtos.ContactDto;
import com.api.contact.model.ContactModel;
import com.api.contact.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/contact")
public class ContactController {

    final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    public ResponseEntity<Object> saveContact(@RequestBody @Valid ContactDto contactDto) {
        String formatPhone = contactService.fomartPhoneNumber(contactDto.getPhone());

        if(contactService.existsByPhone(formatPhone))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Phone is alredy in use!");

        if(formatPhone == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Phone must have 10 or 11 characters.");

        var contactModel = new ContactModel();
        contactModel.setName(contactDto.getName());
        contactModel.setPhone(formatPhone);
        contactModel.setIsActive(true);
        return ResponseEntity.status(HttpStatus.CREATED).body(contactService.save(contactModel));
    }

    @GetMapping
    public ResponseEntity<Object> getAllContact() {

        return  ResponseEntity.status(HttpStatus.OK).body(contactService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneContact(@PathVariable(value = "id") Long id) {
        Optional<ContactModel> contactModelOptional = contactService.getById(id);

        if(!contactModelOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contact not found");

        return ResponseEntity.status(HttpStatus.OK).body(contactModelOptional.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateContact(@PathVariable(value ="id") Long id,
                                                @RequestBody @Valid ContactDto contactDto) {

        Optional<ContactModel> contactModelOptional = contactService.getById(id);
        String formatPhone = contactService.fomartPhoneNumber(contactDto.getPhone());

        if(!contactModelOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contact not found");

        if(formatPhone == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Phone must have 10 or 11 characters.");

        var contactModel = new ContactModel();
        BeanUtils.copyProperties(contactDto, contactModel);
        contactModel.setId(contactModelOptional.get().getId());
        contactModel.setPhone(formatPhone);
        contactModel.setIsActive(contactModelOptional.get().getIsActive());

        return ResponseEntity.status(HttpStatus.OK).body(contactService.save(contactModel));
    }

    @PutMapping("/{id}/swap")
    public ResponseEntity<Object> activeContact(@PathVariable(value ="id") Long id) {

        Optional<ContactModel> contactModelOptional = contactService.getById(id);

        var contactModel = new ContactModel();

        contactModel.setId(contactModelOptional.get().getId());
        contactModel.setName(contactModelOptional.get().getName());
        contactModel.setPhone(contactModelOptional.get().getPhone());
        if(contactModelOptional.get().getIsActive())
            contactModel.setIsActive(false);
        else
            contactModel.setIsActive(true);

        return ResponseEntity.status(HttpStatus.OK).body(contactService.save(contactModel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteContact(@PathVariable(value = "id") Long id) {
        Optional<ContactModel> contactModelOptional = contactService.getById(id);

        if(!contactModelOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contact not found.");

        contactService.delete(contactModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Contact deleted successfully");
    }
}
