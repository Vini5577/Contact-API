package com.api.contact.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_contact")
public class ContactModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String Name;

    @Column(nullable = false, unique = true)
    public String Phone;

    @Column(nullable = false)
    public Boolean IsActive;
}
