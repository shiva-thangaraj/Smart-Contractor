package com.smartcontractor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "companies")
public class Company {
    @Id
    private String companyId;
    private String companyName;
    private String companyDisc;
    private String userId; // Foreign key to User
    
    @OneToMany
    @JoinColumn(name = "companyId", insertable = false, updatable = false)
    private List<Employee> employees;
}
