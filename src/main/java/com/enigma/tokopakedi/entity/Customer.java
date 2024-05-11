package com.enigma.tokopakedi.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "m_customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String address;
    private String phone;
    private Integer poin;
    private String reward;

    @OneToOne
    private UserCredential userCredential;

}
