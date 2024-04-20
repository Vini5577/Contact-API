package com.api.contact.reposiotiries;

import com.api.contact.model.ContactModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<ContactModel, Long> {

    Boolean existsByPhone(String phone);
}
