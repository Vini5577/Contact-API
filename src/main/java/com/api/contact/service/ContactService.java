package com.api.contact.service;

import com.api.contact.model.ContactModel;
import com.api.contact.reposiotiries.ContactRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    final ContactRepository contactRepository;


    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Transactional
    public ContactModel save(ContactModel contactModel) {
        return contactRepository.save(contactModel);
    }

    public boolean existsByPhone(String phone) {
        return contactRepository.existsByPhone(phone);
    }

    public String fomartPhoneNumber(String phone) {
        if(phone == null) {
            return null;
        }

        if (phone.length() == 11) {
            return "(" + phone.substring(0, 2) + ") " + phone.substring(2, 7) + "-" + phone.substring(7);
        } else if (phone.length() == 10) {
            return "(" + phone.substring(0, 2) + ") " + phone.substring(2, 6) + "-" + phone.substring(6);
        } else {
            return null;
        }
    }

    public List<ContactModel> getAll() {
        return contactRepository.findAll();
    }

    public Optional<ContactModel> getById(Long id) {
        return contactRepository.findById(id);
    }

    public void delete(ContactModel contactModel) {
        contactRepository.delete(contactModel);
    }
}
