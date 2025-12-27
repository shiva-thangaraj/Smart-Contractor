package com.smartcontractor.service;

import com.smartcontractor.model.Company;

import java.util.List;

public interface CompanyService {
    Company createCompany(String userId, Company company);

    List<Company> getAllCompanys(String userId);
}
