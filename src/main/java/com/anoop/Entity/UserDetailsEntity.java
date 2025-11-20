package com.anoop.Entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "ATI_USER_DETAILS")
public class UserDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String accStatus;

    @OneToMany(mappedBy = "userDetailsEntity", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StudentEnqEntity> studentEnqEntity;
}
